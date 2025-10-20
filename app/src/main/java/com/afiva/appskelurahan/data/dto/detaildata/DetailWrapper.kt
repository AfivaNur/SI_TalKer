package com.afiva.appskelurahan.data.dto.detaildata

import kotlinx.serialization.Serializable

@Serializable
data class DetailWrapper(
    val tableName: String,
    val detailData: DetailData
)