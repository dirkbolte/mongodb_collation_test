package com.example.demo.repository

import org.springframework.data.mongodb.core.mapping.MongoId

data class SampleDocument(
    @field:MongoId
    var id: String? = null,
    val value: String
)