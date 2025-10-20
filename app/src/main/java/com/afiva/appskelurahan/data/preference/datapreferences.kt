package com.afiva.appskelurahan.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_prefs")

class DataPreferences(private val context: Context) {

    companion object {
        //        PINDAH DATANG
//        Data Kepindahan
        private val ALAMAT_TUJUAN_KEY = stringPreferencesKey("alamat_tujuan")
        private val ALASAN_KEPINDAHAN_KEY = stringPreferencesKey("alasan_kepindahan")
        private val KLASIFIKASI_PINDAH_KEY = stringPreferencesKey("klasifikasi_pindah")
        private val JENIS_KEPINDAHAN_KEY = stringPreferencesKey("jenis_kepindahan")
        private val STATUS_TIDAK_PINDAH_KEY = stringPreferencesKey("status_tidak_pindah")
        private val STATUS_PINDAH_KEY = stringPreferencesKey("status_pindah")
        private val RENCANA_TANGGAL_PINDAH_KEY = stringPreferencesKey("rencana_tanggal_pindah")
        private val KELUARGA_YANG_PINDAH_KEY = stringPreferencesKey("keluarga_yang_pindah")
        private val NIK_KELUARGA_YANG_PINDAH_KEY = stringPreferencesKey("nik_keluarga_yang_pindah")

        //        Data Daerah Asal
        private val NO_KK_KEY = stringPreferencesKey("no_kartu_keluarga")
        private val NAMA_KEPALA_KELUARGA_KEY = stringPreferencesKey("nama_kepala_keluarga")
        private val ALAMAT_ASAL_KEY = stringPreferencesKey("alamat_asal")
        private val RT_ASAL_KEY = stringPreferencesKey("rt_asal")
        private val RW_ASAL_KEY = stringPreferencesKey("rw_asal")
        private val KELURAHAN_ASAL_KEY = stringPreferencesKey("kelurahan_asal")
        private val KECAMATAN_ASAL_KEY = stringPreferencesKey("kecamatan_asal")
        private val KABUPATEN_ASAL_KEY = stringPreferencesKey("kabupaten_asal")
        private val PROVINSI_ASAL_KEY = stringPreferencesKey("provinsi_asal")

        //        Data Daerah tujuan
        private val NO_KK_TUJUAN_KEY = stringPreferencesKey("no_kk_tujuan")
        private val NAMA_KEPALA_KELUARGA_TUJUAN_KEY = stringPreferencesKey("nama_kepala_keluarga_tujuan")
        private val ALAMAT_TUJUAN_DAERAH_TUJUAN_KEY = stringPreferencesKey("alamat_tujuan")
        private val NIK_KEPALA_KELUARGA_TUJUAN_KEY = stringPreferencesKey("nik_kepala_keluarga_tujuan")
        private val STATUS_KK_PINDAH_KEY = stringPreferencesKey("status_kk_pindah")
        private val TANGGAL_KEDATANGAN_KEY = stringPreferencesKey("tanggal_kedatangan")
        private val NIK_KELUARGA_YANG_DATANG_KEY = stringPreferencesKey("nik_keluarga_yang_datang")

        //        KELAHIRAN
        // pelapor
        private val RT_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("rw_Pelapor_Kelahiran")
        private val RW_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("rw_Pelapor_Kelahiran")
        private val NIK_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("nik_Pelapor_Kelahiran")
        private val NAMA_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("nama_Pelapor_Kelahiran")
        private val TEMPAT_LAHIR_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("tempat_lahir_Pelapor_Kelahiran")
        private val TANGGAL_LAHIR_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("tanggal_lahir_Pelapor_Kelahiran")
        private val JENIS_KELAMIN_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("jenis_kelamin_Pelapor_Kelahiran")
        private val ALAMAT_PELAPOR_KELAHIRAN_KEY = stringPreferencesKey("alamat_Pelapor_Kelahiran")


        // anak
        private val NIK_ANAK_KEY = stringPreferencesKey("nik_anak")
        private val NAMA_ANAK_KEY = stringPreferencesKey("nama_anak")
        private val JENIS_KELAMIN_ANAK_KEY = stringPreferencesKey("jenis_kelamin_anak")
        private val TEMPAT_LAHIR_ANAK_KEY = stringPreferencesKey("tempat_lahir_anak")
        private val TANGGAL_LAHIR_ANAK_KEY = stringPreferencesKey("tanggal_lahir_anak")
        private val PUKUL_KELAHIRAN_KEY = stringPreferencesKey("pukul_lahir")
        private val KELAHIRAN_KE_KEY = stringPreferencesKey("kelahiran_ke")
        private val PENOLONG_KELAHIRAN_KEY = stringPreferencesKey("penolong_kelahiran")
        private val BERAT_ANAK_KEY = stringPreferencesKey("berat_anak")
        private val PANJANG_ANAK_KEY = stringPreferencesKey("panjang_anak")

        // ibu
        private val IBU_KELAHIRAN_NIK_KEY = stringPreferencesKey("ibu_kelahiran_nik")
        private val IBU_KELAHIRAN_NAMA_KEY = stringPreferencesKey("ibu_kelahiran_nama")
        private val IBU_KELAHIRAN_TANGGAL_LAHIR_KEY = stringPreferencesKey("ibu_kelahiran_tanggal_lahir")
        private val IBU_KELAHIRAN_TEMPAT_LAHIR_KEY = stringPreferencesKey("ibu_kelahiran_tempat_lahir")
        private val IBU_KELAHIRAN_UMUR_KEY = stringPreferencesKey("ibu_kelahiran_umur")
        private val IBU_KELAHIRAN_PEKERJAAN_KEY = stringPreferencesKey("ibu_kelahiran_pekerjaan")
        private val IBU_KELAHIRAN_ALAMAT_KEY = stringPreferencesKey("ibu_kelahiran_alamat")
        private val IBU_KELAHIRAN_KEWARGANEGARAAN_KEY = stringPreferencesKey("ibu_kelahiran_kewarganegaraan")
        private val IBU_KELAHIRAN_KEBANGSAAN_KEY = stringPreferencesKey("ibu_kelahiran_kebangsaan")
        private val IBU_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY = stringPreferencesKey("ibu_kelahiran_tanggal_pernikahan")

        // ayah
        private val AYAH_KELAHIRAN_NIK_KEY = stringPreferencesKey("ayah_kelahiran_nik")
        private val AYAH_KELAHIRAN_NAMA_KEY = stringPreferencesKey("ayah_kelahiran_nama")
        private val AYAH_KELAHIRAN_TANGGAL_LAHIR_KEY = stringPreferencesKey("ayah_kelahiran_tanggal_lahir")
        private val AYAH_KELAHIRAN_TEMPAT_LAHIR_KEY = stringPreferencesKey("ayah_kelahiran_tempat_lahir")
        private val AYAH_KELAHIRAN_UMUR_KEY = stringPreferencesKey("ayah_kelahiran_umur")
        private val AYAH_KELAHIRAN_PEKERJAAN_KEY = stringPreferencesKey("ayah_kelahiran_pekerjaan")
        private val AYAH_KELAHIRAN_ALAMAT_KEY = stringPreferencesKey("ayah_kelahiran_alamat")
        private val AYAH_KELAHIRAN_KEWARGANEGARAAN_KEY = stringPreferencesKey("ayah_kelahiran_kewarganegaraan")
        private val AYAH_KELAHIRAN_KEBANGSAAN_KEY = stringPreferencesKey("ayah_kelahiran_kebangsaan")
        private val AYAH_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY = stringPreferencesKey("ayah_kelahiran_tanggal_pernikahan")

        // saksi 1
        private val SAKSI1_NIK_KELAHIRAN_KEY = stringPreferencesKey("saksi1_nik")
        private val SAKSI1_NAMA_KELAHIRAN_KEY = stringPreferencesKey("saksi1_nama")
        private val SAKSI1_TANGGAL_LAHIR_KELAHIRAN_KEY = stringPreferencesKey("saksi1_tanggal_lahir")
        private val SAKSI1_TEMPAT_LAHIR_KELAHIRAN_KEY = stringPreferencesKey("saksi1_tempat_lahir")
        private val SAKSI1_JENIS_KELAMIN_KELAHIRAN_KEY = stringPreferencesKey("saksi1_jenis_kelamin")
        private val SAKSI1_UMUR_KELAHIRAN_KEY = stringPreferencesKey("saksi1_umur")
        private val SAKSI1_PEKERJAAN_KELAHIRAN_KEY = stringPreferencesKey("saksi1_pekerjaan")
        private val SAKSI1_ALAMAT_KELAHIRAN_KEY = stringPreferencesKey("saksi1_alamat")

        // saksi 2
        private val SAKSI2_NIK_KELAHIRAN_KEY = stringPreferencesKey("saksi2_nik")
        private val SAKSI2_NAMA_KELAHIRAN_KEY = stringPreferencesKey("saksi2_nama")
        private val SAKSI2_TANGGAL_LAHIR_KELAHIRAN_KEY = stringPreferencesKey("saksi2_tanggal_lahir")
        private val SAKSI2_TEMPAT_LAHIR_KELAHIRAN_KEY = stringPreferencesKey("saksi2_tempat_lahir")
        private val SAKSI2_JENIS_KELAMIN_KELAHIRAN_KEY = stringPreferencesKey("saksi2_jenis_kelamin")
        private val SAKSI2_UMUR_KELAHIRAN_KEY = stringPreferencesKey("saksi2_umur")
        private val SAKSI2_PEKERJAAN_KELAHIRAN_KEY = stringPreferencesKey("saksi2_pekerjaan")
        private val SAKSI2_ALAMAT_KELAHIRAN_KEY = stringPreferencesKey("saksi2_alamat")

    }
    //        KEMATIAN
//        Data Pelapor Kematian
    private val NAMA_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("nama_pelapor_kematian")
    private val TANGGAL_LAHIR_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("tanggal_lahir_pelapor_kematian")
    private val TEMPAT_LAHIR_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("tempat_lahir_pelapor_kematian")
    private val JENIS_KELAMIN_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("jenis_kelamin_pelapor_kematian")
    private val NIK_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("nik_pelapor_kematian")
    private val ALAMAT_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("alamat_pelapor_kematian")
    private val AGAMA_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("agama_pelapor_kematian")
    private val PEKERJAAN_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("pekerjaan_pelapor_kematian")
    private val TUJUAN_SURAT_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("tujuan_surat_pelapor_kematian")
    private val RT_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("rt_pelapor_kematian")
    private val RW_PELAPOR_KEMATIAN_KEY = stringPreferencesKey("rw_pelapor_kematian")

    // — KEMATIAN (Jenazah)
    private val NIK_JENAZAH_KEY                 = stringPreferencesKey("nik_jenazah")
    private val NAMA_JENAZAH_KEY                = stringPreferencesKey("nama_jenazah")
    private val JENIS_KELAMIN_JENAZAH_KEY       = stringPreferencesKey("jenis_kelamin_jenazah")
    private val TEMPAT_LAHIR_JENAZAH_KEY        = stringPreferencesKey("tempat_lahir_jenazah")
    private val TANGGAL_LAHIR_JENAZAH_KEY       = stringPreferencesKey("tanggal_lahir_jenazah")
    private val UMUR_JENAZAH_KEY                = stringPreferencesKey("umur_jenazah")
    private val AGAMA_JENAZAH_KEY               = stringPreferencesKey("agama_jenazah")
    private val PEKERJAAN_JENAZAH_KEY           = stringPreferencesKey("pekerjaan_jenazah")
    private val ALAMAT_JENAZAH_KEY              = stringPreferencesKey("alamat_jenazah")
    private val ANAK_KE_JENAZAH_KEY             = stringPreferencesKey("anak_ke_jenazah")
    private val TANGGAL_KEMATIAN_JENAZAH_KEY    = stringPreferencesKey("tanggal_kematian_jenazah")
    private val PUKUL_JENAZAH_KEY               = stringPreferencesKey("pukul_jenazah")
    private val SEBAB_KEMATIAN_JENAZAH_KEY      = stringPreferencesKey("sebab_kematian_jenazah")
    private val YANG_MENERANGKAN_KEY            = stringPreferencesKey("yang_menerangkan")

    // IBU KEMATIAN
    private val NIK_IBU_KEMATIAN_KEY = stringPreferencesKey("nik_ibu_kematian")
    private val NAMA_IBU_KEMATIAN_KEY = stringPreferencesKey("nama_ibu_kematian")
    private val TEMPAT_LAHIR_IBU_KEMATIAN_KEY = stringPreferencesKey("tempat_lahir_ibu_kematian")
    private val TANGGAL_LAHIR_IBU_KEMATIAN_KEY = stringPreferencesKey("tanggal_lahir_ibu_kematian")
    private val AGAMA_IBU_KEMATIAN_KEY = stringPreferencesKey("agama_ayah_kematian")
    private val PEKERJAAN_IBU_KEMATIAN_KEY = stringPreferencesKey("pekerjaan_ibu_kematian")
    private val ALAMAT_IBU_KEMATIAN_KEY = stringPreferencesKey("alamat_ibu_kematian")


    // AYAH KEMATIAN
    private val NIK_AYAH_KEMATIAN_KEY = stringPreferencesKey("nik_ayah_kematian")
    private val NAMA_AYAH_KEMATIAN_KEY = stringPreferencesKey("nama_ayah_kematian")
    private val TEMPAT_LAHIR_AYAH_KEMATIAN_KEY = stringPreferencesKey("tempat_lahir_ayah_kematian")
    private val TANGGAL_LAHIR_AYAH_KEMATIAN_KEY = stringPreferencesKey("tanggal_lahir_ayah_kematian")
    private val AGAMA_AYAH_KEMATIAN_KEY = stringPreferencesKey("agama_ayah_kematian")
    private val PEKERJAAN_AYAH_KEMATIAN_KEY = stringPreferencesKey("pekerjaan_ayah_kematian")
    private val ALAMAT_AYAH_KEMATIAN_KEY = stringPreferencesKey("alamat_ayah_kematian")

//saksi1 kematian
    private val SAKSI1_NIK_KEMATIAN_KEY = stringPreferencesKey("saksi1_nik")
    private val SAKSI1_NAMA_KEMATIAN_KEY = stringPreferencesKey("saksi1_nama")
    private val SAKSI1_TANGGAL_LAHIR_KEMATIAN_KEY = stringPreferencesKey("saksi1_tanggal_lahir")
    private val SAKSI1_TEMPAT_LAHIR_KEMATIAN_KEY = stringPreferencesKey("saksi1_tempat_lahir")
    private val SAKSI1_JENIS_KELAMIN_KEMATIAN_KEY = stringPreferencesKey("saksi1_jenis_kelamin")
    private val SAKSI1_AGAMA_KEMATIAN_KEY = stringPreferencesKey("saksi1_agama_kematian")
    private val SAKSI1_PEKERJAAN_KEMATIAN_KEY = stringPreferencesKey("saksi1_pekerjaan")
    private val SAKSI1_ALAMAT_KEMATIAN_KEY = stringPreferencesKey("saksi1_alamat")
//saksi2 kematian
    private val SAKSI2_NIK_KEMATIAN_KEY = stringPreferencesKey("saksi2_nik")
    private val SAKSI2_NAMA_KEMATIAN_KEY = stringPreferencesKey("saksi2_nama")
    private val SAKSI2_TANGGAL_LAHIR_KEMATIAN_KEY = stringPreferencesKey("saksi2_tanggal_lahir")
    private val SAKSI2_TEMPAT_LAHIR_KEMATIAN_KEY = stringPreferencesKey("saksi2_tempat_lahir")
    private val SAKSI2_JENIS_KELAMIN_KEMATIAN_KEY = stringPreferencesKey("saksi2_jenis_kelamin")
    private val SAKSI2_AGAMA_KEMATIAN_KEY = stringPreferencesKey("saksi1_agama_kematian")
    private val SAKSI2_PEKERJAAN_KEMATIAN_KEY = stringPreferencesKey("saksi2_pekerjaan")
    private val SAKSI2_ALAMAT_KEMATIAN_KEY = stringPreferencesKey("saksi2_alamat")

//    KETERANGAN NIKAH
//    N.1
    private val NAMA_LENGKAP_CALON_KEY = stringPreferencesKey("nama_lengkap_calon")
    private val JENIS_KELAMIN_CALON_KEY = stringPreferencesKey("jenis_kelamin_calon")
    private val TEMPAT_LAHIR_CALON_KEY = stringPreferencesKey("tempat_lahir_calon")
    private val KEWARGANEGARAAN_CALON_KEY = stringPreferencesKey("kewarganegaraan_calon")
    private val TANGGAL_LAHIR_CALON_KEY = stringPreferencesKey("tanggal_lahir_calon")
    private val NIK_CALON_KEY = stringPreferencesKey("nik_calon")
    private val AGAMA_CALON_KEY = stringPreferencesKey("agama_calon")
    private val PEKERJAAN_CALON_KEY = stringPreferencesKey("pekerjaan_calon")
    private val LAINNYA_CALON_KEY = stringPreferencesKey("lainnya_calon")
    private val ALAMAT_CALON_KEY = stringPreferencesKey("alamat_calon")
    private val BIN_CALON_KEY = stringPreferencesKey("bin_calon")
    private val STATUS_PERKAWINAN_CALON_KEY = stringPreferencesKey("status_perkawinan_calon")
    private val NAMA_PASANGAN_DULU_KEY = stringPreferencesKey("nama_pasangan_dulu")

    // Ayah
    private val NAMA_AYAH_CALON_KEY = stringPreferencesKey("nama_ayah_calon")
    private val TEMPAT_LAHIR_AYAH_CALON_KEY = stringPreferencesKey("tempat_lahir_ayah_calon")
    private val TANGGAL_LAHIR_AYAH_CALON_KEY = stringPreferencesKey("tanggal_lahir_ayah_calon")
    private val NIK_AYAH_CALON_KEY = stringPreferencesKey("nik_ayah_calon")
    private val KEWARGANEGARAAN_AYAH_CALON_KEY = stringPreferencesKey("kewarganegaraan_ayah_calon")
    private val AGAMA_AYAH_CALON_KEY = stringPreferencesKey("agama_ayah_calon")
    private val PEKERJAAN_AYAH_CALON_KEY = stringPreferencesKey("pekerjaan_ayah_calon")
    private val ALAMAT_AYAH_CALON_KEY = stringPreferencesKey("alamat_ayah_calon")

    // Ibu
    private val NAMA_IBU_CALON_KEY = stringPreferencesKey("nama_ibu_calon")
    private val TEMPAT_LAHIR_IBU_CALON_KEY = stringPreferencesKey("tempat_lahir_ibu_calon")
    private val TANGGAL_LAHIR_IBU_CALON_KEY = stringPreferencesKey("tanggal_lahir_ibu_calon")
    private val NIK_IBU_CALON_KEY = stringPreferencesKey("nik_ibu_calon")
    private val KEWARGANEGARAAN_IBU_CALON_KEY = stringPreferencesKey("kewarganegaraan_ibu_calon")
    private val AGAMA_IBU_CALON_KEY = stringPreferencesKey("agama_ibu_calon")
    private val PEKERJAAN_IBU_CALON_KEY = stringPreferencesKey("pekerjaan_ibu_calon")
    private val ALAMAT_IBU_CALON_KEY = stringPreferencesKey("alamat_ibu_calon")

//    N.3
    // Calon Suami
    private val NAMA_LENGKAP_SUAMI_KEY = stringPreferencesKey("nama_lengkap_suami")
    private val BIN_SUAMI_KEY = stringPreferencesKey("bin_suami")
    private val TEMPAT_LAHIR_SUAMI_KEY = stringPreferencesKey("tempat_lahir_suami")
    private val TANGGAL_LAHIR_SUAMI_KEY = stringPreferencesKey("tanggal_lahir_suami")
    private val NIK_SUAMI_KEY = stringPreferencesKey("nik_suami")
    private val KEWARGANEGARAAN_SUAMI_KEY = stringPreferencesKey("kewarganegaraan_suami")
    private val AGAMA_SUAMI_KEY = stringPreferencesKey("agama_suami")
    private val PEKERJAAN_SUAMI_KEY = stringPreferencesKey("pekerjaan_suami")
    private val ALAMAT_SUAMI_KEY = stringPreferencesKey("alamat_suami")

    // Calon Istri
    private val NAMA_LENGKAP_ISTRI_KEY = stringPreferencesKey("nama_lengkap_istri")
    private val BINTI_ISTRI_KEY = stringPreferencesKey("binti_istri")
    private val TEMPAT_LAHIR_ISTRI_KEY = stringPreferencesKey("tempat_lahir_istri")
    private val TANGGAL_LAHIR_ISTRI_KEY = stringPreferencesKey("tanggal_lahir_istri")
    private val NIK_ISTRI_KEY = stringPreferencesKey("nik_istri")
    private val KEWARGANEGARAAN_ISTRI_KEY = stringPreferencesKey("kewarganegaraan_istri")
    private val AGAMA_ISTRI_KEY = stringPreferencesKey("agama_istri")
    private val PEKERJAAN_ISTRI_KEY = stringPreferencesKey("pekerjaan_istri")
    private val ALAMAT_ISTRI_KEY = stringPreferencesKey("alamat_istri")

    // N.4
    // Calon Pria
    private val NIK_PRIA_KEY = stringPreferencesKey("nik_pria")
    private val NAMA_LENGKAP_PRIA_KEY = stringPreferencesKey("nama_lengkap_pria")
    private val TANGGAL_LAHIR_PRIA_KEY = stringPreferencesKey("tanggal_lahir_pria")
    private val TEMPAT_LAHIR_PRIA_KEY = stringPreferencesKey("tempat_lahir_pria")
    private val KEWARGANEGARAAN_PRIA_KEY = stringPreferencesKey("kewarganegaraan_pria")
    private val AGAMA_PRIA_KEY = stringPreferencesKey("agama_pria")
    private val PEKERJAAN_PRIA_KEY = stringPreferencesKey("pekerjaan_pria")
    private val ALAMAT_PRIA_KEY = stringPreferencesKey("alamat_pria")

    // Ayah Pria
    private val NIK_AYAH_PRIA_KEY = stringPreferencesKey("nik_ayah_pria")
    private val NAMA_LENGKAP_AYAH_PRIA_KEY = stringPreferencesKey("nama_lengkap_ayah_pria")
    private val TANGGAL_LAHIR_AYAH_PRIA_KEY = stringPreferencesKey("tanggal_lahir_ayah_pria")
    private val TEMPAT_LAHIR_AYAH_PRIA_KEY = stringPreferencesKey("tempat_lahir_ayah_pria")
    private val KEWARGANEGARAAN_AYAH_PRIA_KEY = stringPreferencesKey("kewarganegaraan_ayah_pria")
    private val AGAMA_AYAH_PRIA_KEY = stringPreferencesKey("agama_ayah_pria")
    private val PEKERJAAN_AYAH_PRIA_KEY = stringPreferencesKey("pekerjaan_ayah_pria")
    private val ALAMAT_AYAH_PRIA_KEY = stringPreferencesKey("alamat_ayah_pria")

    // Ibu Pria
    private val NIK_IBU_PRIA_KEY = stringPreferencesKey("nik_ibu_pria")
    private val NAMA_LENGKAP_IBU_PRIA_KEY = stringPreferencesKey("nama_lengkap_ibu_pria")
    private val TANGGAL_LAHIR_IBU_PRIA_KEY = stringPreferencesKey("tanggal_lahir_ibu_pria")
    private val TEMPAT_LAHIR_IBU_PRIA_KEY = stringPreferencesKey("tempat_lahir_ibu_pria")
    private val KEWARGANEGARAAN_IBU_PRIA_KEY = stringPreferencesKey("kewarganegaraan_ibu_pria")
    private val AGAMA_IBU_PRIA_KEY = stringPreferencesKey("agama_ibu_pria")
    private val PEKERJAAN_IBU_PRIA_KEY = stringPreferencesKey("pekerjaan_ibu_pria")
    private val ALAMAT_IBU_PRIA_KEY = stringPreferencesKey("alamat_ibu_pria")

    // Calon Wanita
    private val NIK_WANITA_KEY = stringPreferencesKey("nik_wanita")
    private val NAMA_LENGKAP_WANITA_KEY = stringPreferencesKey("nama_lengkap_wanita")
    private val TANGGAL_LAHIR_WANITA_KEY = stringPreferencesKey("tanggal_lahir_wanita")
    private val TEMPAT_LAHIR_WANITA_KEY = stringPreferencesKey("tempat_lahir_wanita")
    private val KEWARGANEGARAAN_WANITA_KEY = stringPreferencesKey("kewarganegaraan_wanita")
    private val AGAMA_WANITA_KEY = stringPreferencesKey("agama_wanita")
    private val PEKERJAAN_WANITA_KEY = stringPreferencesKey("pekerjaan_wanita")
    private val ALAMAT_WANITA_KEY = stringPreferencesKey("alamat_wanita")

    // Ayah Wanita
    private val NIK_AYAH_WANITA_KEY = stringPreferencesKey("nik_ayah_wanita")
    private val NAMA_LENGKAP_AYAH_WANITA_KEY = stringPreferencesKey("nama_lengkap_ayah_wanita")
    private val TANGGAL_LAHIR_AYAH_WANITA_KEY = stringPreferencesKey("tanggal_lahir_ayah_wanita")
    private val TEMPAT_LAHIR_AYAH_WANITA_KEY = stringPreferencesKey("tempat_lahir_ayah_wanita")
    private val KEWARGANEGARAAN_AYAH_WANITA_KEY = stringPreferencesKey("kewarganegaraan_ayah_wanita")
    private val AGAMA_AYAH_WANITA_KEY = stringPreferencesKey("agama_ayah_wanita")
    private val PEKERJAAN_AYAH_WANITA_KEY = stringPreferencesKey("pekerjaan_ayah_wanita")
    private val ALAMAT_AYAH_WANITA_KEY = stringPreferencesKey("alamat_ayah_wanita")

    // Ibu Wanita
    private val NIK_IBU_WANITA_KEY = stringPreferencesKey("nik_ibu_wanita")
    private val NAMA_LENGKAP_IBU_WANITA_KEY = stringPreferencesKey("nama_lengkap_ibu_wanita")
    private val TANGGAL_LAHIR_IBU_WANITA_KEY = stringPreferencesKey("tanggal_lahir_ibu_wanita")
    private val TEMPAT_LAHIR_IBU_WANITA_KEY = stringPreferencesKey("tempat_lahir_ibu_wanita")
    private val KEWARGANEGARAAN_IBU_WANITA_KEY = stringPreferencesKey("kewarganegaraan_ibu_wanita")
    private val AGAMA_IBU_WANITA_KEY = stringPreferencesKey("agama_ibu_wanita")
    private val PEKERJAAN_IBU_WANITA_KEY = stringPreferencesKey("pekerjaan_ibu_wanita")
    private val ALAMAT_IBU_WANITA_KEY = stringPreferencesKey("alamat_ibu_wanita")





    // MENYIMPAN DATA

//PINDAH DATANG
    /* ---------- PINDAH DATANG (Data Kepindahan) ---------- */

    suspend fun saveALAMAT_TUJUAN_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_TUJUAN_KEY] = value }
    }

    suspend fun saveALASAN_KEPINDAHAN_KEY(value: String) {
        context.dataStore.edit { it[ALASAN_KEPINDAHAN_KEY] = value }
    }

    suspend fun saveKLASIFIKASI_PINDAH_KEY(value: String) {
        context.dataStore.edit { it[KLASIFIKASI_PINDAH_KEY] = value }
    }

    suspend fun saveJENIS_KEPINDAHAN_KEY(value: String) {
        context.dataStore.edit { it[JENIS_KEPINDAHAN_KEY] = value }
    }

    suspend fun saveSTATUS_TIDAK_PINDAH_KEY(value: String) {
        context.dataStore.edit { it[STATUS_TIDAK_PINDAH_KEY] = value }
    }

    suspend fun saveSTATUS_PINDAH_KEY(value: String) {
        context.dataStore.edit { it[STATUS_PINDAH_KEY] = value }
    }

    suspend fun saveRENCANA_TANGGAL_PINDAH_KEY(value: String) {
        context.dataStore.edit { it[RENCANA_TANGGAL_PINDAH_KEY] = value }
    }

    suspend fun saveKELUARGA_YANG_PINDAH_KEY(value: String) {
        context.dataStore.edit { it[KELUARGA_YANG_PINDAH_KEY] = value }
    }

    suspend fun saveNIK_KELUARGA_YANG_PINDAH_KEY(value: String) {
        context.dataStore.edit { it[NIK_KELUARGA_YANG_PINDAH_KEY] = value }
    }

    /* ---------- PINDAH DATANG (Data Daerah Asal) ---------- */

    suspend fun saveNO_KK_KEY(value: String) {
        context.dataStore.edit { it[NO_KK_KEY] = value }
    }

    suspend fun saveNAMA_KEPALA_KELUARGA_KEY(value: String) {
        context.dataStore.edit { it[NAMA_KEPALA_KELUARGA_KEY] = value }
    }

    suspend fun saveALAMAT_ASAL_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_ASAL_KEY] = value }
    }

    suspend fun saveRT_ASAL_KEY(value: String) {
        context.dataStore.edit { it[RT_ASAL_KEY] = value }
    }

    suspend fun saveRW_ASAL_KEY(value: String) {
        context.dataStore.edit { it[RW_ASAL_KEY] = value }
    }

    suspend fun saveKELURAHAN_ASAL_KEY(value: String) {
        context.dataStore.edit { it[KELURAHAN_ASAL_KEY] = value }
    }

    suspend fun saveKECAMATAN_ASAL_KEY(value: String) {
        context.dataStore.edit { it[KECAMATAN_ASAL_KEY] = value }
    }

    suspend fun saveKABUPATEN_ASAL_KEY(value: String) {
        context.dataStore.edit { it[KABUPATEN_ASAL_KEY] = value }
    }

    suspend fun savePROVINSI_ASAL_KEY(value: String) {
        context.dataStore.edit { it[PROVINSI_ASAL_KEY] = value }
    }

    /* ---------- PINDAH DATANG (Data Daerah Tujuan) ---------- */

    suspend fun saveNO_KK_TUJUAN_KEY(value: String) {
        context.dataStore.edit { it[NO_KK_TUJUAN_KEY] = value }
    }

    suspend fun saveNIK_KELUARGA_YANG_DATANG_KEY(value: String) {
        context.dataStore.edit { it[NIK_KELUARGA_YANG_DATANG_KEY] = value }
    }

    suspend fun saveNAMA_KEPALA_KELUARGA_TUJUAN_KEY(value: String) {
        context.dataStore.edit { it[NAMA_KEPALA_KELUARGA_TUJUAN_KEY] = value }
    }

    suspend fun saveALAMAT_TUJUAN_DAERAH_TUJUAN_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_TUJUAN_DAERAH_TUJUAN_KEY] = value }
    }

    suspend fun saveNIK_KEPALA_KELUARGA_TUJUAN_KEY(value: String) {
        context.dataStore.edit { it[NIK_KEPALA_KELUARGA_TUJUAN_KEY] = value }
    }

    suspend fun saveSTATUS_KK_PINDAH_KEY(value: String) {
        context.dataStore.edit { it[STATUS_KK_PINDAH_KEY] = value }
    }

    suspend fun saveTANGGAL_KEDATANGAN_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_KEDATANGAN_KEY] = value }
    }


    //KELAHIRAN
    /* ---------- KELAHIRAN (PELAPOR KELAHIRAN) ---------- */
    suspend fun saveRT_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[RT_PELAPOR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveRW_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[RW_PELAPOR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveNIK_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[NIK_PELAPOR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveNAMA_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[NAMA_PELAPOR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_PELAPOR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_PELAPOR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveJENIS_KELAMIN_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[JENIS_KELAMIN_PELAPOR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveALAMAT_PELAPOR_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_PELAPOR_KELAHIRAN_KEY] = value }
    }

    /* ---------- KELAHIRAN (ANAK) ---------- */
    suspend fun saveNIK_ANAK_KEY(value: String) {
        context.dataStore.edit { it[NIK_ANAK_KEY] = value }
    }

    suspend fun saveNAMA_ANAK_KEY(value: String) {
        context.dataStore.edit { it[NAMA_ANAK_KEY] = value }
    }

    suspend fun saveJENIS_KELAMIN_ANAK_KEY(value: String) {
        context.dataStore.edit { it[JENIS_KELAMIN_ANAK_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_ANAK_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_ANAK_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_ANAK_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_ANAK_KEY] = value }
    }

    suspend fun savePUKUL_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[PUKUL_KELAHIRAN_KEY] = value}
    }

    suspend fun saveKELAHIRAN_KE_KEY(value: String) {
        context.dataStore.edit { it[KELAHIRAN_KE_KEY] = value }
    }

    suspend fun savePENOLONG_KELAHIRAN_KEY(value: String) {
        context.dataStore.edit { it[PENOLONG_KELAHIRAN_KEY] = value }
    }

    suspend fun saveBERAT_ANAK_KEY(value: String) {
        context.dataStore.edit { it[BERAT_ANAK_KEY] = value }
    }

    suspend fun savePANJANG_ANAK_KEY(value: String) {
        context.dataStore.edit { it[PANJANG_ANAK_KEY] = value }
    }

    /* ---------- KELAHIRAN (IBU) ---------- */
    suspend fun saveIBU_KELAHIRAN_NIK_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_NIK_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_NAMA_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_NAMA_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_TANGGAL_LAHIR_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_TANGGAL_LAHIR_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_TEMPAT_LAHIR_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_TEMPAT_LAHIR_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_UMUR_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_UMUR_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_PEKERJAAN_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_PEKERJAAN_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_ALAMAT_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_ALAMAT_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_KEWARGANEGARAAN_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_KEWARGANEGARAAN_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_KEBANGSAAN_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_KEBANGSAAN_KEY] = value }
    }

    suspend fun saveIBU_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY(value: String) {
        context.dataStore.edit { it[IBU_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY] = value }
    }

    /* ---------- KELAHIRAN (AYAH) ---------- */
    suspend fun saveAYAH_KELAHIRAN_NIK_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_NIK_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_NAMA_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_NAMA_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_TANGGAL_LAHIR_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_TANGGAL_LAHIR_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_TEMPAT_LAHIR_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_TEMPAT_LAHIR_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_UMUR_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_UMUR_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_PEKERJAAN_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_PEKERJAAN_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_ALAMAT_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_ALAMAT_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_KEWARGANEGARAAN_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_KEWARGANEGARAAN_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_KEBANGSAAN_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_KEBANGSAAN_KEY] = value }
    }

    suspend fun saveAYAH_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY(value: String) {
        context.dataStore.edit { it[AYAH_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY] = value }
    }

    /* ---------- SAKSI 1 SAVE FUNCTIONS ---------- */
    suspend fun saveSAKSI1_NIK_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_NIK_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI1_NAMA_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_NAMA_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI1_TANGGAL_LAHIR_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_TANGGAL_LAHIR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI1_TEMPAT_LAHIR_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_TEMPAT_LAHIR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI1_JENIS_KELAMIN_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_JENIS_KELAMIN_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI1_UMUR_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_UMUR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI1_PEKERJAAN_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_PEKERJAAN_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI1_ALAMAT_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI1_ALAMAT_KELAHIRAN_KEY] = value }
    }

    /* ---------- SAKSI 2 SAVE FUNCTIONS ---------- */
    suspend fun saveSAKSI2_NIK_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_NIK_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI2_NAMA_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_NAMA_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI2_TANGGAL_LAHIR_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_TANGGAL_LAHIR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI2_TEMPAT_LAHIR_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_TEMPAT_LAHIR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI2_JENIS_KELAMIN_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_JENIS_KELAMIN_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI2_UMUR_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_UMUR_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI2_PEKERJAAN_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_PEKERJAAN_KELAHIRAN_KEY] = value }
    }

    suspend fun saveSAKSI2_ALAMAT_KELAHIRAN(value: String) {
        context.dataStore.edit { it[SAKSI2_ALAMAT_KELAHIRAN_KEY] = value }
    }

    // KEMATIAN
    /* ---------- KEMATIAN (PELAPOR KEMATIAN) ---------- */
    suspend fun saveNAMA_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { preferences ->
            preferences[NAMA_PELAPOR_KEMATIAN_KEY] = value
        }
    }

    suspend fun saveNIK_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[NIK_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveJENIS_KELAMIN_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[JENIS_KELAMIN_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveAGAMA_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun savePEKERJAAN_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveALAMAT_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveTUJUAN_SURAT_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[TUJUAN_SURAT_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveRT_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[RT_PELAPOR_KEMATIAN_KEY] = value }
    }

    suspend fun saveRW_PELAPOR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[RW_PELAPOR_KEMATIAN_KEY] = value }
    }

    /* ---------- KEMATIAN (JENAZAH) ---------- */
    suspend fun saveNIK_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[NIK_JENAZAH_KEY] = value }
    }

    suspend fun saveNAMA_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[NAMA_JENAZAH_KEY] = value }
    }

    suspend fun saveJENIS_KELAMIN_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[JENIS_KELAMIN_JENAZAH_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_JENAZAH_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_JENAZAH_KEY] = value }
    }

    suspend fun saveUMUR_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[UMUR_JENAZAH_KEY] = value }
    }

    suspend fun saveAGAMA_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_JENAZAH_KEY] = value }
    }

    suspend fun savePEKERJAAN_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_JENAZAH_KEY] = value }
    }

    suspend fun saveALAMAT_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_JENAZAH_KEY] = value }
    }

    suspend fun saveANAK_KE_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[ANAK_KE_JENAZAH_KEY] = value }
    }

    suspend fun saveTANGGAL_KEMATIAN_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_KEMATIAN_JENAZAH_KEY] = value }
    }

    suspend fun savePUKUL_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[PUKUL_JENAZAH_KEY] = value }
    }

    suspend fun saveSEBAB_KEMATIAN_JENAZAH_KEY(value: String) {
        context.dataStore.edit { it[SEBAB_KEMATIAN_JENAZAH_KEY] = value }
    }

    suspend fun saveYANG_MENERANGKAN_KEY(value: String) {
        context.dataStore.edit { it[YANG_MENERANGKAN_KEY] = value }
    }


    /* ---------- KEMATIAN (IBU KEMATIAN) ---------- */
    suspend fun saveNIK_IBU_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[NIK_IBU_KEMATIAN_KEY] = value }
    }

    suspend fun saveNAMA_IBU_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[NAMA_IBU_KEMATIAN_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_IBU_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_IBU_KEMATIAN_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_IBU_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_IBU_KEMATIAN_KEY] = value }
    }

    suspend fun saveAGAMA_IBU_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_IBU_KEMATIAN_KEY] = value }
    }

    suspend fun savePEKERJAAN_IBU_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_IBU_KEMATIAN_KEY] = value }
    }

    suspend fun saveALAMAT_IBU_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_IBU_KEMATIAN_KEY] = value }
    }

    /* ---------- KEMATIAN (AYAH KEMATIAN) ---------- */
    suspend fun saveNIK_AYAH_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[NIK_AYAH_KEMATIAN_KEY] = value }
    }

    suspend fun saveNAMA_AYAH_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[NAMA_AYAH_KEMATIAN_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_AYAH_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_AYAH_KEMATIAN_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_AYAH_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_AYAH_KEMATIAN_KEY] = value }
    }

    suspend fun saveAGAMA_AYAH_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_AYAH_KEMATIAN_KEY] = value }
    }

    suspend fun savePEKERJAAN_AYAH_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_AYAH_KEMATIAN_KEY] = value }
    }

    suspend fun saveALAMAT_AYAH_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_AYAH_KEMATIAN_KEY] = value }
    }

    /* ---------- KEMATIAN (SAKSI 1) ---------- */
    suspend fun saveSAKSI1_NIK_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI1_NIK_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI1_NAMA_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI1_NAMA_KEMATIAN_KEY] = value }
    }
    suspend fun saveSAKSI1_TEMPAT_LAHIR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI1_TEMPAT_LAHIR_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI1_TANGGAL_LAHIR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI1_TANGGAL_LAHIR_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI1_JENIS_KELAMIN_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI1_JENIS_KELAMIN_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI1_PEKERJAAN_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI1_PEKERJAAN_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI1_ALAMAT_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI1_ALAMAT_KEMATIAN_KEY] = value }
    }

    /* ---------- KEMATIAN (SAKSI 2) ---------- */
    suspend fun saveSAKSI2_NIK_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI2_NIK_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI2_NAMA_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI2_NAMA_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI2_TANGGAL_LAHIR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI2_TANGGAL_LAHIR_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI2_TEMPAT_LAHIR_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI2_TEMPAT_LAHIR_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI2_JENIS_KELAMIN_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI2_JENIS_KELAMIN_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI2_PEKERJAAN_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI2_PEKERJAAN_KEMATIAN_KEY] = value }
    }

    suspend fun saveSAKSI2_ALAMAT_KEMATIAN_KEY(value: String) {
        context.dataStore.edit { it[SAKSI2_ALAMAT_KEMATIAN_KEY] = value }
    }

    // NIKAH (N.1)
// ------------------------- CALON PENGANTIN -------------------------

    suspend fun saveNAMA_LENGKAP_CALON_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_CALON_KEY] = value }
    }

    suspend fun saveJENIS_KELAMIN_CALON_KEY(value: String) {
        context.dataStore.edit { it[JENIS_KELAMIN_CALON_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_CALON_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_CALON_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_CALON_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_CALON_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_CALON_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_CALON_KEY] = value }
    }

    suspend fun saveNIK_CALON_KEY(value: String) {
        context.dataStore.edit { it[NIK_CALON_KEY] = value }
    }

    suspend fun saveAGAMA_CALON_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_CALON_KEY] = value }
    }

    suspend fun savePEKERJAAN_CALON_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_CALON_KEY] = value }
    }

    suspend fun saveLAINNYA_CALON_KEY(value: String) {
        context.dataStore.edit { it[LAINNYA_CALON_KEY] = value }
    }

    suspend fun saveALAMAT_CALON_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_CALON_KEY] = value }
    }

    suspend fun saveBIN_CALON_KEY(value: String) {
        context.dataStore.edit { it[BIN_CALON_KEY] = value }
    }

    suspend fun saveSTATUS_PERKAWINAN_CALON_KEY(value: String) {
        context.dataStore.edit { it[STATUS_PERKAWINAN_CALON_KEY] = value }
    }

    suspend fun saveNAMA_PASANGAN_DULU_KEY(value: String) {
        context.dataStore.edit { it[NAMA_PASANGAN_DULU_KEY] = value }
    }
// ------------------------- AYAH CALON -------------------------

    suspend fun saveNAMA_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[NAMA_AYAH_CALON_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_AYAH_CALON_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_AYAH_CALON_KEY] = value }
    }

    suspend fun saveNIK_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[NIK_AYAH_CALON_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_AYAH_CALON_KEY] = value }
    }

    suspend fun saveAGAMA_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_AYAH_CALON_KEY] = value }
    }

    suspend fun savePEKERJAAN_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_AYAH_CALON_KEY] = value }
    }

    suspend fun saveALAMAT_AYAH_CALON_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_AYAH_CALON_KEY] = value }
    }

// ------------------------- IBU CALON -------------------------

    suspend fun saveNAMA_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[NAMA_IBU_CALON_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_IBU_CALON_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_IBU_CALON_KEY] = value }
    }

    suspend fun saveNIK_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[NIK_IBU_CALON_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_IBU_CALON_KEY] = value }
    }

    suspend fun saveAGAMA_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_IBU_CALON_KEY] = value }
    }

    suspend fun savePEKERJAAN_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_IBU_CALON_KEY] = value }
    }

    suspend fun saveALAMAT_IBU_CALON_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_IBU_CALON_KEY] = value }
    }
    //NIKAH (N.3)
    // ------------------------- CALON SUAMI -------------------------

    suspend fun saveNAMA_LENGKAP_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_SUAMI_KEY] = value }
    }

    suspend fun saveBIN_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[BIN_SUAMI_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_SUAMI_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_SUAMI_KEY] = value }
    }

    suspend fun saveNIK_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[NIK_SUAMI_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_SUAMI_KEY] = value }
    }

    suspend fun saveAGAMA_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_SUAMI_KEY] = value }
    }

    suspend fun savePEKERJAAN_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_SUAMI_KEY] = value }
    }

    suspend fun saveALAMAT_SUAMI_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_SUAMI_KEY] = value }
    }

// ------------------------- CALON ISTRI -------------------------

    suspend fun saveNAMA_LENGKAP_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_ISTRI_KEY] = value }
    }

    suspend fun saveBINTI_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[BINTI_ISTRI_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_ISTRI_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_ISTRI_KEY] = value }
    }

    suspend fun saveNIK_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[NIK_ISTRI_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_ISTRI_KEY] = value }
    }

    suspend fun saveAGAMA_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_ISTRI_KEY] = value }
    }

    suspend fun savePEKERJAAN_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_ISTRI_KEY] = value }
    }

    suspend fun saveALAMAT_ISTRI_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_ISTRI_KEY] = value }
    }

    // ========================= NIKAH (N.4) - DATA PRIA & WANITA =========================

// ------------------------- DATA PRIA -------------------------

    suspend fun saveNIK_PRIA_KEY(value: String) {
        context.dataStore.edit { it[NIK_PRIA_KEY] = value }
    }

    suspend fun saveNAMA_LENGKAP_PRIA_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_PRIA_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_PRIA_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_PRIA_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_PRIA_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_PRIA_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_PRIA_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_PRIA_KEY] = value }
    }

    suspend fun saveAGAMA_PRIA_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_PRIA_KEY] = value }
    }

    suspend fun savePEKERJAAN_PRIA_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_PRIA_KEY] = value }
    }

    suspend fun saveALAMAT_PRIA_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_PRIA_KEY] = value }
    }

// ------------------------- DATA AYAH PRIA -------------------------

    suspend fun saveNIK_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[NIK_AYAH_PRIA_KEY] = value }
    }

    suspend fun saveNAMA_LENGKAP_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_AYAH_PRIA_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_AYAH_PRIA_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_AYAH_PRIA_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_AYAH_PRIA_KEY] = value }
    }

    suspend fun saveAGAMA_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_AYAH_PRIA_KEY] = value }
    }

    suspend fun savePEKERJAAN_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_AYAH_PRIA_KEY] = value }
    }

    suspend fun saveALAMAT_AYAH_PRIA_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_AYAH_PRIA_KEY] = value }
    }

// ------------------------- DATA IBU PRIA -------------------------

    suspend fun saveNIK_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[NIK_IBU_PRIA_KEY] = value }
    }

    suspend fun saveNAMA_LENGKAP_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_IBU_PRIA_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_IBU_PRIA_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_IBU_PRIA_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_IBU_PRIA_KEY] = value }
    }

    suspend fun saveAGAMA_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_IBU_PRIA_KEY] = value }
    }

    suspend fun savePEKERJAAN_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_IBU_PRIA_KEY] = value }
    }

    suspend fun saveALAMAT_IBU_PRIA_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_IBU_PRIA_KEY] = value }
    }

// ------------------------- DATA WANITA -------------------------

    suspend fun saveNIK_WANITA_KEY(value: String) {
        context.dataStore.edit { it[NIK_WANITA_KEY] = value }
    }

    suspend fun saveNAMA_LENGKAP_WANITA_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_WANITA_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_WANITA_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_WANITA_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_WANITA_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_WANITA_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_WANITA_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_WANITA_KEY] = value }
    }

    suspend fun saveAGAMA_WANITA_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_WANITA_KEY] = value }
    }

    suspend fun savePEKERJAAN_WANITA_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_WANITA_KEY] = value }
    }

    suspend fun saveALAMAT_WANITA_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_WANITA_KEY] = value }
    }

// ------------------------- DATA AYAH WANITA -------------------------

    suspend fun saveNIK_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[NIK_AYAH_WANITA_KEY] = value }
    }

    suspend fun saveNAMA_LENGKAP_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_AYAH_WANITA_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_AYAH_WANITA_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_AYAH_WANITA_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_AYAH_WANITA_KEY] = value }
    }

    suspend fun saveAGAMA_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_AYAH_WANITA_KEY] = value }
    }

    suspend fun savePEKERJAAN_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_AYAH_WANITA_KEY] = value }
    }

    suspend fun saveALAMAT_AYAH_WANITA_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_AYAH_WANITA_KEY] = value }
    }

// ------------------------- DATA IBU WANITA -------------------------

    suspend fun saveNIK_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[NIK_IBU_WANITA_KEY] = value }
    }

    suspend fun saveNAMA_LENGKAP_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[NAMA_LENGKAP_IBU_WANITA_KEY] = value }
    }

    suspend fun saveTANGGAL_LAHIR_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[TANGGAL_LAHIR_IBU_WANITA_KEY] = value }
    }

    suspend fun saveTEMPAT_LAHIR_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[TEMPAT_LAHIR_IBU_WANITA_KEY] = value }
    }

    suspend fun saveKEWARGANEGARAAN_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[KEWARGANEGARAAN_IBU_WANITA_KEY] = value }
    }

    suspend fun saveAGAMA_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[AGAMA_IBU_WANITA_KEY] = value }
    }

    suspend fun savePEKERJAAN_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[PEKERJAAN_IBU_WANITA_KEY] = value }
    }

    suspend fun saveALAMAT_IBU_WANITA_KEY(value: String) {
        context.dataStore.edit { it[ALAMAT_IBU_WANITA_KEY] = value }
    }


    // Membaca data sebagai Flow (untuk observasi perubahan)
// — PINDAH DATANG (Data Kepindahan)
    val alamatTujuanFlow               = context.dataStore.data.map { it[ALAMAT_TUJUAN_KEY] ?: "" }
    val alasanKepindahanFlow           = context.dataStore.data.map { it[ALASAN_KEPINDAHAN_KEY] ?: "" }
    val klasifikasiPindahFlow          = context.dataStore.data.map { it[KLASIFIKASI_PINDAH_KEY] ?: "" }
    val jenisKepindahanFlow            = context.dataStore.data.map { it[JENIS_KEPINDAHAN_KEY] ?: "" }
    val statusTidakPindahFlow          = context.dataStore.data.map { it[STATUS_TIDAK_PINDAH_KEY] ?: "" }
    val statusPindahFlow               = context.dataStore.data.map { it[STATUS_PINDAH_KEY] ?: "" }
    val rencanaTanggalPindahFlow       = context.dataStore.data.map { it[RENCANA_TANGGAL_PINDAH_KEY] ?: "" }
    val keluargaYangPindahFlow         = context.dataStore.data.map { it[KELUARGA_YANG_PINDAH_KEY] ?: "" }
    val nikKeluargaYangPindahFlow      = context.dataStore.data.map { it[NIK_KELUARGA_YANG_PINDAH_KEY] ?: "" }

    // — PINDAH DATANG (Data Daerah Asal)
    val noKKFlow                       = context.dataStore.data.map { it[NO_KK_KEY] ?: "" }
    val namaKepalaKeluargaFlow        = context.dataStore.data.map { it[NAMA_KEPALA_KELUARGA_KEY] ?: "" }
    val alamatAsalFlow                 = context.dataStore.data.map { it[ALAMAT_ASAL_KEY] ?: "" }
    val rtAsalFlow                     = context.dataStore.data.map { it[RT_ASAL_KEY] ?: "" }
    val rwAsalFlow                     = context.dataStore.data.map { it[RW_ASAL_KEY] ?: "" }
    val kelurahanAsalFlow              = context.dataStore.data.map { it[KELURAHAN_ASAL_KEY] ?: "" }
    val kecamatanAsalFlow              = context.dataStore.data.map { it[KECAMATAN_ASAL_KEY] ?: "" }
    val kabupatenAsalFlow              = context.dataStore.data.map { it[KABUPATEN_ASAL_KEY] ?: "" }
    val provinsiAsalFlow               = context.dataStore.data.map { it[PROVINSI_ASAL_KEY] ?: "" }

    // — PINDAH DATANG (Data Daerah Tujuan)
    val noKKTujuanFlow                 = context.dataStore.data.map { it[NO_KK_TUJUAN_KEY] ?: "" }
    val namaKepalaKeluargaTujuanFlow   = context.dataStore.data.map { it[NAMA_KEPALA_KELUARGA_TUJUAN_KEY] ?: "" }
    val alamatTujuanDaerahTujuanFlow   = context.dataStore.data.map { it[ALAMAT_TUJUAN_DAERAH_TUJUAN_KEY] ?: "" }
    val nikKepalaKeluargaTujuanFlow    = context.dataStore.data.map { it[NIK_KEPALA_KELUARGA_TUJUAN_KEY] ?: "" }
    val statusKKPindahFlow             = context.dataStore.data.map { it[STATUS_KK_PINDAH_KEY] ?: "" }
    val tanggalKedatanganFlow          = context.dataStore.data.map { it[TANGGAL_KEDATANGAN_KEY] ?: "" }
    val nikKeluargaYangDatangFlow      = context.dataStore.data.map { it[NIK_KELUARGA_YANG_DATANG_KEY] ?: "" }

    // — KELAHIRAN (Pelapor)
    val rtPelaporFlow                  = context.dataStore.data.map { it[RT_PELAPOR_KELAHIRAN_KEY] ?: "" }
    val rwPelaporFlow                  = context.dataStore.data.map { it[RW_PELAPOR_KELAHIRAN_KEY] ?: "" }
    val nikPelaporFlow                 = context.dataStore.data.map { it[NIK_PELAPOR_KELAHIRAN_KEY] ?: "" }
    val namaPelaporFlow                = context.dataStore.data.map { it[NAMA_PELAPOR_KELAHIRAN_KEY] ?: "" }
    val tempatLahirPelaporFlow         = context.dataStore.data.map { it[TEMPAT_LAHIR_PELAPOR_KELAHIRAN_KEY] ?: "" }
    val tanggalLahirPelaporFlow        = context.dataStore.data.map { it[TANGGAL_LAHIR_PELAPOR_KELAHIRAN_KEY] ?: "" }
    val jenisKelaminPelaporFlow        = context.dataStore.data.map { it[JENIS_KELAMIN_PELAPOR_KELAHIRAN_KEY] ?: "" }
    val alamatPelaporFlow              = context.dataStore.data.map { it[ALAMAT_PELAPOR_KELAHIRAN_KEY] ?: "" }

    // — KELAHIRAN (Anak)
    val nikAnakFlow                    = context.dataStore.data.map { it[NIK_ANAK_KEY] ?: "" }
    val namaAnakFlow                   = context.dataStore.data.map { it[NAMA_ANAK_KEY] ?: "" }
    val jenisKelaminAnakFlow           = context.dataStore.data.map { it[JENIS_KELAMIN_ANAK_KEY] ?: "" }
    val tempatLahirAnakFlow            = context.dataStore.data.map { it[TEMPAT_LAHIR_ANAK_KEY] ?: "" }
    val tanggalLahirAnakFlow           = context.dataStore.data.map { it[TANGGAL_LAHIR_ANAK_KEY] ?: "" }
    val pukulFlow                      = context.dataStore.data.map { it[PUKUL_KELAHIRAN_KEY]?.toString() ?: "" }
    val kelahiranKeFlow                = context.dataStore.data.map { it[KELAHIRAN_KE_KEY] ?: "" }
    val penolongKelahiranFlow          = context.dataStore.data.map { it[PENOLONG_KELAHIRAN_KEY] ?: "" }
    val beratBayiFlow                  = context.dataStore.data.map { it[BERAT_ANAK_KEY] ?: "" }
    val panjangBayiFlow                = context.dataStore.data.map { it[PANJANG_ANAK_KEY] ?: "" }

    // — KELAHIRAN (Ibu)
    val ibuNikFlow                     = context.dataStore.data.map { it[IBU_KELAHIRAN_NIK_KEY] ?: "" }
    val ibuNamaFlow                    = context.dataStore.data.map { it[IBU_KELAHIRAN_NAMA_KEY] ?: "" }
    val ibuTanggalLahirFlow            = context.dataStore.data.map { it[IBU_KELAHIRAN_TANGGAL_LAHIR_KEY] ?: "" }
    val ibuTempatLahirFlow             = context.dataStore.data.map { it[IBU_KELAHIRAN_TEMPAT_LAHIR_KEY] ?: "" }
    val ibuUmurFlow                    = context.dataStore.data.map { it[IBU_KELAHIRAN_UMUR_KEY] ?: "" }
    val ibuPekerjaanFlow               = context.dataStore.data.map { it[IBU_KELAHIRAN_PEKERJAAN_KEY] ?: "" }
    val ibuAlamatFlow                  = context.dataStore.data.map { it[IBU_KELAHIRAN_ALAMAT_KEY] ?: "" }
    val ibuKewarganegaraanFlow         = context.dataStore.data.map { it[IBU_KELAHIRAN_KEWARGANEGARAAN_KEY] ?: "" }
    val ibuKebangsaanFlow              = context.dataStore.data.map { it[IBU_KELAHIRAN_KEBANGSAAN_KEY] ?: "" }
    val ibuTanggalPernikahanFlow       = context.dataStore.data.map { it[IBU_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY] ?: "" }

    // — KELAHIRAN (Ayah)
    val ayahNikFlow                    = context.dataStore.data.map { it[AYAH_KELAHIRAN_NIK_KEY] ?: "" }
    val ayahNamaFlow                   = context.dataStore.data.map { it[AYAH_KELAHIRAN_NAMA_KEY] ?: "" }
    val ayahTanggalLahirFlow           = context.dataStore.data.map { it[AYAH_KELAHIRAN_TANGGAL_LAHIR_KEY] ?: "" }
    val ayahTempatLahirFlow            = context.dataStore.data.map { it[AYAH_KELAHIRAN_TEMPAT_LAHIR_KEY] ?: "" }
    val ayahUmurFlow                   = context.dataStore.data.map { it[AYAH_KELAHIRAN_UMUR_KEY] ?: "" }
    val ayahPekerjaanFlow              = context.dataStore.data.map { it[AYAH_KELAHIRAN_PEKERJAAN_KEY] ?: "" }
    val ayahAlamatFlow                 = context.dataStore.data.map { it[AYAH_KELAHIRAN_ALAMAT_KEY] ?: "" }
    val ayahKewarganegaraanFlow        = context.dataStore.data.map { it[AYAH_KELAHIRAN_KEWARGANEGARAAN_KEY] ?: "" }
    val ayahKebangsaanFlow             = context.dataStore.data.map { it[AYAH_KELAHIRAN_KEBANGSAAN_KEY] ?: "" }
    val ayahTanggalPernikahanFlow      = context.dataStore.data.map { it[AYAH_KELAHIRAN_TANGGAL_PERNIKAHAN_KEY] ?: "" }

    // — KELAHIRAN (Saksi 1)
    val saksi1NikFlow                  = context.dataStore.data.map { it[SAKSI1_NIK_KELAHIRAN_KEY] ?: "" }
    val saksi1NamaFlow                 = context.dataStore.data.map { it[SAKSI1_NAMA_KELAHIRAN_KEY] ?: "" }
    val saksi1TanggalLahirFlow         = context.dataStore.data.map { it[SAKSI1_TANGGAL_LAHIR_KELAHIRAN_KEY] ?: "" }
    val saksi1TempatLahirFlow          = context.dataStore.data.map { it[SAKSI1_TEMPAT_LAHIR_KELAHIRAN_KEY] ?: "" }
    val saksi1JenisKelaminFlow         = context.dataStore.data.map { it[SAKSI1_JENIS_KELAMIN_KELAHIRAN_KEY] ?: "" }
    val saksi1UmurFlow                 = context.dataStore.data.map { it[SAKSI1_UMUR_KELAHIRAN_KEY] ?: "" }
    val saksi1PekerjaanFlow            = context.dataStore.data.map { it[SAKSI1_PEKERJAAN_KELAHIRAN_KEY] ?: "" }
    val saksi1AlamatFlow               = context.dataStore.data.map { it[SAKSI1_ALAMAT_KELAHIRAN_KEY] ?: "" }

    // — KELAHIRAN (Saksi 2)
    val saksi2NikFlow                  = context.dataStore.data.map { it[SAKSI2_NIK_KELAHIRAN_KEY] ?: "" }
    val saksi2NamaFlow                 = context.dataStore.data.map { it[SAKSI2_NAMA_KELAHIRAN_KEY] ?: "" }
    val saksi2TanggalLahirFlow         = context.dataStore.data.map { it[SAKSI2_TANGGAL_LAHIR_KELAHIRAN_KEY] ?: "" }
    val saksi2TempatLahirFlow          = context.dataStore.data.map { it[SAKSI2_TEMPAT_LAHIR_KELAHIRAN_KEY] ?: "" }
    val saksi2JenisKelaminFlow         = context.dataStore.data.map { it[SAKSI2_JENIS_KELAMIN_KELAHIRAN_KEY] ?: "" }
    val saksi2UmurFlow                 = context.dataStore.data.map { it[SAKSI2_UMUR_KELAHIRAN_KEY] ?: "" }
    val saksi2PekerjaanFlow            = context.dataStore.data.map { it[SAKSI2_PEKERJAAN_KELAHIRAN_KEY] ?: "" }
    val saksi2AlamatFlow               = context.dataStore.data.map { it[SAKSI2_ALAMAT_KELAHIRAN_KEY] ?: "" }


    // — KEMATIAN (Pelapor)
    val namaPelaporKematianFlow           = context.dataStore.data.map { it[NAMA_PELAPOR_KEMATIAN_KEY] ?: "" }
    val tanggalLahirPelaporKematianFlow   = context.dataStore.data.map { it[TANGGAL_LAHIR_PELAPOR_KEMATIAN_KEY] ?: "" }
    val tempatLahirPelaporKematianFlow    = context.dataStore.data.map { it[TEMPAT_LAHIR_PELAPOR_KEMATIAN_KEY] ?: "" }
    val jenisKelaminPelaporKematianFlow   = context.dataStore.data.map { it[JENIS_KELAMIN_PELAPOR_KEMATIAN_KEY] ?: "" }
    val nikPelaporKematianFlow             = context.dataStore.data.map { it[NIK_PELAPOR_KEMATIAN_KEY] ?: "" }
    val alamatPelaporKematianFlow          = context.dataStore.data.map { it[ALAMAT_PELAPOR_KEMATIAN_KEY] ?: "" }
    val rtPelaporKematianFlow              = context.dataStore.data.map { it[RT_PELAPOR_KEMATIAN_KEY] ?: "" }
    val rwPelaporKematianFlow              = context.dataStore.data.map { it[RW_PELAPOR_KEMATIAN_KEY] ?: "" }

    // — KEMATIAN (Jenazah)
    val nikJenazahFlow               = context.dataStore.data.map { it[NIK_JENAZAH_KEY] ?: "" }
    val namaJenazahFlow              = context.dataStore.data.map { it[NAMA_JENAZAH_KEY] ?: "" }
    val jenisKelaminJenazahFlow      = context.dataStore.data.map { it[JENIS_KELAMIN_JENAZAH_KEY] ?: "" }
    val tempatLahirJenazahFlow       = context.dataStore.data.map { it[TEMPAT_LAHIR_JENAZAH_KEY] ?: "" }
    val tanggalLahirJenazahFlow      = context.dataStore.data.map { it[TANGGAL_LAHIR_JENAZAH_KEY] ?: "" }
    val umurJenazahFlow              = context.dataStore.data.map { it[UMUR_JENAZAH_KEY] ?: "" }
    val agamaJenazahFlow             = context.dataStore.data.map { it[AGAMA_JENAZAH_KEY] ?: "" }
    val pekerjaanJenazahFlow         = context.dataStore.data.map { it[PEKERJAAN_JENAZAH_KEY] ?: "" }
    val alamatJenazahFlow            = context.dataStore.data.map { it[ALAMAT_JENAZAH_KEY] ?: "" }
    val anakKeJenazahFlow            = context.dataStore.data.map { it[ANAK_KE_JENAZAH_KEY] ?: "" }
    val tanggalKematianJenazahFlow   = context.dataStore.data.map { it[TANGGAL_KEMATIAN_JENAZAH_KEY] ?: "" }
    val pukulJenazahFlow             = context.dataStore.data.map { it[PUKUL_JENAZAH_KEY] ?: "" }
    val sebabKematianJenazahFlow     = context.dataStore.data.map { it[SEBAB_KEMATIAN_JENAZAH_KEY] ?: "" }
    val yangMenerangkanFlow          = context.dataStore.data.map { it[YANG_MENERANGKAN_KEY] ?: "" }

    // — KEMATIAN (Ibu)
    val nikIbuKematianFlow                 = context.dataStore.data.map { it[NIK_IBU_KEMATIAN_KEY] ?: "" }
    val namaIbuKematianFlow                = context.dataStore.data.map { it[NAMA_IBU_KEMATIAN_KEY] ?: "" }
    val tempatLahirIbuKematianFlow        = context.dataStore.data.map { it[TEMPAT_LAHIR_IBU_KEMATIAN_KEY] ?: "" }
    val tanggalLahirIbuKematianFlow       = context.dataStore.data.map { it[TANGGAL_LAHIR_IBU_KEMATIAN_KEY] ?: "" }
    val agamaIbuKematianFlow               = context.dataStore.data.map { it[AGAMA_IBU_KEMATIAN_KEY] ?: "" }
    val pekerjaanIbuKematianFlow           = context.dataStore.data.map { it[PEKERJAAN_IBU_KEMATIAN_KEY] ?: "" }
    val alamatIbuKematianFlow              = context.dataStore.data.map { it[ALAMAT_IBU_KEMATIAN_KEY] ?: "" }

    // — KEMATIAN (Ayah)
    val nikAyahKematianFlow                = context.dataStore.data.map { it[NIK_AYAH_KEMATIAN_KEY] ?: "" }
    val namaAyahKematianFlow               = context.dataStore.data.map { it[NAMA_AYAH_KEMATIAN_KEY] ?: "" }
    val tempatLahirAyahKematianFlow       = context.dataStore.data.map { it[TEMPAT_LAHIR_AYAH_KEMATIAN_KEY] ?: "" }
    val tanggalLahirAyahKematianFlow      = context.dataStore.data.map { it[TANGGAL_LAHIR_AYAH_KEMATIAN_KEY] ?: "" }
    val umurAyahKematianFlow               = context.dataStore.data.map { it[AGAMA_AYAH_KEMATIAN_KEY] ?: "" }
    val pekerjaanAyahKematianFlow          = context.dataStore.data.map { it[PEKERJAAN_AYAH_KEMATIAN_KEY] ?: "" }
    val alamatAyahKematianFlow             = context.dataStore.data.map { it[ALAMAT_AYAH_KEMATIAN_KEY] ?: "" }

    // — KEMATIAN (Saksi 1)
    val nikSaksi1KematianFlow              = context.dataStore.data.map { it[SAKSI1_NIK_KEMATIAN_KEY] ?: "" }
    val namaSaksi1KematianFlow             = context.dataStore.data.map { it[SAKSI1_NAMA_KEMATIAN_KEY] ?: "" }
    val tanggalLahirSaksi1KematianFlow     = context.dataStore.data.map { it[SAKSI1_TANGGAL_LAHIR_KEMATIAN_KEY] ?: "" }
    val tempatLahirSaksi1KematianFlow      = context.dataStore.data.map { it[SAKSI1_TEMPAT_LAHIR_KEMATIAN_KEY] ?: "" }
    val jenisKelaminSaksi1KematianFlow     = context.dataStore.data.map { it[SAKSI1_JENIS_KELAMIN_KEMATIAN_KEY] ?: "" }
    val pekerjaanSaksi1KematianFlow         = context.dataStore.data.map { it[SAKSI1_PEKERJAAN_KEMATIAN_KEY] ?: "" }
    val alamatSaksi1KematianFlow            = context.dataStore.data.map { it[SAKSI1_ALAMAT_KEMATIAN_KEY] ?: "" }

    // — KEMATIAN (Saksi 2)
    val nikSaksi2KematianFlow              = context.dataStore.data.map { it[SAKSI2_NIK_KEMATIAN_KEY] ?: "" }
    val namaSaksi2KematianFlow             = context.dataStore.data.map { it[SAKSI2_NAMA_KEMATIAN_KEY] ?: "" }
    val tanggalLahirSaksi2KematianFlow     = context.dataStore.data.map { it[SAKSI2_TANGGAL_LAHIR_KEMATIAN_KEY] ?: "" }
    val tempatLahirSaksi2KematianFlow      = context.dataStore.data.map { it[SAKSI2_TEMPAT_LAHIR_KEMATIAN_KEY] ?: "" }
    val jenisKelaminSaksi2KematianFlow     = context.dataStore.data.map { it[SAKSI2_JENIS_KELAMIN_KEMATIAN_KEY] ?: "" }
    val pekerjaanSaksi2KematianFlow         = context.dataStore.data.map { it[SAKSI2_PEKERJAAN_KEMATIAN_KEY] ?: "" }
    val alamatSaksi2KematianFlow            = context.dataStore.data.map { it[SAKSI2_ALAMAT_KEMATIAN_KEY] ?: "" }



    // Mendapatkan nilai tunggal (suspend functions)
// — PINDAH DATANG
    suspend fun getAlamatTujuan()               = alamatTujuanFlow.first()
    suspend fun getAlasanKepindahan()           = alasanKepindahanFlow.first()
    suspend fun getKlasifikasiPindah()          = klasifikasiPindahFlow.first()
    suspend fun getJenisKepindahan()            = jenisKepindahanFlow.first()
    suspend fun getStatusTidakPindah()          = statusTidakPindahFlow.first()
    suspend fun getStatusPindah()               = statusPindahFlow.first()
    suspend fun getRencanaTanggalPindah()       = rencanaTanggalPindahFlow.first()
    suspend fun getKeluargaYangPindah()         = keluargaYangPindahFlow.first()
    suspend fun getNikKeluargaYangPindah()      = nikKeluargaYangPindahFlow.first()

    suspend fun getNoKK()                       = noKKFlow.first()
    suspend fun getNamaKepalaKeluarga()         = namaKepalaKeluargaFlow.first()
    suspend fun getAlamatAsal()                 = alamatAsalFlow.first()
    suspend fun getRtAsal()                     = rtAsalFlow.first()
    suspend fun getRwAsal()                     = rwAsalFlow.first()
    suspend fun getKelurahanAsal()              = kelurahanAsalFlow.first()
    suspend fun getKecamatanAsal()              = kecamatanAsalFlow.first()
    suspend fun getKabupatenAsal()              = kabupatenAsalFlow.first()
    suspend fun getProvinsiAsal()               = provinsiAsalFlow.first()

    suspend fun getNoKKTujuan()                 = noKKTujuanFlow.first()
    suspend fun getNamaKepalaKeluargaTujuan()   = namaKepalaKeluargaTujuanFlow.first()
    suspend fun getAlamatTujuanDaerahTujuan()   = alamatTujuanDaerahTujuanFlow.first()
    suspend fun getNikKepalaKeluargaTujuan()    = nikKepalaKeluargaTujuanFlow.first()
    suspend fun getStatusKKPindah()             = statusKKPindahFlow.first()
    suspend fun getTanggalKedatangan()          = tanggalKedatanganFlow.first()
    suspend fun getNikKeluargaYangDatang()      = nikKeluargaYangDatangFlow.first()

//KELAHIRAN
    // — KELAHIRAN (Pelapor)
    suspend fun getRtPelapor()                  = rtPelaporFlow.first()
    suspend fun getRwPelapor()                  = rwPelaporFlow.first()
    suspend fun getNikPelapor()                 = nikPelaporFlow.first()
    suspend fun getNamaPelapor()                = namaPelaporFlow.first()
    suspend fun getTempatLahirPelapor()         = tempatLahirPelaporFlow.first()
    suspend fun getTanggalLahirPelapor()        = tanggalLahirPelaporFlow.first()
    suspend fun getJenisKelaminPelapor()        = jenisKelaminPelaporFlow.first()
    suspend fun getAlamatPelapor()              = alamatPelaporFlow.first()

    // — KELAHIRAN (Anak)
    suspend fun getNikAnak()                    = nikAnakFlow.first()
    suspend fun getNamaAnak()                   = namaAnakFlow.first()
    suspend fun getJenisKelaminAnak()           = jenisKelaminAnakFlow.first()
    suspend fun getTempatLahirAnak()            = tempatLahirAnakFlow.first()
    suspend fun getTanggalLahirAnak()           = tanggalLahirAnakFlow.first()
    suspend fun getPukul()                      = pukulFlow.first().toIntOrNull() ?: 0
    suspend fun getKelahiranKe()                = kelahiranKeFlow.first()
    suspend fun getPenolongKelahiran()          = penolongKelahiranFlow.first()
    suspend fun getBeratBayi()                  = beratBayiFlow.first()
    suspend fun getPanjangBayi()                = panjangBayiFlow.first()

    // — KELAHIRAN (Ibu)
    suspend fun getIbuNik()                     = ibuNikFlow.first()
    suspend fun getIbuNama()                    = ibuNamaFlow.first()
    suspend fun getIbuTanggalLahir()            = ibuTanggalLahirFlow.first()
    suspend fun getIbuTempatLahir()             = ibuTempatLahirFlow.first()
    suspend fun getIbuUmur()                    = ibuUmurFlow.first()
    suspend fun getIbuPekerjaan()               = ibuPekerjaanFlow.first()
    suspend fun getIbuAlamat()                  = ibuAlamatFlow.first()
    suspend fun getIbuKewarganegaraan()         = ibuKewarganegaraanFlow.first()
    suspend fun getIbuKebangsaan()              = ibuKebangsaanFlow.first()
    suspend fun getIbuTanggalPernikahan()       = ibuTanggalPernikahanFlow.first()

    // — KELAHIRAN (Ayah)
    suspend fun getAyahNik()                    = ayahNikFlow.first()
    suspend fun getAyahNama()                   = ayahNamaFlow.first()
    suspend fun getAyahTanggalLahir()           = ayahTanggalLahirFlow.first()
    suspend fun getAyahTempatLahir()            = ayahTempatLahirFlow.first()
    suspend fun getAyahUmur()                   = ayahUmurFlow.first()
    suspend fun getAyahPekerjaan()              = ayahPekerjaanFlow.first()
    suspend fun getAyahAlamat()                 = ayahAlamatFlow.first()
    suspend fun getAyahKewarganegaraan()        = ayahKewarganegaraanFlow.first()
    suspend fun getAyahKebangsaan()             = ayahKebangsaanFlow.first()
    suspend fun getAyahTanggalPernikahan()      = ayahTanggalPernikahanFlow.first()

    // — KELAHIRAN (Saksi 1)
    suspend fun getSaksi1Nik()                  = saksi1NikFlow.first()
    suspend fun getSaksi1Nama()                 = saksi1NamaFlow.first()
    suspend fun getSaksi1TanggalLahir()         = saksi1TanggalLahirFlow.first()
    suspend fun getSaksi1TempatLahir()          = saksi1TempatLahirFlow.first()
    suspend fun getSaksi1JenisKelamin()         = saksi1JenisKelaminFlow.first()
    suspend fun getSaksi1Umur()                 = saksi1UmurFlow.first()
    suspend fun getSaksi1Pekerjaan()            = saksi1PekerjaanFlow.first()
    suspend fun getSaksi1Alamat()               = saksi1AlamatFlow.first()

    // — KELAHIRAN (Saksi 2)
    suspend fun getSaksi2Nik()                  = saksi2NikFlow.first()
    suspend fun getSaksi2Nama()                 = saksi2NamaFlow.first()
    suspend fun getSaksi2TanggalLahir()         = saksi2TanggalLahirFlow.first()
    suspend fun getSaksi2TempatLahir()          = saksi2TempatLahirFlow.first()
    suspend fun getSaksi2JenisKelamin()         = saksi2JenisKelaminFlow.first()
    suspend fun getSaksi2Umur()                 = saksi2UmurFlow.first()
    suspend fun getSaksi2Pekerjaan()            = saksi2PekerjaanFlow.first()
    suspend fun getSaksi2Alamat()               = saksi2AlamatFlow.first()


    // Metode untuk membersihkan semua data
    suspend fun clearAllData() {
        context.dataStore.edit { it.clear() }
    }
}

