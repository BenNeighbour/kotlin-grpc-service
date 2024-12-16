package com.example.shared

import com.example.ListRequest
import com.google.protobuf.Timestamp
import io.micronaut.data.repository.jpa.criteria.PredicateSpecification
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

object ProtoConverter {
    fun String.toUUID() = UUID.fromString(this)
    fun List<String>.toUUID() = map { it.toUUID() }
    fun UUID.toProto() = toString()
    fun List<UUID>.toProto() = map { it.toProto() }

    fun Timestamp.toLocalDateTime() = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(seconds, nanos.toLong()),
        ZoneId.systemDefault()
    )

    fun LocalDateTime.toProto(): Timestamp {
        val instant = atZone(ZoneId.systemDefault()).toInstant()
        return Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()
    }

    fun <E> ListRequest.toPredicate(): PredicateSpecification<E> =
        PredicateSpecificationConverter.toPredicateSpecification(this)
}
