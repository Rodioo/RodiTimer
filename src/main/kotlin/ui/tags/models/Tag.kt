package ui.tags.models

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ui.common.toColor

object ColorSerializer : KSerializer<Color> {
    override val descriptor = PrimitiveSerialDescriptor("Color", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Color {
        return decoder.decodeString().toColor() ?: Color.Transparent
    }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeString(value.toString())
    }
}

@Serializable
data class Tag(
    val label: String,
    @Serializable(with = ColorSerializer::class)
    val color: Color,
    val isFavorite: Boolean = false,
    val id: Long? = null
)
