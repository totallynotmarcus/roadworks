package me.znepb.roadworks.render.attachments

import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.attachment.Attachment
import me.znepb.roadworks.attachment.AttachmentType

class AttachmentRendererFactories {
    companion object {
        private val FACTORIES: MutableMap<AttachmentType<*>, AttachmentRendererFactory<*>> = Maps.newHashMap()

        fun <T : Attachment> register(type: AttachmentType<out T>, factory: AttachmentRendererFactory<T>) {
            FACTORIES[type] = factory
        }

        fun reload(): ImmutableMap<AttachmentType<*>, AttachmentRenderer<Attachment>> {
            val builder = ImmutableMap.builder<AttachmentType<*>, AttachmentRenderer<Attachment>>()
            FACTORIES.forEach {
                try {
                    builder.put(it.key, it.value.create() as AttachmentRenderer<Attachment>)
                } catch (var5: Exception) {
                    throw IllegalStateException(
                        "Failed to create model for " + RoadworksRegistry.ModAttachments.REGISTRY.getId(it.key)
                    )
                }
            }
            return builder.build()
        }
    }

    fun interface AttachmentRendererFactory<T : Attachment> {
        fun create(): AttachmentRenderer<T>
    }
}
