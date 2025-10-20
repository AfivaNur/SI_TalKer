package com.afiva.appskelurahan.ui.screens.keterangan_pindah_datang

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.delay
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.v_model.PindahDatangViewModel
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.ui.screens.Domisili.ActionButtonsOutput
import com.afiva.appskelurahan.ui.screens.Domisili.EnhancedDataRowMultilineDomisili
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewPindahDatang(navController: NavController) { BackHandler(enabled = true) {}
    val PindahDatangViewModel: PindahDatangViewModel = viewModel(navController.getBackStackEntry(Screen.DataDaerahAsal.route))
    val pindahDatangData by PindahDatangViewModel.dataPindahDatang.collectAsState()
    val hasActivePengajuan by PindahDatangViewModel.hasActivePengajuan
    val isLoading by PindahDatangViewModel.isLoading
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    // Validasi data sebelum pengajuan
    val isDataValid = pindahDatangData?.let { data ->
        data.kkAsal.isNotBlank() &&
                data.namaKepalaKeluargaAsal.isNotBlank() &&
                data.alamatAsal.isNotBlank() &&
                data.noTeleponAsal.isNotBlank() &&
                data.kodePosAsal.isNotBlank() &&
                data.rtAsal.isNotBlank() &&
                data.rwAsal.isNotBlank() &&
                data.alasanPindah.isNotBlank() &&
                data.alamatPindah.isNotBlank() &&
                data.kodePosPindah.isNotBlank() &&
                data.noTeleponPindah.isNotBlank() &&
                data.klasifikasiPindah.isNotBlank() &&
                data.jenisKepindahan.isNotBlank() &&
                data.statusNoKkTidakPindah.isNotBlank() &&
                data.statusNoKkPindah.isNotBlank() &&
                data.rencanaTanggalPindah.isNotBlank() &&
                data.nikKeluargaYangDatangPindah.isNotBlank() &&
                data.namaKeluargaYangDatangPindah.isNotBlank() &&
                data.rtPindah.isNotBlank() &&
                data.rwPindah.isNotBlank() &&
                data.noKkTujuan.isNotBlank() &&
                data.namaKepalaKeluargaTujuan.isNotBlank() &&
                data.nikKepalaKeluargaTujuan.isNotBlank() &&
                data.statusKkPindahTujuan.isNotBlank() &&
                data.tanggalKedatanganTujuan.isNotBlank() &&
                data.alamatTujuan.isNotBlank() &&
                data.nikKeluargaYangDatangTujuan.isNotBlank() &&
                data.namaKeluargaYangDatangTujuan.isNotBlank() &&
                data.rtTujuan.isNotBlank() &&
                data.rwTujuan.isNotBlank() &&
                data.namaFileSuratPengantarRT.isNotBlank() &&
                data.namaFilePersyaratan.isNotBlank()
    } ?: false

    // Memeriksa pengajuan aktif dan validasi login saat layar dibuka
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            if (SessionManager.currentUser?.id == null) {
                val result = snackbarHostState.showSnackbar(
                    message = "Sesi login tidak valid. Silakan login kembali.",
                    actionLabel = "Login",
                    duration = SnackbarDuration.Indefinite
                )
                if (result == SnackbarResult.ActionPerformed) {
                    navController.navigate("login") // Navigasi ke login jika tombol action ditekan
                }
            } else {
                PindahDatangViewModel.checkActivePengajuan(SessionManager.currentUser?.id.toString())
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            modifier = Modifier.size(40.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f))
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_banyuasin),
                                    contentDescription = "Logo Banyuasin",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "KETERANGAN PINDAH-DATANG",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Review Data Pengajuan",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor)
            )
        },
        containerColor = surfaceColor,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                primaryColor,
                                primaryColor.copy(alpha = 0.8f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                InfoHeaderPindahDatang()
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EnhancedSectionCardPindahDatang(
                    title = "Informasi Wilayah Daerah Asal",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChipPindahDatang("RT", pindahDatangData?.rtAsal ?: "", modifier = Modifier.weight(1f))
                        InfoChipPindahDatang("RW", pindahDatangData?.rwAsal ?: "", modifier = Modifier.weight(1f))
                    }
                }

                EnhancedSectionCardPindahDatang(
                    title = "Data Keluarga Daerah Asal",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowPindahDatang("No. KK", pindahDatangData?.kkAsal ?: "")
                        EnhancedDataRowPindahDatang("Nama Kepala Keluarga", pindahDatangData?.namaKepalaKeluargaAsal ?: "")
                        EnhancedDataRowMultilineDomisili("Alamat", pindahDatangData?.alamatAsal ?: "")
                        EnhancedDataRowPindahDatang("Kode Pos", pindahDatangData?.kodePosAsal ?: "")
                        EnhancedDataRowPindahDatang("No. Telepon", pindahDatangData?.noTeleponAsal ?: "")
                    }
                }

                EnhancedSectionCardPindahDatang(
                    title = "Informasi Wilayah Kepindahan",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChipPindahDatang("RT", pindahDatangData?.rtPindah ?: "", modifier = Modifier.weight(1f))
                        InfoChipPindahDatang("RW", pindahDatangData?.rwPindah ?: "", modifier = Modifier.weight(1f))
                    }
                }

                EnhancedSectionCardPindahDatang(
                    title = "Detail Kepindahan",
                    icon = Icons.Default.Info,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowPindahDatang("Alasan Pindah", pindahDatangData?.alasanPindah ?: "")
                        EnhancedDataRowMultilineDomisili("Alamat Tujuan Pindah", pindahDatangData?.alamatPindah ?: "")
                        EnhancedDataRowPindahDatang("Kode Pos", pindahDatangData?.kodePosPindah ?: "")
                        EnhancedDataRowPindahDatang("No. Telepon", pindahDatangData?.noTeleponPindah ?: "")
                        EnhancedDataRowPindahDatang("Klasifikasi Pindah", pindahDatangData?.klasifikasiPindah ?: "")
                        EnhancedDataRowPindahDatang("Jenis Kepindahan", pindahDatangData?.jenisKepindahan ?: "")
                        EnhancedDataRowPindahDatang("Status No. KK Tidak Pindah", pindahDatangData?.statusNoKkTidakPindah ?: "")
                        EnhancedDataRowPindahDatang("Status No. KK Pindah", pindahDatangData?.statusNoKkPindah ?: "")
                        EnhancedDataRowPindahDatang("Rencana Tanggal Pindah", pindahDatangData?.rencanaTanggalPindah ?: "")
                        EnhancedDataRowMultilineDomisili("NIK Keluarga Yang Pindah", pindahDatangData?.nikKeluargaYangDatangPindah ?: "")
                        EnhancedDataRowPindahDatang("Nama Keluarga Yang Pindah", pindahDatangData?.namaKeluargaYangDatangPindah ?: "")
                    }
                }

                EnhancedSectionCardPindahDatang(
                    title = "Informasi Wilayah Daerah Tujuan",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChipPindahDatang("RT", pindahDatangData?.rtTujuan ?: "", modifier = Modifier.weight(1f))
                        InfoChipPindahDatang("RW", pindahDatangData?.rwTujuan ?: "", modifier = Modifier.weight(1f))
                    }
                }

                EnhancedSectionCardPindahDatang(
                    title = "Data Daerah Tujuan",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowPindahDatang("No. KK Tujuan", pindahDatangData?.noKkTujuan ?: "")
                        EnhancedDataRowPindahDatang("Nama Kepala Keluarga", pindahDatangData?.namaKepalaKeluargaTujuan ?: "")
                        EnhancedDataRowPindahDatang("NIK Kepala Keluarga", pindahDatangData?.nikKepalaKeluargaTujuan ?: "")
                        EnhancedDataRowPindahDatang("Status KK Pindah", pindahDatangData?.statusKkPindahTujuan ?: "")
                        EnhancedDataRowPindahDatang("Tanggal Kedatangan", pindahDatangData?.tanggalKedatanganTujuan ?: "")
                        EnhancedDataRowMultilineDomisili("Alamat Tujuan", pindahDatangData?.alamatTujuan ?: "")
                        EnhancedDataRowMultilineDomisili("NIK Keluarga Yang Datang", pindahDatangData?.nikKeluargaYangDatangTujuan ?: "")
                        EnhancedDataRowPindahDatang("Nama Keluarga Yang Datang", pindahDatangData?.namaKeluargaYangDatangTujuan ?: "")
                    }
                }

                EnhancedSectionCardPindahDatang(
                    title = "Berkas Lampiran",
                    icon = Icons.Default.Info,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FileAttachmentCardPindahDatang("Surat Pengantar RT", pindahDatangData?.namaFileSuratPengantarRT ?: "", primaryColor)
                        FileAttachmentCardPindahDatang("Berkas Persyaratan", pindahDatangData?.namaFilePersyaratan ?: "", primaryColor)

                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                // Tampilkan indikator loading
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = primaryColor)
                    }
                }

                // Tampilkan peringatan jika ada pengajuan aktif (unverified)
                if (!isLoading && hasActivePengajuan) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                        border = BorderStroke(1.dp, Color(0xFFFFA726))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFFFFA726),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Anda memiliki pengajuan domisili yang sedang diproses. Silakan cek riwayat pengajuan.",
                                fontSize = 14.sp,
                                color = Color(0xFF2C3E50)
                            )
                        }
                    }
                }

                // Tampilkan peringatan jika data tidak lengkap
                if (!isLoading && !isDataValid) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                        border = BorderStroke(1.dp, Color(0xFFFFA726))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFFFFA726),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Data pengajuan tidak lengkap. Silakan lengkapi data.",
                                fontSize = 14.sp,
                                color = Color(0xFF2C3E50)
                            )
                        }
                    }
                }

                ActionButtonsOutput(
                    onEdit = { navController.navigateUp() },
                    onConfirm = {
                        if (!isDataValid) {
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Data penancing tidak lengkap. Silakan lengkapi data.",
                                    actionLabel = "Edit",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    navController.navigateUp()
                                }
                            }
                        } else if (hasActivePengajuan) {
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Pengajuan domisili sedang diproses. Lihat riwayat?",
                                    actionLabel = "Lihat",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    navController.navigate(Screen.RiwayatScreen.route)
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                PindahDatangViewModel.submitPengajuan(
                                    onSuccess = {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Berhasil Diajukan!",
                                                duration = SnackbarDuration.Long
                                            )
                                            PindahDatangViewModel.resetData() // Reset data setelah pengajuan sukses
                                            delay(3000)
                                            navController.navigate("beranda")
                                        }
                                    },
                                    onError = { errorMessage ->
                                        coroutineScope.launch {
                                            val result = snackbarHostState.showSnackbar(
                                                message = errorMessage,
                                                actionLabel = "Coba Lagi",
                                                duration = SnackbarDuration.Long
                                            )

                                            if (result == SnackbarResult.ActionPerformed) {
                                                // User menekan "Coba Lagi"
                                                PindahDatangViewModel.submitPengajuan(
                                                    onSuccess = {
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Berhasil Diajukan!",
                                                                duration = SnackbarDuration.Long
                                                            )
                                                            PindahDatangViewModel.resetData()
                                                            delay(3000)
                                                            navController.navigate("beranda")
                                                        }
                                                    },
                                                    onError = { retryError ->
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = retryError,
                                                                duration = SnackbarDuration.Long
                                                            )
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    },
                    primaryColor = primaryColor,
                    isConfirmEnabled = isDataValid && !hasActivePengajuan && !isLoading
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun InfoHeaderPindahDatang() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFF00897B).copy(alpha = 0.1f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF00897B),
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Konfirmasi Data",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = "Pastikan semua data sudah sesuai sebelum mengajukan",
                    fontSize = 14.sp,
                    color = Color(0xFF5D6D7E),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun EnhancedSectionCardPindahDatang(
    title: String,
    icon: ImageVector,
    primaryColor: Color,
    lightColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = primaryColor.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.1f),
                                lightColor.copy(alpha = 0.3f)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(primaryColor, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            icon,
                            contentDescription = title,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF2C3E50),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                content = content
            )
        }
    }
}

@Composable
fun FileAttachmentCardPindahDatang(label: String, filename: String, primaryColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        border = BorderStroke(
            1.dp,
            primaryColor.copy(alpha = 0.2f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.Email,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = filename,
                    fontSize = 12.sp,
                    color = Color(0xFF5D6D7E)
                )
            }
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF27AE60),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun InfoChipPindahDatang(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00897B).copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5D6D7E)
            )
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00897B)
            )
        }
    }
}

@Composable
fun EnhancedDataRowPindahDatang(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF8F9FA),
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF5D6D7E)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF2C3E50)
        )
    }
}


@Composable
internal fun ActionButtonsOutput(
    onEdit: () -> Unit, // Renamed for clarity
    onConfirm: () -> Unit,
    primaryColor: Color,
    modifier: Modifier = Modifier,
    isConfirmEnabled: Boolean = true // Added to control confirm button state
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onEdit,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = primaryColor,
                disabledContentColor = primaryColor.copy(alpha = 0.5f)
            ),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text = "Edit",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Button(
            onClick = onConfirm,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                disabledContainerColor = primaryColor.copy(alpha = 0.5f),
                contentColor = Color.White // Assuming white text for contrast
            ),
            enabled = isConfirmEnabled,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            Text(
                text = "Ajukan",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewPindahDatangPreview() {
    ReviewPindahDatang(rememberNavController())
}