package com.vanotech.experiments.core.utils.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Duration

object DurationInSecondsAsLongSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.vanotech.experiments.core.DurationInSecondsAsLong",
        PrimitiveKind.LONG
    )

    override fun serialize(encoder: Encoder, value: Duration) {
        return encoder.encodeLong(value.toSeconds())
    }

    override fun deserialize(decoder: Decoder): Duration {
        return Duration.ofSeconds(decoder.decodeLong())
    }
}