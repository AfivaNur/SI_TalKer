package com.afiva.appskelurahan.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.afiva.appskelurahan.model.*
import kotlinx.serialization.json.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    dataJson: String?
) {
    val context = LocalContext.current
    val primaryColor = Color(0xFF00695C)
    val accentColor = Color(0xFF4DB6AC)
    val json = Json { ignoreUnknownKeys = true }

    val dataMap = try {
        dataJson?.let { json.decodeFromString<Map<String, JsonElement>>(it) }
    } catch (e: Exception) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Gagal memuat data: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail Pengajuan",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor),
                modifier = Modifier
                    .shadow(10.dp)
                    .background(Brush.horizontalGradient(listOf(primaryColor, accentColor)))
            )
        },
        containerColor = Color(0xFFEDF7FA)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    dataMap?.let { map ->
                        val table = map["tableName"]?.jsonPrimitive?.content ?: "Unknown"
                        val detailData = map["detailData"]?.jsonObject
                        val dataJsonElement = detailData?.get("data")

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(24.dp))
                                .shadow(8.dp, RoundedCornerShape(24.dp))
                                .background(Brush.verticalGradient(colors = listOf(Color.White, Color(0xFFF6FBFF))))
                                .animateContentSize(spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFE0F2F1))
                                        .padding(12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Jenis Surat: $table",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        color = Color(0xFF0F2C3E),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                HorizontalDivider(color = Color(0xFFD1E3E8), thickness = 1.5.dp)

                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    when (table) {
                                        "SuratPengantarRTData" -> {
                                            val data = json.decodeFromJsonElement<SuratPengantarRTData>(dataJsonElement!!)
                                            DataField("Nama Ketua RT", data.namaKetuaRtPengantar)
                                            DataField("Jabatan", data.jabatanKetuaRt)
                                            DataField("Nama", data.namaPengantar)
                                            DataField("TTL", "${data.tempatLahirPengantar}, ${data.tanggalLahirPengantar}")
                                            DataField("Jenis Kelamin", data.jenisKelaminPengantar)
                                            DataField("Kewarganegaraan", data.kewarganegaraan)
                                            DataField("Agama", data.agamaPengantar)
                                            DataField("Status Perkawinan", data.statusPerkawinanPengantar)
                                            DataField("Pekerjaan", data.pekerjaanPengantar)
                                            DataField("NIK", data.nikPengantar)
                                            DataField("Alamat", data.alamatPengantar)
                                            DataField("RT", data.rtPengantar)
                                            DataField("RW", data.rwPengantar)
                                            DataField("Keterangan Tujuan", data.keteranganTujuanPengantar)
                                            DataField("File Persyaratan", data.namaFilePersyaratan)
                                        }
                                        "DomisiliData" -> {
                                            val data = json.decodeFromJsonElement<DomisiliData>(dataJsonElement!!)
                                            DataField("Nama", data.nama)
                                            DataField("TTL", "${data.tempatLahir}, ${data.tanggalLahir}")
                                            DataField("Jenis Kelamin", data.jenisKelamin)
                                            DataField("Agama", data.agama)
                                            DataField("Status Perkawinan", data.statusPerkawinan)
                                            DataField("Pekerjaan", data.pekerjaan)
                                            DataField("Alamat", data.alamat)
                                            DataField("RT", data.rt)
                                            DataField("RW", data.rw)
                                            DataField("Tujuan Surat", data.tujuanSurat)
                                            DataField("File Surat Pengantar RT", data.namaFileSuratPengantarRT)
                                            DataField("File Persyaratan", data.namaFilePersyaratan)
                                        }
                                        "TidakMampuData" -> {
                                            val data = json.decodeFromJsonElement<TidakMampuInsertData>(dataJsonElement!!)
                                            DataField("Nama", data.nama)
                                            DataField("NIK", data.nik_tidak_mampu)
                                            DataField("Tempat Lahir", data.tempat_lahir)
                                            DataField("Tanggal Lahir", data.tanggal_lahir)
                                            DataField("Jenis Kelamin", data.jenis_kelamin)
                                            DataField("Agama", data.agama)
                                            DataField("Kewarganegaraan", data.kewarganegaraan)
                                            DataField("Pekerjaan", data.pekerjaan)
                                            DataField("Alamat", data.alamat)
                                            DataField("Tujuan Surat", data.tujuan_surat)
                                            DataField("RT", data.selected_rt)
                                            DataField("RW", data.selected_rw)
                                            DataField("Kategori Keterangan", data.kategori_keterangan)
                                            DataField("File Surat Pengantar RT", data.nama_file_surat_pengantar_rt)
                                            DataField("File Persyaratan", data.nama_file_persyaratan)
                                        }
                                        "UsahaData" -> {
                                            val data = json.decodeFromJsonElement<UsahaInsertData>(dataJsonElement!!)
                                            DataField("Nama Usaha", data.nama_usaha)
                                            DataField("NIK", data.nik_usaha)
                                            DataField("Tempat Lahir", data.tempat_lahir_usaha)
                                            DataField("Tanggal Lahir", data.tanggal_lahir_usaha)
                                            DataField("Jenis Kelamin", data.jenis_kelamin_usaha)
                                            DataField("Alamat", data.alamat)
                                            DataField("Jenis Usaha", data.jenis_usaha)
                                            DataField("Ukuran Tempat Usaha", "${data.ukuran_tempat_usaha} m²")
                                            DataField("Lokasi Usaha", data.lokasi_usaha)
                                            DataField("Lama Usaha", "${data.lama_usaha} tahun")
                                            DataField("Tujuan Surat", data.tujuan_surat_usaha)
                                            DataField("RT", data.selected_rt_usaha?.toString())
                                            DataField("RW", data.selected_rw_usaha?.toString())
                                            DataField("File Surat Pengantar RT", data.nama_file_surat_pengantar_rt)
                                            DataField("File Persyaratan", data.nama_file_persyaratan)
                                        }
                                        "PindahDatangData" -> {
                                            val data = json.decodeFromJsonElement<PindahDatangInsertData>(dataJsonElement!!)
                                            DataField("No. KK Asal", data.kk_asal)
                                            DataField("Nama Kepala Keluarga Asal", data.nama_kepala_keluarga_asal)
                                            DataField("Alamat Asal", data.alamat_asal)
                                            DataField("No. Telepon Asal", data.no_telepon_asal)
                                            DataField("Kode Pos Asal", data.kode_pos_asal)
                                            DataField("RT Asal", data.rt_asal?.toString())
                                            DataField("RW Asal", data.rw_asal?.toString())
                                            DataField("Alasan Pindah", data.alasan_pindah)
//                                            DataField("Alasan Lainnya", data.alasan_pindah_lainnya)
                                            DataField("Alamat Pindah", data.alamat_pindah)
                                            DataField("Kode Pos Pindah", data.kode_pos_pindah)
                                            DataField("No. Telepon Pindah", data.no_telepon_pindah)
                                            DataField("Klasifikasi Pindah", data.klasifikasi_pindah)
                                            DataField("Jenis Kepindahan", data.jenis_kepindahan)
                                            DataField("Status No. KK Tidak Pindah", data.status_no_kk_tidak_pindah)
                                            DataField("Status No. KK Pindah", data.status_no_kk_pindah)
                                            DataField("Rencana Tanggal Pindah", data.rencana_tanggal_pindah)
                                            DataField("NIK Keluarga Datang (Pindah)", data.nik_keluarga_yang_datang_pindah)
                                            DataField("Nama Keluarga Datang (Pindah)", data.nama_keluarga_yang_datang_pindah)
                                            DataField("RT Pindah", data.rt_pindah?.toString())
                                            DataField("RW Pindah", data.rw_pindah?.toString())
                                            DataField("No. KK Tujuan", data.nokk_tujuan)
                                            DataField("Nama Kepala Keluarga Tujuan", data.nama_kepala_keluarga_tujuan)
                                            DataField("NIK Kepala Keluarga Tujuan", data.nik_kepala_keluarga_tujuan)
                                            DataField("Status KK Pindah Tujuan", data.status_kk_pindah_tujuan)
                                            DataField("Tanggal Kedatangan Tujuan", data.tanggal_kedatangan_tujuan)
                                            DataField("Alamat Tujuan", data.alamat_tujuan)
                                            DataField("NIK Keluarga Tujuan", data.nik_keluarga_yang_datang_tujuan)
                                            DataField("Nama Keluarga Tujuan", data.nama_keluarga_yang_datang_tujuan)
                                            DataField("RT Tujuan", data.rt_tujuan?.toString())
                                            DataField("RW Tujuan", data.rw_tujuan?.toString())
                                            DataField("File Surat Pengantar RT", data.nama_file_surat_pengantar_rt)
                                            DataField("File Persyaratan", data.nama_file_persyaratan)
                                        }
                                        "KeteranganData" -> {
                                            val data = json.decodeFromJsonElement<KeteranganInsertData>(dataJsonElement!!)
                                            DataField("Nama Pejabat", data.nama_pejabat)
                                            DataField("NIP", data.nip)
                                            DataField("Jabatan", data.jabatan)
                                            DataField("NIK", data.nik)
                                            DataField("Nama", data.nama)
                                            DataField("Tempat Lahir", data.tempat_lahir)
                                            DataField("Tanggal Lahir", data.tanggal_lahir)
                                            DataField("Jenis Kelamin", data.jenis_kelamin)
                                            DataField("Warga Negara", data.warga_negara)
                                            DataField("Agama", data.agama)
                                            DataField("Pekerjaan", data.pekerjaan)
                                            DataField("Alamat", data.alamat)
                                            DataField("Tujuan Surat", data.tujuan_surat)
                                            DataField("RT", data.rt?.toString())
                                            DataField("RW", data.rw?.toString())
                                            DataField("File Surat Pengantar RT", data.nama_file_surat_pengantar_rt)
                                            DataField("File Persyaratan", data.nama_file_persyaratan)
                                        }
                                        "KematianData" -> {
                                            val data = json.decodeFromJsonElement<KematianInsertData>(dataJsonElement!!)
                                            DataField("Nama Jenazah", data.nama_jenazah)
                                            DataField("Jenis Kelamin", data.jenis_kelamin_jenazah)
                                            DataField("Umur", data.umur_jenazah?.toString()?.plus(" Tahun"))
                                            DataField("Pekerjaan", data.pekerjaan_jenazah)
                                            DataField("Alamat", data.alamat_jenazah)
                                            DataField("Tanggal Kematian", data.tanggal_kematian_jenazah)
                                            DataField("Sebab Kematian", data.sebab_kematian_jenazah)
                                            DataField("Yang Menerangkan", data.nama_yang_menerangkan)
                                            DataField("Yang Melapor", data.nama_yang_melapor)
                                            DataField("Hubungan Pelapor", data.hubungan_pelapor)
                                            DataField("RT", data.selected_rt_kematian?.toString())
                                            DataField("RW", data.selected_rw_kematian?.toString())
                                            DataField("File Surat Pengantar RT", data.nama_file_surat_pengantar_rt)
                                            DataField("File Persyaratan", data.nama_file_persyaratan)
                                        }

                                        // Tambahkan if-else lain sesuai kebutuhan (DomisiliData, KematianData, dst)
                                        else -> {
                                            Text(
                                                text = "Tipe data tidak dikenali",
                                                color = Color(0xFFD32F2F),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } ?: run {
                        Text(
                            text = "Data tidak tersedia",
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DataField(
    label: String,
    value: String?,
    labelWeight: Float = 0.45f,
    valueWeight: Float = 0.55f,
    labelFontSize: androidx.compose.ui.unit.TextUnit = 14.sp,
    valueFontSize: androidx.compose.ui.unit.TextUnit = 14.sp
) {
    val cleanValue = value?.replace("+", " ")?.trim() // Hapus tanda '+' dan spasi ekstra

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF1F8FA))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = labelFontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0F2C3E),
                modifier = Modifier
                    .weight(labelWeight)
                    .wrapContentWidth(Alignment.Start),
                maxLines = 2
            )
            Text(
                text = cleanValue ?: "Tidak tersedia",
                fontSize = valueFontSize,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF4B5EAA),
                modifier = Modifier
                    .weight(valueWeight)
                    .wrapContentWidth(Alignment.End),
                textAlign = TextAlign.End,
                maxLines = 2
            )
        }
    }
}
