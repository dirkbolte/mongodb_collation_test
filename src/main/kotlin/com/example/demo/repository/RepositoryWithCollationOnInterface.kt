package com.example.demo.repository

import org.springframework.data.mongodb.core.annotation.Collation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository

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
interface RepositoryWithCollationOnInterface: CrudRepository<SampleDocument, String> {

    @Query(
        value = "{}",
        sort = """{ "value":  1}"""
    )
    fun findAllCustom(): List<SampleDocument>

}