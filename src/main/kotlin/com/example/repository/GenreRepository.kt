package com.example.repository

import com.example.model.Genre
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactiveStreamsPageableRepository
import reactor.core.publisher.Flux
import java.util.UUID

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface GenreRepository : ReactiveStreamsPageableRepository<Genre, UUID> {

    @Query("SELECT * FROM genre WHERE id IN (:ids)")
    fun findByIds(ids: List<UUID>): Flux<Genre>

}