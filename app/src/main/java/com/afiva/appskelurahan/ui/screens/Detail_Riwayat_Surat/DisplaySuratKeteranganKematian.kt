package com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat

import androidx.lifecycle.viewmodel.compose.viewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.viewmodel.KematianViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewDisplaySuratKeteranganKematian(navController: NavController) {
    val kematianViewModel: KematianViewModel = viewModel(navController.getBackStackEntry("form_jenazah"))
    val kematianData by kematianViewModel.dataKematian.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val supabase = SupabaseProvider.client

    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)
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
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_banyuasin),
                                    contentDescription = "Logo",
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
                                text = "Form Pengajuan",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )
        },
        containerColor = surfaceColor
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header dengan gradient
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
                        InfoChipKematian("RW",kematianData?.rwKematian ?: "", modifier = Modifier.weight(1f))
                    }
                }

                EnhancedSectionCardKematian(
                    title = "Data Jenazah",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowKematian("Nama Jenazah", kematianData?.namaJenazah ?: "",)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowKematian("Jenis Kelamin", kematianData?.jenisKelaminJenazah ?: "",)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowKematian("Umur", kematianData?.umurJenazah ?: "",)
                            }
                        }
                        EnhancedDataRowKematian("Pekerjaan", kematianData?.pekerjaanJenazah ?: "",)
                        EnhancedDataRowMultilineKematian("Alamat Jenazah", kematianData?.alamatJenazah ?: "",)
                    }
                }

                EnhancedSectionCardKematian(
                    title = "Informasi Kematian",
                    icon = Icons.Default.DateRange,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowKematian("Tanggal Kematian", kematianData?.tanggalKematianJenazah ?: "",)
                        EnhancedDataRowMultilineKematian("Sebab Kematian", kematianData?.sebabKematianJenazah ?: "",)
                        EnhancedDataRowKematian("Yang Menerangkan", kematianData?.namaYangMenerangkan ?: "",)
                    }
                }

                EnhancedSectionCardKematian(
                    title = "Data Pelapor",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowKematian("Nama Pelapor", kematianData?.namaYangMelapor ?: "",)
                        EnhancedDataRowKematian("Hubungan dengan Jenazah", kematianData?.hubunganPelapor ?: "",)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                ActionButtonsKematian(
                    onCancel = { navController.navigateUp() },
                    onConfirm = { /* Handle submission logic here */
                        coroutineScope.launch {
                            val data = kematianData
                            if (data != null) {
                                val response = supabase
                                    .postgrest["KematianData"]
                                    .insert(
                                        mapOf(
                                            "nama_jenazah" to data.namaJenazah,
                                            "jenis_kelamin_jenazah" to data.jenisKelaminJenazah,
                                            "umur_jenazah" to data.umurJenazah,
                                            "pekerjaan_jenazah" to data.pekerjaanJenazah,
                                            "alamat_jenazah" to data.alamatJenazah,
                                            "tanggal_kematian_jenazah" to data.tanggalKematianJenazah,
                                            "sebab_kematian_jenazah" to data.sebabKematianJenazah,
                                            "nama_yang_menerangkan" to data.namaYangMenerangkan,
                                            "selected_rt_kematian" to data.rwKematian,
                                            "selected_rw_kematian" to data.rwKematian,
                                            "nama_yang_melapor" to data.namaYangMelapor,
                                            "hubungan_pelapor" to data.hubunganPelapor,
                                            "nama_file_surat_pengantar_rt" to data.namaFileSuratPengantarRT,
                                            "nama_file_persyaratan" to data.namaFilePersyaratan
                                        )
                                    )

                                navController.navigate("beranda")
                                snackbarHostState.showSnackbar("Berhasil Diajukan")

                            }
                        }

                    },
                    primaryColor = primaryColor
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
internal fun ActionButtonsKematian(onCancel: () -> Unit, onConfirm: () -> Unit, primaryColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryColor)
        ) {
            Text("Batal", fontWeight = FontWeight.SemiBold)
        }

        Button(
            onClick = onConfirm,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
        ) {
            Text("Konfirmasi", fontWeight = FontWeight.SemiBold)
        }
    }
}


@Preview
@Composable
fun PreviewDisplaySuratKeteranganKematian() {
    PreviewDisplaySuratKeteranganKematian(rememberNavController())
}
