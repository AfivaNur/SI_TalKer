package com.afiva.appskelurahan.data.dto.api_response

data class DataResponse <T>(
    val success : Boolean,
    val message: String,
    val data: T? = null,
    val pagination: Pagination?
)