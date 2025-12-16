package com.vanotech.experiments.core.utils.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Duration

object DurationInMinutesAsLongSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.vanotech.experiments.core.DurationInMinutesAsLong",
        PrimitiveKind.LONG
    )

    override fun serialize(encoder: Encoder, value: Duration) {
        return encoder.encodeLong(value.toMinutes())
    }

    override fun deserialize(decoder: Decoder): Duration {
        return Duration.ofMinutes(decoder.decodeLong())
    }
}