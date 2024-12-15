package com.example.transport

import com.example.*
import com.example.service.GenreService

import jakarta.inject.Singleton

@Singleton
class GenreTransport(private val genreService: GenreService) : GenreServiceGrpcKt.GenreServiceCoroutineImplBase() {

    override suspend fun create(request: GenreCreateRequest): GenreCreateResponse {
        return genreService.createGenre(request)
    }

    override suspend fun get(request: GenreGetRequest): GenreGetResponse {
        return genreService.getGenreById(request)
    }

    override suspend fun createMany(request: GenreCreateManyRequest): GenreCreateManyResponse {
        return genreService.createGenres(request)
    }

    override suspend fun getMany(request: GenreGetManyRequest): GenreGetManyResponse {
        return genreService.getGenreByIds(request)
    }


}