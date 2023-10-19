package com.example.demo.repository

import org.springframework.data.mongodb.core.annotation.Collation
import org.springframework.data.mongodb.core.mapping.MongoId

@Collation(
    """
        {
            "locale": "de",
            "numericOrdering": true,
            "strength": 3,
            "alternate": "shifted"
        }            
        """
)
data class SampleDocument(
    @field:MongoId
    var id: String? = null,
    val value: String
)