package me.znepb.roadworks.network

import com.mojang.serialization.*
import com.mojang.serialization.codecs.RecordCodecBuilder
import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.container.PostContainerBlockEntity
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Uuids
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*
import java.util.stream.Stream

data class DestroyAttachmentPacket(val blockPos: BlockPos, val world: RegistryKey<World>, val uuid: UUID) {
    companion object {
        val PACKET_ID = RoadworksMain.ModId("destroy_attachment")

        private val MAP_CODEC = RecordCodecBuilder.mapCodec<DestroyAttachmentPacket>{ it.group(
            BlockPos.CODEC.fieldOf("blockPos").forGetter(DestroyAttachmentPacket::blockPos),
            World.CODEC.fieldOf("world").forGetter(DestroyAttachmentPacket::world),
            Uuids.CODEC.fieldOf("uuid").forGetter(DestroyAttachmentPacket::uuid)
        ).apply(it, ::DestroyAttachmentPacket) }

        val CODEC: Codec<DestroyAttachmentPacket> = MapCodec.MapCodecCodec(object : MapCodec<DestroyAttachmentPacket>() {
            override fun <T> keys(ops: DynamicOps<T>): Stream<T> {
                return MAP_CODEC.keys(ops)
            }

            override fun <T> decode(ops: DynamicOps<T>, input: MapLike<T>): DataResult<DestroyAttachmentPacket> {
                return MAP_CODEC.decode(ops, input)
            }

            override fun <T> encode(
                input: DestroyAttachmentPacket?,
                ops: DynamicOps<T>,
                prefix: RecordBuilder<T>
            ): RecordBuilder<T>? {
                return MAP_CODEC.encode(input, ops, prefix)
            }
        })

        fun register() {
            ServerPlayNetworking.registerGlobalReceiver(PACKET_ID) { server, player, _, buf, _ ->
                val removeData = buf.decodeAsJson(CODEC)

                server.execute {
                    val blockPos = removeData.blockPos
                    val world = player.world
                    val blockEntity = world?.let {
                        val be = it.getBlockEntity(removeData.blockPos)
                        if(be is PostContainerBlockEntity) be else null
                    }

                    val canPlace = blockEntity?.world?.canPlayerModifyAt(player, blockPos)

                    if(canPlace == false) return@execute

                    if(!blockPos.isWithinDistance(player.blockPos, 8.0) || blockEntity == null) {
                        return@execute
                    }

                    blockEntity.removeAttachment(removeData.uuid)
                }
            }
        }
    }
}