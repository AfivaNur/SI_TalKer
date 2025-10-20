package com.afiva.appskelurahan.ui.screens.Detail_Riwayat_Surat

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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.afiva.appskelurahan.v_model.UsahaViewModel
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.ui.screens.Domisili.EnhancedDataRowMultilineDomisili
import com.afiva.appskelurahan.ui.screens.Domisili.FileAttachmentCardDomisili
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewDisplaySuratKeteranganUsaha(navController: NavController) {
    val usahaViewModel: UsahaViewModel = viewModel(navController.getBackStackEntry("surat_keterangan_usaha"))
    val usahaData by usahaViewModel.dataUsaha.collectAsState()
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
                                text = "KETERANGAN USAHA",
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
                InfoHeaderUsaha()
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EnhancedSectionCardUsaha(
                    title = "Informasi Wilayah",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChipUsaha("RT", usahaData?.rtUsaha ?: "", modifier = Modifier.weight(1f))
                        InfoChipUsaha("RW", usahaData?.rwUsaha ?: "", modifier = Modifier.weight(1f))
                    }
                }

                EnhancedSectionCardUsaha(
                    title = "Data Pribadi",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowUsaha("NIK", usahaData?.nikUsaha ?: "")
                        EnhancedDataRowUsaha("Nama Lengkap", usahaData?.namaUsaha ?: "")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowUsaha("Tempat Lahir", usahaData?.tempatLahirUsaha ?: "")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                EnhancedDataRowUsaha("Tanggal Lahir", usahaData?.tanggalLahirUsaha ?: "")
                            }
                        }
                        EnhancedDataRowUsaha("Jenis Kelamin", usahaData?.jenisKelaminUsaha ?: "")
                    }
                }

                EnhancedSectionCardUsaha(
                    title = "Detail Usaha",
                    icon = Icons.Default.ShoppingCart,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        EnhancedDataRowUsaha("Jenis Usaha", usahaData?.jenisUsaha ?: "")
                        EnhancedDataRowUsaha("Ukuran Tempat Usaha", "${usahaData?.ukuranTempatUsaha ?: ""} m²")
                        EnhancedDataRowUsaha("Lama Usaha", "${usahaData?.lamaUsaha ?: ""} tahun")
                        EnhancedDataRowMultilineDomisili("Lokasi Usaha", usahaData?.lokasiUsaha ?: "")
                        EnhancedDataRowMultilineDomisili("Alamat", usahaData?.alamat ?: "")
                        EnhancedDataRowMultilineDomisili("Tujuan Pengajuan", usahaData?.tujuanSuratUsaha ?: "")
                    }
                }

                EnhancedSectionCardUsaha(
                    title = "Berkas Lampiran",
                    icon = Icons.Default.Info,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FileAttachmentCardDomisili("Surat Pengantar RT", usahaData?.namaFileSuratPengantarRT ?: "", primaryColor)
                        FileAttachmentCardDomisili("Berkas Persyaratan", usahaData?.namaFilePersyaratan ?: "", primaryColor)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                ActionButtonsOutput(
                    onEdit = { navController.navigateUp() },
                    onConfirm = {
                        coroutineScope.launch {
                            val data = usahaData
                            if (data != null) {
                                val response = supabase
                                    .postgrest["UsahaData"]
                                    .insert(
                                        mapOf(
                                            "nama_usaha" to data.namaUsaha,
                                            "nik_usaha" to data.nikUsaha,
                                            "tanggal_lahir_usaha" to data.tanggalLahirUsaha,
                                            "tempat_lahir_usaha" to data.tempatLahirUsaha,
                                            "jenis_kelamin_usaha" to data.jenisKelaminUsaha,
                                            "alamat" to data.alamat,
                                            "jenis_usaha" to data.jenisUsaha,
                                            "ukuran_tempat_usaha" to data.ukuranTempatUsaha,
                                            "lokasi_usaha" to data.lokasiUsaha,
                                            "lama_usaha" to data.lamaUsaha,
                                            "tujuan_surat_usaha" to data.tujuanSuratUsaha,
                                            "selected_rt_usaha" to data.rtUsaha,
                                            "selected_rw_usaha" to data.rwUsaha,
                                            "nama_file_surat_pengantar_rt" to data.namaFileSuratPengantarRT,
                                            "nama_file_persyaratan" to data.namaFilePersyaratan
                                        )
                                    )
                                snackbarHostState.showSnackbar("Berhasil Diajukan")
                                navController.navigate(Screen.Beranda.route)
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
fun InfoHeaderUsaha() {
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
fun EnhancedSectionCardUsaha(
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
fun InfoChipUsaha(label: String, value: String, modifier: Modifier = Modifier) {
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
fun EnhancedDataRowUsaha(label: String, value: String) {
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


@Preview(showBackground = true)
@Composable
fun PreviewDisplaySuratKeteranganUsaha() {
    PreviewDisplaySuratKeteranganUsaha(rememberNavController())
}

