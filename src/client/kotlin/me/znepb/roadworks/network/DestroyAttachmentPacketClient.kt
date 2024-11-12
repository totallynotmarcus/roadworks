package me.znepb.roadworks.network

import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.network.PacketByteBuf

class DestroyAttachmentPacketClient {
    companion object {
        fun sendDestroyAttachmentPacket(packet: DestroyAttachmentPacket) {
            val buf = PacketByteBuf(Unpooled.buffer())
            buf.encodeAsJson(DestroyAttachmentPacket.CODEC, packet)

            // Send the packet to the server
            MinecraftClient.getInstance().networkHandler?.sendPacket(
                ClientPlayNetworking.createC2SPacket(DestroyAttachmentPacket.PACKET_ID, buf)
            )
        }
    }
}