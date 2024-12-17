package com.example.shared

interface DtoConverter<E, D> {
    fun D.toEntity(): E
    fun E.toProto(): D
    fun List<E>.toProto(): List<D> = this.map { it.toProto() }
    fun List<D>.toEntity(): List<E> = this.map { it.toEntity() }
}

interface RequestConverter<E, D> {
    fun D.toEntity(): E
}