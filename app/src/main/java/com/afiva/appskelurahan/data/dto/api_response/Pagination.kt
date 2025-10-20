package com.afiva.appskelurahan.data.dto.api_response

data class Pagination (
    val current_page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int
)