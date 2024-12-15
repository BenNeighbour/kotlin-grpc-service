package com.example.converter

import io.micronaut.data.model.Page

// A DTO can be converted into basically anything
interface DtoConverter<E, D> {
    fun D.toEntity(): E
    fun E.toProto(): D
    fun List<E>.toProto(): List<D> = this.map { it.toProto() }
    fun List<D>.toEntity(): List<E> = this.map { it.toEntity() }
    fun Page<E>.toProto(): Page<D> = this.map { it.toProto() }
}

// A request will only ever be converted into an entity (or partial entity)
interface RequestConverter<E, D> {
    fun D.toEntity(): E
}