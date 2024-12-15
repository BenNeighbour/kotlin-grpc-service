package com.example.converter

import com.google.protobuf.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

object ProtoConverter {
    fun String.toUUID(): UUID {
        return UUID.fromString(this)
    }

    fun List<String>.toUUID(): List<UUID> {
        return this.map { it.toUUID() }
    }

    fun UUID.toProto(): String {
        return this.toString()
    }

    fun List<UUID>.toProto(): List<String> {
        return this.map { it.toProto() }
    }

    fun Timestamp.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochSecond(this.seconds, this.nanos.toLong()),
            ZoneId.systemDefault()
        )
    }

    fun LocalDateTime.toProto(): Timestamp {
        val instant = this.atZone(ZoneId.systemDefault()).toInstant()

        return Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()
    }
}