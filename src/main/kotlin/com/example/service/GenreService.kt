package com.example.service

import com.example.*
import io.micronaut.data.model.Page

interface GenreService {
    suspend fun createGenre(request: GenreCreateRequest): GenreCreateResponse
    suspend fun getGenreByIds(request: GenreGetManyRequest): GenreGetManyResponse
    suspend fun getGenreById(request: GenreGetRequest): GenreGetResponse
    suspend fun createGenres(request: GenreCreateManyRequest): GenreCreateManyResponse

    //    TODO - Switch this out for a gRPC request/response which converts into io.micronaut.data.model.*
    suspend fun list(): Page<GenreDto>
}

