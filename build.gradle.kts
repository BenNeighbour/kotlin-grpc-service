import com.google.protobuf.gradle.*
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.23"
    id("com.google.devtools.ksp") version "1.9.23-1.0.19"
    id("groovy") 
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.0"
    id("com.google.protobuf") version "0.9.2"
    id("io.micronaut.aot") version "4.4.4"
}

version = "0.1"
group = "com.example"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut.data:micronaut-data-processor")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-discovery-core")
    implementation("io.micronaut.data:micronaut-data-r2dbc")
    implementation("io.micronaut.grpc:micronaut-grpc-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")
    implementation("io.micronaut.data:micronaut-data-hibernate-reactive")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("io.grpc:grpc-services")
    implementation("io.grpc:grpc-kotlin-stub")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.google.protobuf:protobuf-kotlin")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
}


application {
    mainClass = "com.example.ApplicationKt"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
}


sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/grpckt")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.64.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

micronaut {
    testRuntime("spock2")
    runtime("netty")

    processing {
        incremental(true)
        annotations("com.example.*")
        aot {
            // Please review carefully the optimizations enabled below
            // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
            optimizeServiceLoading = false
            convertYamlToJava = false
            precomputeOperations = true
            cacheEnvironment = true
            optimizeClassLoading = true
            deduceEnvironment = true
            optimizeNetty = true
            replaceLogbackXml = true
        }
    }
}

graalvmNative.toolchainDetection = false

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}


