package com.example.converter

import com.example.model.Genre
import com.example.GenreDto
import com.example.shared.ProtoConverter.toProto
import com.example.shared.DtoConverter

object GenreDtoConverter : DtoConverter<Genre, GenreDto> {
    override fun GenreDto.toEntity(): Genre {
        return Genre(
            name = this.name,
            description = this.description,
            isActive = this.isActive
        )
    }

    // TODO - Somehow make this like the one above, or maybe try and find an interchangeable, reversible way to do this?
    override fun Genre.toProto(): GenreDto {
        return GenreDto.newBuilder()
            .setId(this.id.toProto())
            .setName(this.name)
            .setDescription(this.description)
            .setIsActive(this.isActive)
            .setCreatedAt(this.createdAt.toProto())
            .setUpdatedAt(this.updatedAt.toProto())
            .build()
    }
}