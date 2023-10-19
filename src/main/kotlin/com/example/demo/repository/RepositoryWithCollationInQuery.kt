package com.example.demo.repository

import org.springframework.data.mongodb.core.annotation.Collation
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository

interface RepositoryWithCollationInQuery: CrudRepository<SampleDocument, String> {

    @Query(
        value = "{}",
        sort = """{ "value":  1}""",
        collation = """
        {
            "locale": "de",
            "numericOrdering": true,
            "strength": 3,
            "alternate": "shifted"
        }
        """
    )
    fun findAllCollationInQuery(): List<SampleDocument>

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
    @Query(
        value = "{}",
        sort = """{ "value":  1}""",
        collation = """
        {
            "locale": "de",
            "numericOrdering": true,
            "strength": 3,
            "alternate": "shifted"
        }
        """
    )
    fun findAllCollationInAnnotation(): List<SampleDocument>

}