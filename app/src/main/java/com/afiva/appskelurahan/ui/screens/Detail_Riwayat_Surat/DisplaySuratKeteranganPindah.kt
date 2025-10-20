package com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat

import android.annotation.SuppressLint
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
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.v_model.PindahDatangViewModel
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.ui.screens.Domisili.EnhancedDataRowMultilineDomisili
import com.afiva.appskelurahan.ui.screens.Domisili.FileAttachmentCardDomisili
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewDisplaySuratKeteranganPindah(navController: NavController) {
    val PindahDatangViewModel: PindahDatangViewModel = viewModel(navController.getBackStackEntry(Screen.DataDaerahAsal.route))
    val pindahDatangData by PindahDatangViewModel.dataPindahDatang.collectAsState()
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
                        FileAttachmentCardDomisili("Surat Pengantar RT", pindahDatangData?.namaFileSuratPengantarRT ?: "", primaryColor)
                        FileAttachmentCardDomisili("Berkas Persyaratan", pindahDatangData?.namaFilePersyaratan ?: "", primaryColor)
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                ActionButtonsPindah(
                    onEdit = { navController.navigateUp() },
                    onConfirm = {
                        coroutineScope.launch {
                            val data = pindahDatangData // Simpan ke variabel lokal
                            if (data != null) {
                                try {
                                    supabase.postgrest["PindahDatangData"].insert(
                                        mapOf(
                                            "kk_asal" to data.kkAsal,
                                            "nama_kepala_keluarga_asal" to data.namaKepalaKeluargaAsal,
                                            "alamat_asal" to data.alamatAsal,
                                            "no_telepon_asal" to data.noTeleponAsal,
                                            "kode_pos_asal" to data.kodePosAsal,
                                            "rt_asal" to data.rtAsal,
                                            "rw_asal" to data.rwAsal,
                                            "alasan_pindah" to data.alasanPindah,
                                            "alamat_pindah" to data.alamatPindah,
                                            "kode_pos_pindah" to data.kodePosPindah,
                                            "no_telepon_pindah" to data.noTeleponPindah,
                                            "klasifikasi_pindah" to data.klasifikasiPindah,
                                            "jenis_kepindahan" to data.jenisKepindahan,
                                            "status_no_kk_tidak_pindah" to data.statusNoKkTidakPindah,
                                            "status_no_kk_pindah" to data.statusNoKkPindah,
                                            "rencana_tanggal_pindah" to data.rencanaTanggalPindah,
                                            "nik_keluarga_yang_datang_pindah" to data.nikKeluargaYangDatangPindah,
                                            "nama_keluarga_yang_datang_pindah" to data.namaKeluargaYangDatangPindah,
                                            "rt_pindah" to data.rtPindah,
                                            "rw_pindah" to data.rwPindah,
                                            "nokk_tujuan" to data.noKkTujuan,
                                            "nama_kepala_keluarga_tujuan" to data.namaKepalaKeluargaTujuan,
                                            "nik_kepala_keluarga_tujuan" to data.nikKepalaKeluargaTujuan,
                                            "status_kk_pindah_tujuan" to data.statusKkPindahTujuan,
                                            "tanggal_kedatangan_tujuan" to data.tanggalKedatanganTujuan,
                                            "alamat_tujuan" to data.alamatTujuan,
                                            "nik_keluarga_yang_datang_tujuan" to data.nikKeluargaYangDatangTujuan,
                                            "nama_keluarga_yang_datang_tujuan" to data.namaKeluargaYangDatangTujuan,
                                            "rt_tujuan" to data.rtTujuan,
                                            "rw_tujuan" to data.rwTujuan,
                                            "nama_file_surat_pengantar_rt" to data.namaFileSuratPengantarRT,
                                            "nama_file_persyaratan" to data.namaFilePersyaratan
                                        )
                                    )
                                    navController.navigate("beranda")
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Gagal menyimpan data: ${e.message}")
                                }
                            } else {
                                snackbarHostState.showSnackbar("Data tidak lengkap")
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
internal fun ActionButtonsPindah(
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
fun PreviewDisplaySuratKeteranganPindah() {
    PreviewDisplaySuratKeteranganPindah(rememberNavController())
}