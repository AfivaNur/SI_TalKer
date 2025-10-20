package com.afiva.appskelurahan.ui.screens.surat_ket_Kematian

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
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.viewmodel.KematianViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuratkematianPreview(navController: NavController) {
    val kematianViewModel: KematianViewModel = viewModel(navController.getBackStackEntry("form_jenazah"))
    val kematianData by kematianViewModel.dataKematian.collectAsState()
    val hasActivePengajuan by kematianViewModel.hasActivePengajuan
    val isLoading by kematianViewModel.isLoading
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    // Validasi data sebelum pengajuan
    val isDataValid = kematianData?.let { data ->
         data.namaYangMelapor.isNotBlank()
    } ?: false

    // Memeriksa pengajuan unverified dan validasi login saat layar dibuka
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            if (SessionManager.currentUser?.id == null) {
                val result = snackbarHostState.showSnackbar(
                    message = "Sesi login tidak valid. Silakan login kembali.",
                    actionLabel = "Login",
                    duration = SnackbarDuration.Indefinite
                )
                if (result == SnackbarResult.ActionPerformed) {
                    navController.navigate("login")
                }
            } else {
                kematianViewModel.checkActivePengajuan(SessionManager.currentUser?.id.toString()) // Changed to check unverified
            }
        }
    }

    // Mengunci tombol kembali dengan konfirmasi
    BackHandler(enabled = true) {
        coroutineScope.launch {
            val result = snackbarHostState.showSnackbar(
                message = "Apakah Anda yakin ingin membatalkan pengajuan?",
                actionLabel = "Batalkan",
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                navController.navigateUp()
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
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "KETERANGAN KEMATIAN",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Review Data Pengajuan",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Apakah Anda yakin ingin membatalkan pengajuan?",
                                    actionLabel = "Batalkan",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    navController.navigateUp()
                                }
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = surfaceColor
    ) { padding ->
        Column(
            modifier = Modifier
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
                InfoHeaderKematian()
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EnhancedSectionCardKematian(
                    title = "Informasi Wilayah",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChipKematian("RT", kematianData?.rtKematian ?: "", modifier = Modifier.weight(1f))
                        InfoChipKematian("RW", kematianData?.rwKematian ?: "", modifier = Modifier.weight(1f))
                    }
                }

                EnhancedSectionCardKematian(
                    title = "Data Jenazah",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowKematian("Nama Jenazah", kematianData?.namaJenazah ?: "")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowKematian("Jenis Kelamin", kematianData?.jenisKelaminJenazah ?: "")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowKematian("Umur", kematianData?.umurJenazah ?: "")
                            }
                        }
                        EnhancedDataRowKematian("Pekerjaan", kematianData?.pekerjaanJenazah ?: "")
                        EnhancedDataRowMultilineKematian("Alamat Jenazah", kematianData?.alamatJenazah ?: "")
                    }
                }

                EnhancedSectionCardKematian(
                    title = "Informasi Kematian",
                    icon = Icons.Default.DateRange,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowKematian("Tanggal Kematian", kematianData?.tanggalKematianJenazah ?: "")
                        EnhancedDataRowMultilineKematian("Sebab Kematian", kematianData?.sebabKematianJenazah ?: "")
                        EnhancedDataRowKematian("Yang Menerangkan", kematianData?.namaYangMenerangkan ?: "")
                    }
                }

                EnhancedSectionCardKematian(
                    title = "Data Pelapor",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowKematian("Nama Pelapor", kematianData?.namaYangMelapor ?: "")
                        EnhancedDataRowKematian("Hubungan dengan Jenazah", kematianData?.hubunganPelapor ?: "")
                    }
                }

                EnhancedSectionCardKematian(
                    title = "Berkas Lampiran",
                    icon = Icons.Default.Email,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FileAttachmentCardKematian(
                            "Surat Pengantar RT",
                            kematianData?.namaFileSuratPengantarRT ?: "",
                            primaryColor = primaryColor
                        )
                        FileAttachmentCardKematian(
                            "Berkas Persyaratan",
                            kematianData?.namaFilePersyaratan ?: "",
                            primaryColor = primaryColor
                        )
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
                                text = "Anda memiliki pengajuan kematian yang sedang diproses. Silakan cek riwayat pengajuan.",
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
                    onEdit = {
                        coroutineScope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Apakah Anda yakin ingin mengedit data?",
                                actionLabel = "Edit",
                                duration = SnackbarDuration.Long
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                navController.navigateUp()
                            }
                        }
                    },
                    onConfirm = {
                        if (!isDataValid) {
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Data pengajuan tidak lengkap. Silakan lengkapi data.",
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
                                    message = "Pengajuan kematian Anda masih menunggu verifikasi. Lihat riwayat?",
                                    actionLabel = "Lihat",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    navController.navigate(Screen.RiwayatScreen.route)
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                kematianViewModel.submitPengajuan(
                                    onSuccess = {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Berhasil Diajukan!",
                                                duration = SnackbarDuration.Long
                                            )
                                            kematianViewModel.resetData()
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
                                                kematianViewModel.submitPengajuan(
                                                    onSuccess = {
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Berhasil Diajukan!",
                                                                duration = SnackbarDuration.Long
                                                            )
                                                            kematianViewModel.resetData()
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
fun InfoHeaderKematian() {
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
                    text = "Konfirmasi Data Kematian",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = "Pastikan semua data sudah sesuai sebelum mengajukan",
                    fontSize = 14.sp,
                    color = Color(0xFF5D6D7E)
                )
            }
        }
    }
}

@Composable
fun EnhancedSectionCardKematian(
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
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF2C3E50)
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
fun InfoChipKematian(label: String, value: String, modifier: Modifier = Modifier) {
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
                text = value.ifEmpty { "-" },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (value.isEmpty()) Color(0xFF95A5A6) else Color(0xFF00897B)
            )
        }
    }
}

@Composable
fun EnhancedDataRowKematian(label: String, value: String) {
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
            text = value.ifEmpty { "-" },
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (value.isEmpty()) Color(0xFF95A5A6) else Color(0xFF2C3E50)
        )
    }
}

@Composable
fun EnhancedDataRowMultilineKematian(label: String, value: String) {
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
            text = value.ifEmpty { "-" },
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = if (value.isEmpty()) Color(0xFF95A5A6) else Color(0xFF2C3E50),
            lineHeight = 20.sp
        )
    }
}

@Composable
fun FileAttachmentCardKematian(label: String, filename: String, primaryColor: Color) {
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
                    text = filename.ifEmpty { "-" },
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
internal fun ActionButtonsOutput(
    onEdit: () -> Unit,
    onConfirm: () -> Unit,
    primaryColor: Color,
    modifier: Modifier = Modifier,
    isConfirmEnabled: Boolean = true
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
                contentColor = Color.White
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
fun SuratkematianPreview() {
    SuratkematianPreview(rememberNavController())
}
