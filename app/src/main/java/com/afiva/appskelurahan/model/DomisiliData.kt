package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName // Pastikan ini diimport

@Serializable
data class DomisiliData(
    @SerialName("id") // Sesuai dengan id SERIAL PRIMARY KEY di DB
    val id: Int = -1, // Menggunakan Int, karena SERIAL PRIMARY KEY di PostgreSQL adalah integer

//    @SerialName("id_user")
//    val idUser: Int = -1,
    @SerialName("id_verifikator")
    val idVerifikator: Int = -1,

    // Kolom-kolom ini ada di Supabase, dan akan dipetakan ke camelCase di Kotlin
    @SerialName("rt")
    val rt: String = "",
    @SerialName("rw")
    val rw: String = "",
    @SerialName("nik")
    val nik: String = "",
    @SerialName("nama")
    val nama: String = "",
    @SerialName("tempat_lahir")
    val tempatLahir: String = "",
    @SerialName("tanggal_lahir")
    val tanggalLahir: String = "",
    @SerialName("jenis_kelamin")
    val jenisKelamin: String = "",
    @SerialName("agama")
    val agama: String = "",
    @SerialName("status_perkawinan")
    val statusPerkawinan: String = "",
    @SerialName("pekerjaan")
    val pekerjaan: String = "",
    @SerialName("alamat")
    val alamat: String = "",
    @SerialName("tujuan_surat")
    val tujuanSurat: String = "",
    @SerialName("nama_file_surat_pengantar_rt")
    val namaFileSuratPengantarRT: String = "",
    @SerialName("nama_file_persyaratan")
    val namaFilePersyaratan: String = "",
    @SerialName("status")
    val status: String = "unverified",

    // Kolom ini sering diisi otomatis dan bisa saja null jika tidak ada data saat di-fetch
    @SerialName("created_at")
    val createdAt: String? = null,
    val id_user: Int?,
)


@Serializable
data class DomisiliInsertData(
    val rt: String,
    val rw: String,
    val nik: String,
    val nama: String,
    val tempat_lahir: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String,
    val agama: String,
    val status_perkawinan: String,
    val pekerjaan: String,
    val alamat: String,
    val tujuan_surat: String,
    val nama_file_surat_pengantar_rt: String,
    val nama_file_persyaratan: String,
)
