package me.znepb.roadworks.sign

import com.google.gson.JsonParser
import com.mojang.serialization.JsonOps
import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.RoadworksMain.logger
import me.znepb.roadworks.network.SyncContentPacket
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.registry.RegistryOps
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier

class SignageManager {
    private val SIGN_TYPES = Object2ObjectOpenHashMap<Identifier, SignType>()
    private val sync = RoadworksMain.ModId("sync_content")
    private val id = RoadworksMain.ModId("early_reload")

    init {
        ServerPlayConnectionEvents.INIT.register(sync) { handler, _ ->
            val list = mutableListOf<SignTypeWithIdentifier>()
            this.SIGN_TYPES.forEach {
                list.add(SignTypeWithIdentifier(it.key, it.value))
            }

            val buf = PacketByteBuf(Unpooled.buffer())
            buf.encodeAsJson(SyncContentPacket.CODEC, SyncContentPacket(list))

            handler.sendPacket(ServerPlayNetworking.createS2CPacket(SyncContentPacket.SYNC_CONTENT_PACKET_ID, buf))
        }

        // Server Event Registries
        ServerLifecycleEvents.SERVER_STARTED.addPhaseOrdering(id, Event.DEFAULT_PHASE)
        ServerLifecycleEvents.SERVER_STARTED.register(id) { loadCustomSignage(it) }

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.addPhaseOrdering(id, Event.DEFAULT_PHASE)
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(id) { minecraftServer, _, _ ->
            loadCustomSignage(minecraftServer)
        }
    }

    fun getSignTypes() = SIGN_TYPES

    fun getSign(type: Identifier) = SIGN_TYPES[type]

    fun sync(signData: SyncContentPacket) {
        logger.debug("Syncing server signage")

        this.SIGN_TYPES.clear()
        signData.signs.forEach {
            this.SIGN_TYPES[it.identifier] = it.signType
        }

        logger.info("Server signage synced")
    }

    private fun addCustomSign(server: MinecraftServer, identifier: Identifier, type: SignType) {
        RoadworksMain.logger.debug("Loading custom sign {}", identifier)
        SIGN_TYPES[identifier] = type
    }

    private fun loadCustomSignage(server: MinecraftServer) {
        RoadworksMain.logger.info("Reloading custom content")

        val ops = RegistryOps.of(JsonOps.INSTANCE, server.registryManager)
        val manager = server.resourceManager

        for (res in manager.findResources("signs") { it.path.endsWith(".json") }.entries) {
            val id = Identifier(
                res.key.namespace,
                res.key.path.substring("signs/".length, res.key.path.length - 5)
            )

            try {
                val signType = SignType.CODEC.decode(ops, JsonParser.parseReader(res.value.reader)).getOrThrow(false) {}
                addCustomSign(server, id, signType.first)
            } catch (e: Exception) {
                RoadworksMain.logger.warn("{} is invalid", res.key.toString())
                e.printStackTrace()
            }
        }
    }
}