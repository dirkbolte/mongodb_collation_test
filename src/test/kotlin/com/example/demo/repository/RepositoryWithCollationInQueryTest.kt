package com.example.demo.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration


@DataMongoTest
@Testcontainers
class RepositoryWithCollationInQueryTest {

    companion object {

        @Container
        private val db =
            MongoDBContainer("mongo:4").apply {
                waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(20L)))
                start()
            }

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", db::getReplicaSetUrl)
        }
    }

    @Autowired
    private lateinit var repository: RepositoryWithCollationInQuery

    @Nested
    inner class `when using collation in Query annotation` {

        @Test
        fun `should have correct sorting for numbers`() {
            val first = repository.save(SampleDocument(value = "1aaa"))
            val fourth = repository.save(SampleDocument(value = "12aaa"))
            val third = repository.save(SampleDocument(value = "10aaa"))
            val second = repository.save(SampleDocument(value = "9aaa"))

            assertThat(repository.findAllCollationInQuery()).containsExactly(first, second, third, fourth)
        }
    }

    @Nested
    inner class `when using collation as separate annotation on query` {

        @Test
        fun `should have correct sorting for numbers`() {
            val first = repository.save(SampleDocument(value = "1aaa"))
            val fourth = repository.save(SampleDocument(value = "12aaa"))
            val third = repository.save(SampleDocument(value = "10aaa"))
            val second = repository.save(SampleDocument(value = "9aaa"))

            assertThat(repository.findAllCollationInAnnotation()).containsExactly(first, second, third, fourth)
        }
    }
}