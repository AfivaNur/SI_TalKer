package com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.ui.screens.Domisili.InfoChipDomisili
import com.afiva.appskelurahan.v_model.SuratPengantarRTViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewDisplaySuratPengantarRT(navController: NavController) {
    val pengantarRTViewModel: SuratPengantarRTViewModel = viewModel(navController.getBackStackEntry("surat_pengantar"))
    val pengantarRTData by pengantarRTViewModel.dataSuratPengantarRT.collectAsState()
    val coroutineScope = rememberCoroutineScope()

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
                                text = "SURAT PENGANTAR RT",
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
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
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
                InfoHeaderPengantar(title = "Pastikan data sudah benar")
            }

            // Content dengan padding yang konsisten
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section Wilayah
                EnhancedSectionCardPengantar(
                    title = "Wilayah",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChipDomisili(
                            label = "RT",
                            value = pengantarRTData?.rtPengantar ?: "",
                            modifier = Modifier.weight(1f)
                        )
                        InfoChipDomisili(
                            label = "RW",
                            value = pengantarRTData?.rwPengantar ?: "",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    EnhancedDataRowPengantar(
                        label = "Nama Ketua RT",
                        value = pengantarRTData?.namaKetuaRtPengantar ?: ""
                    )
                    EnhancedDataRowPengantar(
                        label = "Jabatan",
                        value = pengantarRTData?.jabatanKetuaRt ?: ""
                    )
                }

                // Section Data Pemohon
                EnhancedSectionCardPengantar(
                    title = "Data Pemohon",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowPengantar(
                            label = "NIK",
                            value = pengantarRTData?.nikPengantar ?: ""
                        )
                        EnhancedDataRowPengantar(
                            label = "Nama Lengkap",
                            value = pengantarRTData?.namaPengantar ?: ""
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowPengantar(
                                    label = "Tempat Lahir",
                                    value = pengantarRTData?.tempatLahirPengantar ?: ""
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowPengantar(
                                    label = "Tanggal Lahir",
                                    value = pengantarRTData?.tanggalLahirPengantar ?: ""
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowPengantar(
                                    label = "Jenis Kelamin",
                                    value = pengantarRTData?.jenisKelaminPengantar ?: ""
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowPengantar(
                                    label = "Agama",
                                    value = pengantarRTData?.agamaPengantar ?: ""
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowPengantar(
                                    label = "Kewarganegaraan",
                                    value = pengantarRTData?.kewarganegaraan ?: ""
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowPengantar(
                                    label = "Status Perkawinan",
                                    value = pengantarRTData?.statusPerkawinanPengantar ?: ""
                                )
                            }
                        }
                        EnhancedDataRowPengantar(
                            label = "Pekerjaan",
                            value = pengantarRTData?.pekerjaanPengantar ?: ""
                        )
                        EnhancedDataRowMultilinePengantar(
                            label = "Alamat",
                            value = pengantarRTData?.alamatPengantar ?: ""
                        )
                    }
                }

                // Section Tujuan Surat
                EnhancedSectionCardPengantar(
                    title = "Keterangan Tujuan",
                    icon = Icons.Default.Info,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    EnhancedDataRowMultilinePengantar(
                        label = "Tujuan Surat",
                        value = pengantarRTData?.keteranganTujuanPengantar ?: ""
                    )
                }

                // Section Berkas Lampiran
                EnhancedSectionCardPengantar(
                    title = "Berkas Lampiran",
                    icon = Icons.Default.Email,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    FileAttachmentCardPengantar(
                        "Berkas Persyaratan", pengantarRTData?.namaFilePersyaratan ?: "",
                        primaryColor = primaryColor,
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                ActionButtonsOutput(
                    onEdit = { navController.navigateUp() },
                    onConfirm = { /* Handle submission logic here */
                        coroutineScope.launch {
                            val data = pengantarRTData
                            if (data != null) {
                                val response = supabase
                                    .postgrest["SuratPengantarRTData"]
                                    .insert(
                                        mapOf(
                                            "selected_rw_pengantar" to data.rwPengantar,
                                            "selected_rt_pengantar" to data.rtPengantar,
                                            "nama_ketua_rt_pengantar" to data.namaKetuaRtPengantar,
                                            "jabatan_ketua_rt" to data.jabatanKetuaRt,
                                            "nama_pengantar" to data.namaPengantar,
                                            "nik_pengantar" to data.nikPengantar,
                                            "tempat_lahir_pengantar" to data.tempatLahirPengantar,
                                            "tanggal_lahir_pengantar" to data.tanggalLahirPengantar,
                                            "jenis_kelamin_pengantar" to data.jenisKelaminPengantar,
                                            "agama_pengantar" to data.agamaPengantar,
                                            "kewarganegaraan" to data.kewarganegaraan,
                                            "status_perkawinan_pengantar" to data.statusPerkawinanPengantar,
                                            "pekerjaan_pengantar" to data.pekerjaanPengantar,
                                            "alamat_pengantar" to data.alamatPengantar,
                                            "keterangan_tujuan_pengantar" to data.keteranganTujuanPengantar,
                                            "nama_file_persyaratan" to data.namaFilePersyaratan
                                        )
                                    )

                                navController.navigate("beranda")
//                                snackbarHostState.showSnackbar("Data Tersimpan")

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
fun InfoHeaderPengantar(title: String) {
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
fun EnhancedSectionCardPengantar(
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
fun EnhancedDataRowPengantar(label: String, value: String) {
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
fun EnhancedDataRowMultilinePengantar(label: String, value: String) {
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
            color = Color(0xFF2C3E50),
            lineHeight = 20.sp
        )
    }
}

@Composable
fun FileAttachmentCardPengantar(label: String, filename: String, primaryColor: Color) {
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

@Preview
@Composable
fun PreviewDisplaySuratPengantarRT() {
    PreviewDisplaySuratPengantarRT(rememberNavController())
}
