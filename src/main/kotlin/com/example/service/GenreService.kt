package com.example.service

import com.example.*
import io.micronaut.data.model.Page

interface GenreService {
    suspend fun createGenre(request: GenreCreateRequest): GenreCreateResponse
    suspend fun getGenreByIds(request: GenreGetManyRequest): GenreGetManyResponse
    suspend fun getGenreById(request: GenreGetRequest): GenreGetResponse
    suspend fun createGenres(request: GenreCreateManyRequest): GenreCreateManyResponse
    suspend fun list(request: ListRequest): GenreListResponse
}