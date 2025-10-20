package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName // Pastikan ini diimpor!
// Hapus import androidx.room.ColumnInfo karena tidak digunakan lagi
// import androidx.room.ColumnInfo

@Serializable
data class PindahDatangData(
    @SerialName("id") // Tambahkan SerialName untuk ID
    val id: Int = -1,

//    @SerialName("id_user")
//    val idUser: Int? = null, // Changed to nullable Int
    @SerialName("id_verifikator")
    val idVerifikator: Int? = null, // Changed to nullable Int

    @SerialName("tanggal_pengajuan")
    val tanggalPengajuan: String = "",
    @SerialName("status") // Tambahkan SerialName untuk status
    val status: String = "unverified",
    @SerialName("kk_asal")
    val kkAsal: String = "",
    @SerialName("nama_kepala_keluarga_asal")
    val namaKepalaKeluargaAsal: String = "",
    @SerialName("alamat_asal")
    val alamatAsal: String = "",
    @SerialName("no_telepon_asal")
    val noTeleponAsal: String = "",
    @SerialName("kode_pos_asal")
    val kodePosAsal: String = "",
    @SerialName("rt_asal")
    val rtAsal: String = "",
    @SerialName("rw_asal")
    val rwAsal: String = "",
    @SerialName("alasan_pindah")
    val alasanPindah: String = "",
    @SerialName("alamat_pindah")
    val alamatPindah: String = "",
    @SerialName("kode_pos_pindah")
    val kodePosPindah: String = "",
    @SerialName("no_telepon_pindah")
    val noTeleponPindah: String = "",
    @SerialName("klasifikasi_pindah")
    val klasifikasiPindah: String = "",
    @SerialName("jenis_kepindahan")
    val jenisKepindahan: String = "",
    @SerialName("status_no_kk_tidak_pindah")
    val statusNoKkTidakPindah: String = "",
    @SerialName("status_no_kk_pindah")
    val statusNoKkPindah: String = "",
    @SerialName("rencana_tanggal_pindah")
    val rencanaTanggalPindah: String = "",
    @SerialName("nik_keluarga_yang_datang_pindah")
    val nikKeluargaYangDatangPindah: String = "",
    @SerialName("nama_keluarga_yang_datang_pindah")
    val namaKeluargaYangDatangPindah: String = "",
    @SerialName("rt_pindah")
    val rtPindah: String = "",
    @SerialName("rw_pindah")
    val rwPindah: String = "",
    @SerialName("nokk_tujuan")
    val noKkTujuan: String = "",
    @SerialName("nama_kepala_keluarga_tujuan")
    val namaKepalaKeluargaTujuan: String = "",
    @SerialName("nik_kepala_keluarga_tujuan")
    val nikKepalaKeluargaTujuan: String = "",
    @SerialName("status_kk_pindah_tujuan")
    val statusKkPindahTujuan: String = "",
    @SerialName("tanggal_kedatangan_tujuan")
    val tanggalKedatanganTujuan: String = "",
    @SerialName("alamat_tujuan")
    val alamatTujuan: String = "",
    @SerialName("nik_keluarga_yang_datang_tujuan")
    val nikKeluargaYangDatangTujuan: String = "",
    @SerialName("nama_keluarga_yang_datang_tujuan")
    val namaKeluargaYangDatangTujuan: String = "",
    @SerialName("rt_tujuan")
    val rtTujuan: String = "",
    @SerialName("rw_tujuan")
    val rwTujuan: String = "",
    @SerialName("nama_file_surat_pengantar_rt")
    val namaFileSuratPengantarRT: String = "",
    @SerialName("nama_file_persyaratan")
    val namaFilePersyaratan: String = "",
    @SerialName("created_at")
    val created_at: String = "",
    val id_user: Int
)

@Serializable
data class PindahDatangInsertData(
    val kk_asal: String,
    val nama_kepala_keluarga_asal: String,
    val alamat_asal: String,
    val no_telepon_asal: String,
    val kode_pos_asal: String,
    val rt_asal: String,
    val rw_asal: String,
    val alasan_pindah: String,
    val alamat_pindah: String,
    val kode_pos_pindah: String,
    val no_telepon_pindah: String,
    val klasifikasi_pindah: String,
    val jenis_kepindahan: String,
    val status_no_kk_tidak_pindah: String,
    val status_no_kk_pindah: String,
    val rencana_tanggal_pindah: String,
    val nik_keluarga_yang_datang_pindah: String,
    val nama_keluarga_yang_datang_pindah: String,
    val rt_pindah: String,
    val rw_pindah: String,
    val nokk_tujuan: String,
    val nama_kepala_keluarga_tujuan: String,
    val nik_kepala_keluarga_tujuan: String,
    val status_kk_pindah_tujuan: String,
    val tanggal_kedatangan_tujuan: String,
    val alamat_tujuan: String,
    val nik_keluarga_yang_datang_tujuan: String,
    val nama_keluarga_yang_datang_tujuan: String,
    val rt_tujuan: String,
    val rw_tujuan: String,
    val nama_file_surat_pengantar_rt: String,
    val nama_file_persyaratan: String,
    val id_user: Int?
)