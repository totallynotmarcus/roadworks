package me.znepb.roadworks.cabinet

import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.attachment.Attachment
import me.znepb.roadworks.attachment.LinkableAttachment
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.signal.AbstractSignalAttachment
import me.znepb.roadworks.signal.SignalLight
import me.znepb.roadworks.signal.SignalType
import me.znepb.roadworks.util.MiscUtils.blockPosFromNbtIntArray
import me.znepb.roadworks.util.MiscUtils.blockPosToNbtIntArray
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

class TrafficCabinetBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(RoadworksRegistry.ModBlockEntities.CABINET_BLOCK_ENTITY, pos, state) {
    val peripheral = TrafficCabinetPeripheral(this)
    private var connections = Connections()
    private val idTypeCache = HashMap<Int, String>()
    private var signalSetQueue = HashMap<Int, HashMap<SignalLight, Boolean>>()

    fun getTotalDevices() = connections.getAmount()
    fun getConnections() = connections
    fun getTypeOfId(id: Int): String? {
        return idTypeCache[id]
    }

    fun getConnectionIdentifierFromBlockPosAndUUID(pos: BlockPos, uuid: UUID): Int? {
        return connections.get(pos, uuid)?.getId()
    }

    fun addDevice(pos: BlockPos, uuid: UUID): Int? {
        val blockEntity = this.world?.getBlockEntity(pos)
        if(blockEntity is PostContainerBlockEntity) {
            val attachment = blockEntity.getAttachment(uuid)
            if(attachment is AbstractSignalAttachment) {
                return addSignal(attachment)
            }
        }
        return null
    }

    fun addSignal(attachment: AbstractSignalAttachment): Int {
        val newSignal = connections.add(attachment.container.pos, attachment.id)
        attachment.signalType.lights.forEach {
            attachment.setSignalActive(it, it.genericType == SignalLight.RED || it.isGeneric && it == SignalLight.RED)
        }
        idTypeCache[newSignal.getId()] = attachment.getLinkType()
        this.markDirty()
        return newSignal.getId()
    }

    fun addButton(pos: BlockPos, uuid: UUID): Int? {
        TODO("Update for new traffic system")
        //val blockEntity = this.world?.getBlockEntity(pos)
//
        //if(blockEntity is PedestrianButtonBlockEntity) {
        //    val newButton = connections.add(pos, uuid)
        //    idTypeCache[newButton.getId()] = blockEntity.getLinkType()
        //    this.markDirty()
        //    return newButton.getId()
        //}
//
        //return null
    }

    fun removeConnection(pos: BlockPos, uuid: UUID) {
        getConnectionIdentifierFromBlockPosAndUUID(pos, uuid)?.let { removeConnection(it) }
    }

    fun removeConnection(identifier: Int) {
        val connection = connections.get(identifier) ?: return
        val attachment = this.world?.let { connection.getAttachment(it) }
        if(attachment is LinkableAttachment) attachment.unlink()
        connections.remove(connection.getId())

        this.markDirty()
    }

    fun remove() {
        connections.getAll().forEach {
            val connection = connections.get(it.getId()) ?: return
            val attachment = this.world?.let { connection.getAttachment(it) }
            if(attachment is LinkableAttachment) attachment.unlink()
        }
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        connections.fromNbtList(nbt.get("connections") as NbtList)
        this.markDirty()
    }

    override fun writeNbt(nbt: NbtCompound) {
        nbt.put("connections", connections.toNbtList())

        super.writeNbt(nbt)
    }

    fun queueSignalSet(id: Int, signalLight: SignalLight, value: Boolean) {
        val idQueue = signalSetQueue.getOrPut(id) { hashMapOf() }
        idQueue[signalLight] = value
    }

    fun onTick(world: World, pos: BlockPos, state: BlockState) {
        if(signalSetQueue.isNotEmpty()) {
            connections.getAll().forEach {
                val item = signalSetQueue[it.getId()]

                if(item != null) {
                    val type = getTypeOfId(it.getId())
                    val attachment = it.getAttachment(world)

                    if(attachment is AbstractSignalAttachment) {
                        type?.let { it1 -> SignalType.fromType(it1) }!!.lights.forEach { light ->
                            if (item[light] != null) {
                                attachment.queueSignalSet(light, item[light]!!)
                            }
                        }
                    }
                }
            }
        }

        this.connections.getAll().forEach {
            if(this.idTypeCache[it.getId()] == null) {
                try {
                    val attachment = it.getAttachment(world)

                    if(attachment is LinkableAttachment) {
                        this.idTypeCache[it.getId()] = attachment.getLinkType()
                    }
                } catch(_: Exception) {}
            }
        }
    }

    class Connections {
        class Connection(private val id: Int, private val pos: BlockPos, private val uuid: UUID) {
            fun getId() = id
            fun getPos() = pos
            fun getUUID() = uuid

            fun toNbt(): NbtCompound {
                val compound = NbtCompound()
                compound.putInt("id", id)
                compound.putIntArray("pos", blockPosToNbtIntArray(pos))
                compound.putString("uuid", uuid.toString())
                return compound
            }

            fun getAttachment(world: BlockView): Attachment? {
                val be = world.getBlockEntity(this.pos)
                if(be is PostContainerBlockEntity) {
                    return be.getAttachment(this.uuid)
                }
                return null
            }

            companion object {
                fun fromNbt(nbt: NbtCompound): Connection {
                    return Connection(nbt.getInt("id"), blockPosFromNbtIntArray(nbt.getIntArray("pos")), UUID.fromString(nbt.getString("uuid")))
                }
            }
        }

        private val list = mutableListOf<Connection>()

        fun getNextId(): Int {
            var id = 1
            while(get(id) != null) {
                id++
            }

            return id
        }

        fun add(id: Int, pos: BlockPos, uuid: UUID): Connection {
            if(get(id) != null) throw IllegalArgumentException("ID $id already exists")
            if(get(pos, uuid) != null) throw IllegalArgumentException("Signal at $pos with UUID $uuid is already connected")
            val newConnection = Connection(id, pos, uuid)
            list.add(newConnection)
            return newConnection
        }

        fun add(pos: BlockPos, uuid: UUID): Connection {
            return this.add(getNextId(), pos, uuid)
        }

        fun get(id: Int): Connection? {
            val filtered = list.filter { it.getId() == id }
            return if(filtered.isNotEmpty()) filtered[0] else null
        }

        fun get(blockPos: BlockPos, uuid: UUID): Connection? {
            val filtered = list.filter { it.getPos() == blockPos && it.getUUID() == uuid }
            return if(filtered.isNotEmpty()) filtered[0] else null
        }

        fun remove(id: Int) {
            list.remove(list.filter { it.getId() == id }[0])
        }

        fun fromNbtList(list: NbtList) {
            list.forEach {
                if(
                    it is NbtCompound
                    && it.getType("pos") == NbtCompound.INT_ARRAY_TYPE
                    && it.getType("id") == NbtCompound.INT_TYPE
                    && it.getType("uuid") == NbtCompound.STRING_TYPE
                ) {
                    this.list.add(Connection.fromNbt(it))
                }
            }
        }

        fun toNbtList(): NbtList {
            val list = NbtList()
            this.list.forEach {
                list.add(it.toNbt())
            }
            return list
        }

        fun getAll() = list
        fun getAmount() = list.size
    }

    companion object {
        fun onTick(world: World, pos: BlockPos, state: BlockState, blockEntity: TrafficCabinetBlockEntity) {
            blockEntity.onTick(world, pos, state)
        }
    }
}