package com.afiva.appskelurahan.routing

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.afiva.appskelurahan.routing.Screen.DetailRoute
import com.afiva.appskelurahan.ui.layar.surat_ket_kematian.FormJenazah
import com.example.ui.MainScreen
import com.afiva.appskelurahan.ui.screens.AllRequirementsScreen
import com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat.PreviewDisplaySuratKeterangan
import com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat.PreviewDisplaySuratKeteranganKematian
import com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat.PreviewDisplaySuratKeteranganPindah
import com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat.PreviewDisplaySuratKeteranganUsaha
import com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat.PreviewDisplaySuratPengantarRT
import com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat.PreviewDisplaySuratTidakMampu
import com.afiva.appskelurahan.ui.screens.DetailsScreen
import com.afiva.appskelurahan.ui.screens.Domisili.PreviewDisplaySuratKeteranganDomisili
import com.afiva.appskelurahan.ui.screens.Domisili.SuratKeteranganDomisili
import com.afiva.appskelurahan.ui.screens.ForgotPasswordScreen
import com.afiva.appskelurahan.ui.screens.InfoKelurahanScreen
import com.afiva.appskelurahan.ui.screens.InformasiScreen
import com.afiva.appskelurahan.ui.screens.Keterangan.DisplaySuratKeterangan
import com.afiva.appskelurahan.ui.screens.Keterangan.SuratKeterangan
import com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.SuratPengantar
import com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.SuratPengantarRTOutputScreen
import com.afiva.appskelurahan.ui.screens.Keterangan_Usaha.ReviewSuratKeteranganUsaha
import com.afiva.appskelurahan.ui.screens.Keterangan_Usaha.SuratKeteranganUsaha
import com.afiva.appskelurahan.ui.screens.LoginScreen
import com.afiva.appskelurahan.ui.screens.RiwayatScreen
import com.afiva.appskelurahan.ui.screens.SplashScreen
import com.afiva.appskelurahan.ui.screens.Surat_Tidak_mampu.ReviewSuratTidakMampu
import com.afiva.appskelurahan.ui.screens.Surat_Tidak_mampu.SuratKeteranganTidakMampu
import com.afiva.appskelurahan.ui.screens.keterangan_pindah_datang.DataDaerahAsal
import com.afiva.appskelurahan.ui.screens.keterangan_pindah_datang.ReviewPindahDatang
import com.afiva.appskelurahan.ui.screens.ProfileScreen
import com.afiva.appskelurahan.ui.screens.registerScreen
import com.afiva.appskelurahan.ui.screens.surat_ket_Kematian.SuratkematianPreview


@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        // Splash & Auth
        composable(Screen.SplashScreen.route) { SplashScreen(navController) }
        composable(Screen.Register.route) { registerScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable("forgot_password") {
            ForgotPasswordScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Detail JSON
        composable(
            route = "${DetailRoute.baseRoute}/{${DetailRoute.paramDataJson}}",
            arguments = listOf(navArgument(DetailRoute.paramDataJson) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val dataJson = backStackEntry.arguments?.getString(DetailRoute.paramDataJson)
            DetailsScreen(
                navController = navController,
                dataJson = dataJson
            )
        }

        // Main
        composable(Screen.Beranda.route) { MainScreen(navController) }
        composable(Screen.ProfilScreen.route) { ProfileScreen(navController) }

        // Informasi
        composable(Screen.InformasiScreen.route) { InformasiScreen(navController) }
        composable(Screen.InfoKelurahanScreen.route) { InfoKelurahanScreen(navController) }

        // All Requirements
        composable(Screen.AllRequirementsScreen.route) {
            AllRequirementsScreen(
                navController = navController,
                onAgreeClicked = { navController.navigate(Screen.Beranda.route) },
                onBackClicked = { navController.popBackStack() }
            )
        }

        // Riwayat
        composable(Screen.RiwayatScreen.route) { RiwayatScreen(navController) }

        // Surat Pengantar RT
        composable(Screen.SuratPengantar.route) { SuratPengantar(navController) }
        composable(Screen.SuratPengantarRTOutputScreen.route) { SuratPengantarRTOutputScreen(navController) }
        composable(Screen.PreviewDisplaySuratPengantarRT.route) { PreviewDisplaySuratPengantarRT(navController) }

        // Surat Tidak Mampu
        composable(Screen.SuratKeteranganTidakMampu.route) { SuratKeteranganTidakMampu(navController) }
        composable(Screen.ReviewSuratTidakMampu.route) { ReviewSuratTidakMampu(navController) }
        composable(Screen.PreviewDisplaySuratTidakMampu.route) { PreviewDisplaySuratTidakMampu(navController) }

        // Surat Usaha
        composable(Screen.SuratKeteranganUsaha.route) { SuratKeteranganUsaha(navController) }
        composable(Screen.ReviewSuratUsaha.route) { ReviewSuratKeteranganUsaha(navController) }
        composable(Screen.PreviewDisplaySuratKeteranganUsaha.route) { PreviewDisplaySuratKeteranganUsaha(navController) }

        // Surat Keterangan Khusus
        composable(Screen.SuratKeterangan.route) { SuratKeterangan(navController) }
        composable(Screen.DisplaySuratKeterangan.route) { DisplaySuratKeterangan(navController) }
        composable(Screen.PreviewDisplaySuratKeterangan.route) { PreviewDisplaySuratKeterangan(navController) }

        // Surat Domisili
        composable(Screen.SuratKeteranganDomisili.route) { SuratKeteranganDomisili(navController) }
        composable(Screen.DisplaySuratKeteranganDomisili.route) { PreviewDisplaySuratKeteranganDomisili(navController) }
        composable(Screen.PreviewDisplaySuratKeteranganDomisili.route) { PreviewDisplaySuratKeteranganDomisili(navController) }

        // Surat Kematian
        composable(Screen.FormJenazah.route) { FormJenazah(navController) }
        composable(Screen.DisplaySuratkematianPreview.route) { SuratkematianPreview(navController) }
        composable(Screen.PreviewDisplaySuratKeteranganKematian.route) { PreviewDisplaySuratKeteranganKematian(navController) }

        // Surat Pindah Datang
        composable(Screen.DataDaerahAsal.route) { DataDaerahAsal(navController) }
        composable(Screen.ListDataPindah.route) { ReviewPindahDatang(navController) }
        composable(Screen.PreviewDisplaySuratKeteranganPindah.route) { PreviewDisplaySuratKeteranganPindah(navController) }
    }
}
