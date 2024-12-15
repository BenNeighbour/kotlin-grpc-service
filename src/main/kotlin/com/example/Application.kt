package com.example

import io.grpc.protobuf.services.ProtoReflectionService
import io.micronaut.runtime.Micronaut

fun main(args: Array<String>) {
	Micronaut.build()
		.args(*args)
		.banner(false)
		.singletons(ProtoReflectionService.newInstance())
		.start()
}