package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UsahaData(
    @SerialName("id")
    val id: Int = -1,
    // Non-nullable, assuming DB provides a default
//    @SerialName("id_user")
//    val idUser: Int? = null, // Changed to nullable Int
    @SerialName("id_verifikator")
    val idVerifikator: Int? = null, // Changed to nullable Int

    @SerialName("tanggal_pengajuan")
    val tanggalPengajuan: String = "",
    @SerialName("status")
    val status: String = "",

    @SerialName("nama_usaha")
    val namaUsaha: String = "",
    @SerialName("nik_usaha")
    val nikUsaha: String = "",
    @SerialName("tanggal_lahir_usaha")
    val tanggalLahirUsaha: String = "",
    @SerialName("tempat_lahir_usaha")
    val tempatLahirUsaha: String = "",
    @SerialName("jenis_kelamin_usaha")
    val jenisKelaminUsaha: String = "",
    @SerialName("alamat")
    val alamat: String = "",
    @SerialName("jenis_usaha")
    val jenisUsaha: String = "",
    @SerialName("ukuran_tempat_usaha")
    val ukuranTempatUsaha: String = "",
    @SerialName("lokasi_usaha")
    val lokasiUsaha: String = "",
    @SerialName("lama_usaha")
    val lamaUsaha: String = "",
    @SerialName("tujuan_surat_usaha")
    val tujuanSuratUsaha: String = "",
    @SerialName("selected_rt_usaha")
    val rtUsaha: String = "",
    @SerialName("selected_rw_usaha")
    val rwUsaha: String = "",
    @SerialName("nama_file_surat_pengantar_rt")
    val namaFileSuratPengantarRT: String = "",
    @SerialName("nama_file_persyaratan")
    val namaFilePersyaratan: String = "",
    @SerialName("created_at")
    val created_at: String? = null, // Nullable to match potential null values
    val id_user: Int
)

@Serializable
data class UsahaInsertData(
    val nama_usaha: String,
    val nik_usaha: String,
    val tanggal_lahir_usaha: String,
    val tempat_lahir_usaha: String,
    val jenis_kelamin_usaha: String,
    val alamat: String,
    val jenis_usaha: String,
    val ukuran_tempat_usaha: String,
    val lokasi_usaha: String,
    val lama_usaha: String,
    val tujuan_surat_usaha: String,
    val selected_rt_usaha: String,
    val selected_rw_usaha: String,
    val nama_file_surat_pengantar_rt: String,
    val nama_file_persyaratan: String,
    val id_user: Int?
)