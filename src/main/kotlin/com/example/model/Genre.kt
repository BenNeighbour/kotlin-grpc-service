package com.example.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.time.LocalDateTime
import java.util.UUID
import io.micronaut.data.annotation.AutoPopulated
import io.micronaut.data.annotation.DateUpdated

@MappedEntity
data class Genre(
    val name: String,
    val description: String,
    val isActive: Boolean,
) {
    @Id
    @AutoPopulated
    lateinit var id: UUID

    @AutoPopulated
    @DateUpdated
    lateinit var createdAt: LocalDateTime

    @AutoPopulated
    @DateUpdated
    lateinit var updatedAt: LocalDateTime
}