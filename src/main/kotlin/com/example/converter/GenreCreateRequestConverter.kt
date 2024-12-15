package com.example.converter

import com.example.GenreCreateRequest
import com.example.model.Genre

object GenreCreateRequestConverter : RequestConverter<Genre, GenreCreateRequest> {
    override fun GenreCreateRequest.toEntity(): Genre {
        return Genre(
            name = this.name,
            description = this.description,
            isActive = this.isActive,
        )
    }
}