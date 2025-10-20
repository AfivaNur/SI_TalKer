package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(val password: String)