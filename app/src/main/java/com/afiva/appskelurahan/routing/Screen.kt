package com.afiva.appskelurahan.routing

sealed class Screen(val route: String) {
    // Auth
    object SplashScreen : Screen("splash_screen")
    object Login : Screen("login")
    object Register : Screen("register")
//    object ManageRequests : Screen("manage_requests")

    // Main Screens
    object Beranda : Screen("beranda")
    object ProfilScreen : Screen("profile")
    object InformasiScreen : Screen("informasi")
    object RiwayatScreen : Screen("riwayat")
    object InfoKelurahanScreen : Screen("info_kelurahan")
    object AllRequirementsScreen : Screen("all_requirements")

    // Surat Pengantar & Keterangan
    object SuratPengantar : Screen("surat_pengantar")
    object SuratPengantarRTOutputScreen : Screen("surat_pengantar_rt_output")
    object PreviewDisplaySuratPengantarRT : Screen("detail_surat_pengantar_rt_output")

    // Surat Tidak Mampu
    object SuratKeteranganTidakMampu : Screen("surat_keterangan_tidak_mampu")
    object ReviewSuratTidakMampu : Screen("review_surat_tidak_mampu")
    object PreviewDisplaySuratTidakMampu : Screen("detail_display_tidak_mampu")

    // Surat Usaha
    object SuratKeteranganUsaha : Screen("surat_keterangan_usaha")
    object ReviewSuratUsaha : Screen("review_surat_usaha")
    object PreviewDisplaySuratKeteranganUsaha : Screen("detail_display_usaha")

    // Surat Keterangan Khusus
    object SuratKeterangan : Screen("surat_keterangan")
    object DisplaySuratKeterangan : Screen("display_surat_keterangan")
    object PreviewDisplaySuratKeterangan : Screen("detail_display_surat_keterangan")

    // Surat Domisili
    object SuratKeteranganDomisili : Screen("surat_keterangan_domisili")
    object DisplaySuratKeteranganDomisili : Screen("display_surat_keterangan_domisili")
    object PreviewDisplaySuratKeteranganDomisili : Screen("detail_display_surat_keterangan_domisili")

    // Surat Kematian
    object FormJenazah : Screen("form_jenazah")
    object DisplaySuratkematianPreview : Screen("display_surat_kematian_preview")
    object PreviewDisplaySuratKeteranganKematian : Screen("detail_display_surat_kematian_preview")


    // Surat Pindah Datang
    object DataDaerahAsal : Screen("data_daerah_asal")
    object ListDataPindah : Screen("list_data_pindah")
    object PreviewDisplaySuratKeteranganPindah : Screen("detail_surat_PindahDatang")


    object DetailRoute {
        const val baseRoute = "detail_screen"
        const val paramDataJson = "dataJson"

        fun createRoute(dataJson: String): String {
            // Pastikan JSON-nya di-encode agar tidak error saat dinavigasi
            return "$baseRoute/${java.net.URLEncoder.encode(dataJson, "UTF-8")}"
        }
    }


    // Admin
//    object BerandaAdmin : Screen("beranda_admin")
}