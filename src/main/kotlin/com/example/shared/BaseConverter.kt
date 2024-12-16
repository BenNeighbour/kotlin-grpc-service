package com.example.shared

import io.micronaut.data.model.Page

interface DtoConverter<E, D> {
    fun D.toEntity(): E
    fun E.toProto(): D
    fun List<E>.toProto(): List<D> = this.map { it.toProto() }
    fun List<D>.toEntity(): List<E> = this.map { it.toEntity() }
    fun Page<E>.toProto(): Page<D> = this.map { it.toProto() }
}

interface RequestConverter<E, D> {
    fun D.toEntity(): E
}