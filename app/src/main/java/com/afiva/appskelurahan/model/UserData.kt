package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserData(
    @SerialName("id")
    val id: Int = -1,
    val username: String = "",
    val password: String = "",
    val no_kk: String = "",
    val nik: String = "",
    val role: String = "",


    val rt: String? = null,
    val rw: String? = null,

    @SerialName("tempat_lahir")
    val tempatLahir: String? = null,

    @SerialName("tanggal_lahir")
    val tanggalLahir: String? = null,

    @SerialName("jenis_kelamin")
    val jenisKelamin: String? = null,

    val agama: String? = null,

    @SerialName("status_perkawinan")
    val statusPerkawinan: String? = null,

    val pekerjaan: String? = null,
    val kewarganegaraan: String? = null,
    val alamat: String? = null,

    // ⏱️ Created timestamp
    @SerialName("created_at")
    val createdAt: String? = null
)
