package com.afiva.appskelurahan.ui.screens.Surat_Tidak_mampu

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
import androidx.compose.material.icons.filled.Home
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
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.TidakMampuInsertData
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.ui.screens.Domisili.ActionButtonsOutput
import com.afiva.appskelurahan.v_model.TidakMampuViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewSuratTidakMampu(navController: NavController) {
    BackHandler(enabled = true) {}
    val tidakMampuViewModel: TidakMampuViewModel = viewModel(navController.getBackStackEntry("surat_keterangan_tidak_mampu"))
    val tidakMampuData by tidakMampuViewModel.dataTidakMampu.collectAsState()
    val hasActivePengajuan by tidakMampuViewModel.hasActivePengajuan
    val isLoading by tidakMampuViewModel.isLoading
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    // Validasi data sebelum pengajuan
    val isDataValid = tidakMampuData?.let { data ->
        data.nik.isNotBlank() && data.nama.isNotBlank() && data.alamat.isNotBlank() &&
                data.rt.isNotBlank() && data.rw.isNotBlank() && data.tujuanSurat.isNotBlank()
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
                    navController.navigate("login")
                }
            } else {
                tidakMampuViewModel.checkActivePengajuan(SessionManager.currentUser?.id.toString())
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
                                text = "KETERANGAN TIDAK MAMPU",
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                InfoHeaderTidakMampu()
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EnhancedSectionCardTidakMampu(
                    title = "Informasi Wilayah",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChipTidakMampu("RT", tidakMampuData?.rt ?: "", modifier = Modifier.weight(1f))
                        InfoChipTidakMampu("RW", tidakMampuData?.rw ?: "", modifier = Modifier.weight(1f))
                    }
                }

                EnhancedSectionCardTidakMampu(
                    title = "Data Pribadi",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowTidakMampu("NIK", tidakMampuData?.nik ?: "")
                        EnhancedDataRowTidakMampu("Nama Lengkap", tidakMampuData?.nama ?: "")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowTidakMampu("Tempat Lahir", tidakMampuData?.tempatLahir ?: "")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowTidakMampu("Tanggal Lahir", tidakMampuData?.tanggalLahir ?: "")
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowTidakMampu("Jenis Kelamin", tidakMampuData?.jenisKelamin ?: "")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowTidakMampu("Agama", tidakMampuData?.agama ?: "")
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowTidakMampu("Kewarganegaraan", tidakMampuData?.kewarganegaraan ?: "")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowTidakMampu("Pekerjaan", tidakMampuData?.pekerjaan ?: "")
                            }
                        }
                    }
                }

                EnhancedSectionCardTidakMampu(
                    title = "Alamat & Tujuan",
                    icon = Icons.Default.Home,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowMultilineTidakMampu("Alamat Lengkap", tidakMampuData?.alamat ?: "")
                        EnhancedDataRowMultilineTidakMampu("Tujuan Pengajuan", tidakMampuData?.tujuanSurat ?: "")
                        EnhancedDataRowTidakMampu("Kategori Keterangan", tidakMampuData?.kategoriKeterangan ?: "")
                    }
                }

                EnhancedSectionCardTidakMampu(
                    title = "Berkas Lampiran",
                    icon = Icons.Default.Info,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FileAttachmentCardTidakMampu("Surat Pengantar RT", tidakMampuData?.namaFileSuratPengantarRT ?: "", primaryColor)
                        FileAttachmentCardTidakMampu("Berkas Persyaratan", tidakMampuData?.namaFilePersyaratan ?: "", primaryColor)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                                text = "Anda memiliki pengajuan tidak mampu yang sedang diproses. Silakan cek riwayat pengajuan.",
                                fontSize = 14.sp,
                                color = Color(0xFF2C3E50)
                            )
                        }
                    }
                }

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
                                    message = "Pengajuan tidak mampu sedang diproses. Lihat riwayat?",
                                    actionLabel = "Lihat",
                                    duration = SnackbarDuration.Long
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    navController.navigate(Screen.RiwayatScreen.route)
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                tidakMampuViewModel.submitPengajuan(
                                    onSuccess = {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Berhasil Diajukan!",
                                                duration = SnackbarDuration.Long
                                            )
                                            tidakMampuViewModel.resetData()
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
                                                tidakMampuViewModel.submitPengajuan(
                                                    onSuccess = {
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Berhasil Diajukan!",
                                                                duration = SnackbarDuration.Long
                                                            )
                                                            tidakMampuViewModel.resetData()
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
fun InfoHeaderTidakMampu() {
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
                    color = Color(0xFF5D6D7E)
                )
            }
        }
    }
}

@Composable
fun EnhancedSectionCardTidakMampu(
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
            // Header Section
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

            // Content Section
            Column(
                modifier = Modifier.padding(16.dp),
                content = content
            )
        }
    }
}

@Composable
fun InfoChipTidakMampu(label: String, value: String, modifier: Modifier = Modifier) {
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
                color = if (value == "-") Color(0xFF95A5A6) else Color(0xFF00897B)
            )
        }
    }
}

@Composable
fun EnhancedDataRowTidakMampu(label: String, value: String) {
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
            color = if (value == "-") Color(0xFF95A5A6) else Color(0xFF2C3E50)
        )
    }
}

@Composable
fun EnhancedDataRowMultilineTidakMampu(label: String, value: String) {
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
            fontWeight = FontWeight.Normal,
            color = if (value == "-") Color(0xFF95A5A6) else Color(0xFF2C3E50),
            lineHeight = 20.sp
        )
    }
}

@Composable
fun FileAttachmentCardTidakMampu(
    label: String,
    filename: String,
    primaryColor: Color,
    hasFile: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (hasFile) primaryColor.copy(alpha = 0.2f) else Color(0xFF95A5A6).copy(alpha = 0.2f)
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
                tint = if (hasFile) primaryColor else Color(0xFF95A5A6),
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
                    color = if (hasFile) Color(0xFF5D6D7E) else Color(0xFF95A5A6)
                )
            }
            if (hasFile) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF27AE60),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
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
fun ReviewSuratTidakMampuPreview() {
    ReviewSuratTidakMampu(rememberNavController())
}