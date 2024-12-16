package com.example.service

import com.example.GenreCreateManyRequest
import com.example.GenreCreateManyResponse
import com.example.GenreCreateRequest
import com.example.GenreCreateResponse
import com.example.GenreGetManyRequest
import com.example.GenreGetManyResponse
import com.example.GenreGetRequest
import com.example.GenreGetResponse
import com.example.GenreListResponse
import com.example.ListRequest
import com.example.repository.GenreRepository
import kotlinx.coroutines.reactive.awaitSingle
import com.example.converter.GenreCreateRequestConverter.toEntity
import com.example.converter.GenreDtoConverter.toEntity
import com.example.converter.GenreDtoConverter.toProto
import com.example.shared.ProtoConverter.toPredicate
import com.example.shared.ProtoConverter.toUUID
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow

@Singleton
class GenreServiceImpl(private val genreRepository: GenreRepository) : GenreService {
    override suspend fun createGenre(request: GenreCreateRequest): GenreCreateResponse {
        val item = genreRepository.save(request.toEntity()).awaitSingle().toProto()

        return GenreCreateResponse.newBuilder().setItem(item).build()
    }

    override suspend fun createGenres(request: GenreCreateManyRequest): GenreCreateManyResponse {
        val items = genreRepository.saveAll(request.itemsList.toEntity()).asFlow().toList().toProto()

        return GenreCreateManyResponse.newBuilder().addAllItems(items).build()
    }

    override suspend fun getGenreByIds(request: GenreGetManyRequest): GenreGetManyResponse {
        val items = genreRepository.findByIds(request.idsList.toUUID()).asFlow().toList().toProto()

        return GenreGetManyResponse.newBuilder().addAllItems(items).build()
    }

    override suspend fun getGenreById(request: GenreGetRequest): GenreGetResponse {
        val item = genreRepository.findById(request.id.toUUID()).awaitSingle().toProto()

        return GenreGetResponse.newBuilder().setItem(item).build()
    }

    override suspend fun list(request: ListRequest): GenreListResponse {
//        TODO - Convert request field string from proto -> entity (.toEntity)
//        TODO - Make pagination parameter work
        val page = genreRepository.findAll(request.toPredicate(), Pageable.UNPAGED).awaitSingle().toProto()

        // Build and return the response
        return GenreListResponse.newBuilder()
            .addAllItems(page.content)
            .build()
    }
}
