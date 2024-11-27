package me.znepb.roadworks.container

import me.znepb.roadworks.RoadworksMain.logger
import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.RoadworksRegistry.ModBlocks.POST_CONTAINER
import me.znepb.roadworks.attachment.Attachment
import me.znepb.roadworks.util.PostThickness
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

open class PostContainerBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(RoadworksRegistry.ModBlockEntities.CONTAINER_BLOCK_ENTITY, pos, state) {
    var thickness = PostThickness.MEDIUM
    var up = PostThickness.NONE
    var down = PostThickness.NONE
    var north = PostThickness.NONE
    var east = PostThickness.NONE
    var south = PostThickness.NONE
    var west = PostThickness.NONE
    var footer = false
    var attachments = listOf<Attachment>()
    var stub = false
    private var doneInitialUpdate = false
    private var initialNBTFetch = false

    companion object {
        fun onTick(world: World, pos: BlockPos, state: BlockState, blockEntity: PostContainerBlockEntity?) {
            blockEntity?.onTick(world)
        }
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)
    override fun toInitialChunkDataNbt() = this.createNbt()

    public override fun writeNbt(nbt: NbtCompound) {
        nbt.putBoolean("footer", footer)
        nbt.putString("thickness", thickness.name)
        nbt.putString("up", up.name)
        nbt.putString("down", down.name)
        nbt.putString("north", north.name)
        nbt.putString("east", east.name)
        nbt.putString("south", south.name)
        nbt.putString("west", west.name)
        nbt.putBoolean("stub", stub)

        val attachmentList = NbtList()
        this.attachments.forEach {
            val compound = NbtCompound()
            it.writeNBT(compound)
            attachmentList.add(compound)
        }

        nbt.put("attachments", attachmentList)
    }

    override fun readNbt(nbt: NbtCompound) {
        initialNBTFetch = true

        super.readNbt(nbt)

        footer = nbt.getBoolean("footer")
        thickness = PostThickness.fromName(nbt.getString("thickness"))
        up = PostThickness.fromName(nbt.getString("up"))
        down = PostThickness.fromName(nbt.getString("down"))
        north = PostThickness.fromName(nbt.getString("north"))
        east = PostThickness.fromName(nbt.getString("east"))
        south = PostThickness.fromName(nbt.getString("south"))
        west = PostThickness.fromName(nbt.getString("west"))
        stub = nbt.getBoolean("stub") || false

        if(thickness == PostThickness.NONE) {
            thickness = PostThickness.MEDIUM
        }

        val serializedAttachments = nbt.getList("attachments", NbtElement.COMPOUND_TYPE.toInt())
        val attachments = this.attachments.toMutableList()
        val uuidsFound = mutableListOf<UUID>()

        // Check for new attachments
        for (i in 0 until serializedAttachments.size) {
            val attachment = serializedAttachments.getCompound(i)
            val type = Identifier(attachment.getString("id"))
            val uuid = UUID.fromString(attachment.getString("uuid"))
            uuidsFound.add(uuid)

            val existingAttachment = attachments.find { it.id == uuid }

            if(existingAttachment == null) {
                val attachmentType = RoadworksRegistry.ModAttachments.REGISTRY.get(type)

                if (attachmentType == null) {
                    logger.warn("PostContainerBlockEntity at ${this.pos} has unknown attachment with ID $type, ignoring")
                    continue
                }

                val attachmentObject = attachmentType.factory.create(this)
                attachmentObject.readNBT(attachment)

                attachments.add(attachmentObject)
            } else {
                attachments[attachments.indexOf(existingAttachment)].readNBT(attachment)
            }
        }

        // Remove attachments that no longer exist
        this.attachments.forEach {
            if(!uuidsFound.contains(it.id)) {
                attachments.remove(it)
            }
        }

        if(!doneInitialUpdate) {
            if(this.canCheckConnections(this.world)) {
                this.getConnections(this.world!!)
                doneInitialUpdate = true
            }
        }

        this.attachments = attachments
        this.getWorld()?.getBlockState(this.getPos())?.updateNeighbors(this.world, this.pos, Block.NOTIFY_NEIGHBORS, 2)
    }

    fun addAttachment(attachment: Attachment, facing: Direction) {
        val attachments = mutableListOf<Attachment>()
        val initialNBT = NbtCompound()
        attachment.writeNBT(initialNBT)
        initialNBT.putString("facing", facing.getName())
        attachment.readNBT(initialNBT)

        attachments.addAll(0, this.attachments)
        attachments.add(attachment)
        this.attachments = attachments.toList()
        this.markDirty()
        getConnections(this.world!!)
    }

    fun removeAttachment(uuid: UUID) {
        val attachment = this.attachments.find {
            it.id == uuid
        }
        attachment?.remove()

        val newAttachments = mutableListOf<Attachment>()
        this.attachments.forEach {
            if(it != attachment) newAttachments.add(it)
        }

        this.attachments = newAttachments.toList()
        this.markDirty()
        getConnections(this.world!!)
    }

    fun getAttachment(uuid: UUID): Attachment? {
        return attachments.find { it.id == uuid }
    }

    fun getPlayerAttachmentLookingAt(player: PlayerEntity): Attachment? {
        val eyePos = player.getCameraPosVec(1.0F)
        val lookDirection = player.getRotationVec(1.0F)
        val reachVector = eyePos.add(lookDirection.multiply(4.0))

        val result = player.world.raycast(RaycastContext(
            eyePos,
            reachVector,
            RaycastContext.ShapeType.OUTLINE,
            RaycastContext.FluidHandling.NONE,
            player
        ))

        if(result.type == HitResult.Type.BLOCK) {
            val relativeHitPos: Vec3d = result.pos.subtract(result.blockPos.x.toDouble(), result.blockPos.y.toDouble(), result.blockPos.z.toDouble())
            return this.getAttachmentHit(relativeHitPos, ShapeContext.absent())
        }

        return null
    }

    fun getAttachmentHit(relativeHitPos: Vec3d, shapeContext: ShapeContext): Attachment? {
        val validAttachments = mutableListOf<Attachment>()
        val smallIntersectionBox = Box(
            relativeHitPos.x - 0.005, relativeHitPos.y - 0.005, relativeHitPos.z - 0.005,
            relativeHitPos.x + 0.005, relativeHitPos.y + 0.005, relativeHitPos.z + 0.005
        )

        // Check attachments
        this.attachments.forEach {
            val shape = it.getShape(shapeContext)
            shape.boundingBoxes.forEach { it1 ->
                if(it1.intersects(smallIntersectionBox) && !validAttachments.contains(it)) {
                    validAttachments.add(it)
                }
            }
        }

        return if(validAttachments.size == 0) null else validAttachments[0]
    }

    fun getAttachmentHit(hit: BlockHitResult): Attachment? {
        val pos = hit.blockPos
        val relativeHit = hit.pos.subtract(
            pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()
        )

        return getAttachmentHit(relativeHit, ShapeContext.absent())
    }

    fun getAttachmentsOnFace(direction: Direction): List<Attachment> {
        return this.attachments.filter { it.facing == direction }
    }

    private fun canCheckConnections(world: WorldAccess?): Boolean {
        val chunk = world?.chunkManager?.isChunkLoaded(pos.x / 16, pos.z / 16)
        val chunkN = world?.chunkManager?.isChunkLoaded(pos.x / 16, (pos.z / 16) - 1)
        val chunkE = world?.chunkManager?.isChunkLoaded((pos.x / 16) + 1, pos.z / 16)
        val chunkS = world?.chunkManager?.isChunkLoaded(pos.x / 16, (pos.z / 16) + 1)
        val chunkW = world?.chunkManager?.isChunkLoaded((pos.x / 16) - 1, pos.z / 16)

        return chunk == true && chunkN == true && chunkE == true && chunkS == true && chunkW == true
    }

    fun getConnections(world: WorldAccess) {
        if(canCheckConnections(world)) {
            val stateDown = world.getBlockState(pos.down())
            val stateUp = world.getBlockState(pos.up())
            val stateNorth = world.getBlockState(pos.north())
            val stateEast = world.getBlockState(pos.east())
            val stateSouth = world.getBlockState(pos.south())
            val stateWest = world.getBlockState(pos.west())

            footer = shouldBeFooter(stateDown)
            down = if(!footer) this.getConnectionThickness(pos.down(), stateDown, Direction.DOWN) else PostThickness.NONE
            up = this.getConnectionThickness(pos.up(), stateUp, Direction.UP)
            north = this.getConnectionThickness(pos.north(), stateNorth, Direction.NORTH)
            south = this.getConnectionThickness(pos.south(), stateSouth, Direction.SOUTH)
            east = this.getConnectionThickness(pos.east(), stateEast, Direction.EAST)
            west = this.getConnectionThickness(pos.west(), stateWest, Direction.WEST)
            stub = stub && up == PostThickness.NONE && north == PostThickness.NONE && south == PostThickness.NONE && east == PostThickness.NONE && west == PostThickness.NONE

            this.markDirty()
        }
    }

    private fun shouldBeFooter(state: BlockState?): Boolean {
        return state != null &&
                (!state.isOf(Blocks.AIR)
                        && !state.isOf(POST_CONTAINER))
    }

    private fun getConnectionThickness(pos: BlockPos, state: BlockState?, dir: Direction): PostThickness {
        if(state == null) return PostThickness.NONE

        if(state.isOf(POST_CONTAINER)) {
            // Don't attach to the front of blocks that are post-mountable
            val blockEntity = this.world?.getBlockEntity(this.pos.offset(dir))
            if(blockEntity is PostContainerBlockEntity) {
                return if(getAttachmentsOnFace(dir).isNotEmpty() || blockEntity.getAttachmentsOnFace(dir.opposite).isNotEmpty()) {
                    PostThickness.NONE
                } else {
                    blockEntity.thickness
                }
            }
        }

        val be = world?.getBlockEntity(pos)
        return if(be != null && be is PostContainerBlockEntity) be.thickness else PostThickness.NONE
    }

    fun getDirectionThickness(dir: Direction): PostThickness {
        return when(dir) {
            Direction.NORTH -> north
            Direction.EAST -> east
            Direction.SOUTH -> south
            Direction.WEST -> west
            Direction.UP -> up
            Direction.DOWN -> down
            else -> PostThickness.NONE
        }
    }

    fun onTick(world: World) {
        this.setWorld(world)
        this.attachments.forEach { it.onTick() }
    }

    override fun markDirty() {
        this.world?.updateListeners(pos, this.cachedState, this.cachedState, Block.NOTIFY_LISTENERS)
        super.markDirty()
    }

    fun isHorizontal() = this.north == PostThickness.NONE || this.east != PostThickness.NONE || this.south == PostThickness.NONE || this.west != PostThickness.NONE
    fun isVertical() = this.up != PostThickness.NONE || this.down != PostThickness.NONE || this.footer

    fun remove() {
        this.attachments.forEach {
            it.remove()
        }
    }

    fun onUse(player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if(player.isHolding(RoadworksRegistry.ModItems.WRENCH)) {
            stub = !stub && up == PostThickness.NONE && north == PostThickness.NONE && south == PostThickness.NONE && east == PostThickness.NONE && west == PostThickness.NONE
            return if(up == PostThickness.NONE && north == PostThickness.NONE && south == PostThickness.NONE && east == PostThickness.NONE && west == PostThickness.NONE) ActionResult.SUCCESS else ActionResult.PASS
        }

        return ActionResult.PASS
    }
}