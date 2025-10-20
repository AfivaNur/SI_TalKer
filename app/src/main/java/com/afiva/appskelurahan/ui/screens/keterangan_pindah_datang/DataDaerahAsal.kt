package com.afiva.appskelurahan.ui.screens.keterangan_pindah_datang

import android.app.DatePickerDialog
import android.net.Uri
import kotlinx.coroutines.launch
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.PindahDatangData
import com.afiva.appskelurahan.ui.screens.Domisili.SectionCard
import com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.FileUploadSection
import com.afiva.appskelurahan.uploadToSupabaseSDK
import com.afiva.appskelurahan.v_model.PindahDatangViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataDaerahAsal(navController: NavController) { BackHandler(enabled = true) {}
    val context = LocalContext.current
    val supabase = SupabaseProvider.client
    val viewModel: PindahDatangViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Color theme
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    // Daerah Asal
    var kkAsal by remember { mutableStateOf("") }
    var namaKepalaKeluargaAsal by remember { mutableStateOf("") }
    var alamatAsal by remember { mutableStateOf("") }
    var noTeleponAsal by remember { mutableStateOf("") }
    var kodePosAsal by remember { mutableStateOf("") }
    var selectedRTAsal by remember { mutableStateOf("") }
    var selectedRWAsal by remember { mutableStateOf("") }
    var expandedRTAsal by remember { mutableStateOf(false) }
    var expandedRWAsal by remember { mutableStateOf(false) }

    // Kepindahan
    var alasanPindah by remember { mutableStateOf("") }
    var alamatPindah by remember { mutableStateOf("") }
    var kodePosPindah by remember { mutableStateOf("") }
    var noTeleponPindah by remember { mutableStateOf("") }
    var klasifikasiPindah by remember { mutableStateOf("") }
    var jenisKepindahan by remember { mutableStateOf("") }
    var statusNoKKTidakPindah by remember { mutableStateOf("") }
    var statusNoKKPindah by remember { mutableStateOf("") }
    var rencanaTanggalPindah by remember { mutableStateOf("") }
    var nikKeluargaYangDatangPindah by remember { mutableStateOf("") }
    var namaKeluargaYangDatangPindah by remember { mutableStateOf("") }
    var selectedRTPindah by remember { mutableStateOf("") }
    var selectedRWPindah by remember { mutableStateOf("") }
    var expandedRTPindah by remember { mutableStateOf(false) }
    var expandedRWPindah by remember { mutableStateOf(false) }
    var alasanPindahExpanded by remember { mutableStateOf(false) }
    var klasifikasiPindahExpanded by remember { mutableStateOf(false) }
    var jenisKepindahanExpanded by remember { mutableStateOf(false) }
    var statusNoKKTidakPindahExpanded by remember { mutableStateOf(false) }
    var statusNoKKPindahExpanded by remember { mutableStateOf(false) }

    // Daerah Tujuan
    var nokkTujuan by remember { mutableStateOf("") }
    var namaKepalaKeluargaTujuan by remember { mutableStateOf("") }
    var nikKepalaKeluargaTujuan by remember { mutableStateOf("") }
    var statusKKPindahTujuan by remember { mutableStateOf("") }
    var tanggalKedatanganTujuan by remember { mutableStateOf("") }
    var alamatTujuan by remember { mutableStateOf("") }
    var nikKeluargaYangDatangTujuan by remember { mutableStateOf("") }
    var namaKeluargaYangDatangTujuan by remember { mutableStateOf("") }
    var selectedRTTujuan by remember { mutableStateOf("") }
    var selectedRWTujuan by remember { mutableStateOf("") }
    var expandedRTTujuan by remember { mutableStateOf(false) }
    var expandedRWTujuan by remember { mutableStateOf(false) }
    var statusKKPindahTujuanExpanded by remember { mutableStateOf(false) }

    // File pickers state
    var namaFileSuratPengantarRT by remember { mutableStateOf("") }
    var namaFilePersyaratan by remember { mutableStateOf("") }
    var uriFileSuratPengantarRT by remember { mutableStateOf<Uri?>(null) }
    var uriFilePersyaratan by remember { mutableStateOf<Uri?>(null) }

    // Dropdown options
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }
    val alasanPindahOptions = listOf(
        "Pekerjaan", "Pendidikan", "Keamanan", "Kesehatan", "Perumahan", "Keluarga", "Lainnya(sebutkan)"
    )
    val klasifikasiPindahOptions = listOf(
        "Dalam Satu Desa/Kelurahan", "Antar Desa/Kelurahan", "Antar Kecamatan",
        "Antar Kabupaten/Kota", "Antar Provinsi"
    )
    val jenisKepindahanOptions = listOf(
        "Kep. Keluarga", "Kep. Keluarga dan Seluruh Anggota Keluarga",
        "Kep. Keluarga dan Sebagian Anggota Keluarga", "Anggota Keluarga"
    )
    val statusNoKKTidakPindahOptions = listOf(
        "Numpang KK", "Membuat KK Baru", "Tidak Ada Keluarga Yang Ditinggal", "Nomor KK Tetap"
    )
    val statusNoKKPindahOptions = listOf(
        "Numpang KK", "Membuat KK Baru", "Nama Kep. Keluarga dan Nomor KK Tetap"
    )
    val statusKKPindahTujuanOptions = listOf(
        "Numpang KK", "Membuat KK Baru", "Nama Kep. Keluarga dan Nomor KK Tetap"
    )

    // Form validation
    val isKKValid = kkAsal.length == 16 && kkAsal.all { it.isDigit() }
    val isNikKepalaValid = nikKepalaKeluargaTujuan.length == 16 && nikKepalaKeluargaTujuan.all { it.isDigit() }
    val isNoKKValid = nokkTujuan.length == 16 && nokkTujuan.all { it.isDigit() }
    val isNIKKelDatangPindahValid = nikKeluargaYangDatangPindah.length == 16 && nikKeluargaYangDatangPindah.all { it.isDigit() }
    val isNIKKelDatangTujuanValid = nikKeluargaYangDatangTujuan.length == 16 && nikKeluargaYangDatangTujuan.all { it.isDigit() }
    val isFormValid = isKKValid &&
            isNikKepalaValid &&
            isNoKKValid &&
            isNIKKelDatangPindahValid &&
            isNIKKelDatangTujuanValid &&
            namaKepalaKeluargaAsal.isNotBlank() &&
            alamatAsal.isNotBlank() &&
            selectedRTAsal.isNotBlank() &&
            selectedRWAsal.isNotBlank() &&
            alasanPindah.isNotBlank() &&
            alamatPindah.isNotBlank() &&
            klasifikasiPindah.isNotBlank() &&
            jenisKepindahan.isNotBlank() &&
            statusNoKKTidakPindah.isNotBlank() &&
            statusNoKKPindah.isNotBlank() &&
            rencanaTanggalPindah.isNotBlank() &&
            nikKeluargaYangDatangPindah.isNotBlank() &&
            namaKeluargaYangDatangPindah.isNotBlank() &&
            selectedRTPindah.isNotBlank() &&
            selectedRWPindah.isNotBlank() &&
            namaKepalaKeluargaTujuan.isNotBlank() &&
            statusKKPindahTujuan.isNotBlank() &&
            tanggalKedatanganTujuan.isNotBlank() &&
            alamatTujuan.isNotBlank() &&
            nikKeluargaYangDatangTujuan.isNotBlank() &&
            namaKeluargaYangDatangTujuan.isNotBlank() &&
            selectedRTTujuan.isNotBlank() &&
            selectedRWTujuan.isNotBlank() &&
            namaFileSuratPengantarRT.isNotBlank() &&
            namaFilePersyaratan.isNotBlank()

    // Date pickers
    val calendar = Calendar.getInstance()
    val datePickerDialogPindah = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
            rencanaTanggalPindah = formatter.format(selectedDate.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    // Atur tanggal maksimal: hari ini
    datePickerDialogPindah.datePicker.maxDate = Calendar.getInstance().timeInMillis

    val datePickerDialogTujuan = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
            tanggalKedatanganTujuan = formatter.format(selectedDate.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    // Atur tanggal maksimal: hari ini
    datePickerDialogTujuan.datePicker.maxDate = Calendar.getInstance().timeInMillis

    // File pickers
    val pemilihFileSuratRT = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            uriFileSuratPengantarRT = it
            namaFileSuratPengantarRT = it.lastPathSegment?.substringAfterLast("/") ?: "surat_pengantar_rt.pdf"
        }
    }

    val pemilihFilePersyaratan = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            uriFilePersyaratan = it
            namaFilePersyaratan = it.lastPathSegment?.substringAfterLast("/") ?: "berkas_persyaratan.pdf"
        }
    }

    // Reset form function
    val resetForm = {
        kkAsal = ""
        namaKepalaKeluargaAsal = ""
        alamatAsal = ""
        noTeleponAsal = ""
        kodePosAsal = ""
        selectedRTAsal = ""
        selectedRWAsal = ""
        alasanPindah = ""
        alamatPindah = ""
        kodePosPindah = ""
        noTeleponPindah = ""
        klasifikasiPindah = ""
        jenisKepindahan = ""
        statusNoKKTidakPindah = ""
        statusNoKKPindah = ""
        rencanaTanggalPindah = ""
        nikKeluargaYangDatangPindah = ""
        namaKeluargaYangDatangPindah = ""
        selectedRTPindah = ""
        selectedRWPindah = ""
        nokkTujuan = ""
        namaKepalaKeluargaTujuan = ""
        nikKepalaKeluargaTujuan = ""
        statusKKPindahTujuan = ""
        tanggalKedatanganTujuan = ""
        alamatTujuan = ""
        nikKeluargaYangDatangTujuan = ""
        namaKeluargaYangDatangTujuan = ""
        selectedRTTujuan = ""
        selectedRWTujuan = ""
        namaFileSuratPengantarRT = ""
        namaFilePersyaratan = ""
        uriFileSuratPengantarRT = null
        uriFilePersyaratan = null
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
                                text = "Form Pengajuan",
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
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
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
            HeaderSection(primaryColor = primaryColor)
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Informasi Wilayah Daerah Asal
                SectionCard(
                    title = "Informasi Wilayah Daerah Asal",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DropdownField(
                            value = selectedRWAsal,
                            label = "RW",
                            expanded = expandedRWAsal,
                            onExpandedChange = { expandedRWAsal = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWAsal = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRTAsal,
                            label = "RT",
                            expanded = expandedRTAsal,
                            onExpandedChange = { expandedRTAsal = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTAsal = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Data Keluarga Daerah Asal
                SectionCard(
                    title = "Data Keluarga Daerah Asal",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = kkAsal,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    kkAsal = it
                                }
                            },
                            label = { Text("No KK (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = kkAsal.isNotEmpty() && !isKKValid,
                            singleLine = true
                        )
                        if (kkAsal.isNotEmpty() && !isKKValid) {
                            Text(
                                text = "No KK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = namaKepalaKeluargaAsal,
                            onValueChange = {
                                namaKepalaKeluargaAsal = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama Kepala Keluarga") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        OutlinedTextField(
                            value = alamatAsal,
                            onValueChange = { alamatAsal = it },
                            label = { Text("Alamat Lengkap Daerah Asal") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 5
                        )
                        OutlinedTextField(
                            value = kodePosAsal,
                            onValueChange = {
                                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                                    kodePosAsal = it
                                }
                            },
                            label = { Text("Kode Pos") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = noTeleponAsal,
                            onValueChange = {
                                if (it.length <= 13 && it.all { char -> char.isDigit() }) {
                                    noTeleponAsal = it
                                }
                            },
                            label = { Text("No Telepon") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true
                        )
                    }
                }

                // Informasi Wilayah Kepindahan
                SectionCard(
                    title = "Informasi Wilayah Kepindahan",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DropdownField(
                            value = selectedRWPindah,
                            label = "RW",
                            expanded = expandedRWPindah,
                            onExpandedChange = { expandedRWPindah = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWPindah = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRTPindah,
                            label = "RT",
                            expanded = expandedRTPindah,
                            onExpandedChange = { expandedRTPindah = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTPindah = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Data Kepindahan
                SectionCard(
                    title = "Data Kepindahan",
                    icon = Icons.Default.Edit,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        DropdownField(
                            value = alasanPindah,
                            label = "Alasan Pindah",
                            expanded = alasanPindahExpanded,
                            onExpandedChange = { alasanPindahExpanded = it },
                            options = alasanPindahOptions,
                            onOptionSelected = { alasanPindah = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = alamatPindah,
                            onValueChange = { alamatPindah = it },
                            label = { Text("Alamat Tujuan Pindah") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 5
                        )
                        OutlinedTextField(
                            value = kodePosPindah,
                            onValueChange = {
                                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                                    kodePosPindah = it
                                }
                            },
                            label = { Text("Kode Pos") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = noTeleponPindah,
                            onValueChange = {
                                if (it.length <= 13 && it.all { char -> char.isDigit() }) {
                                    noTeleponPindah = it
                                }
                            },
                            label = { Text("No Telepon") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true
                        )
                        DropdownField(
                            value = klasifikasiPindah,
                            label = "Klasifikasi Pindah",
                            expanded = klasifikasiPindahExpanded,
                            onExpandedChange = { klasifikasiPindahExpanded = it },
                            options = klasifikasiPindahOptions,
                            onOptionSelected = { klasifikasiPindah = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownField(
                            value = jenisKepindahan,
                            label = "Jenis Kepindahan",
                            expanded = jenisKepindahanExpanded,
                            onExpandedChange = { jenisKepindahanExpanded = it },
                            options = jenisKepindahanOptions,
                            onOptionSelected = { jenisKepindahan = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownField(
                            value = statusNoKKTidakPindah,
                            label = "Status Nomor KK Tidak Pindah",
                            expanded = statusNoKKTidakPindahExpanded,
                            onExpandedChange = { statusNoKKTidakPindahExpanded = it },
                            options = statusNoKKTidakPindahOptions,
                            onOptionSelected = { statusNoKKTidakPindah = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownField(
                            value = statusNoKKPindah,
                            label = "Status Nomor KK Pindah",
                            expanded = statusNoKKPindahExpanded,
                            onExpandedChange = { statusNoKKPindahExpanded = it },
                            options = statusNoKKPindahOptions,
                            onOptionSelected = { statusNoKKPindah = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = rencanaTanggalPindah,
                            onValueChange = {},
                            label = { Text("Rencana Tanggal Pindah") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialogPindah.show() }) {
                                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = nikKeluargaYangDatangPindah,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikKeluargaYangDatangPindah = it
                                }
                            },
                            label = { Text("NIK Keluarga Yang Pindah") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikKeluargaYangDatangPindah.isNotEmpty() && !isNIKKelDatangPindahValid,
                            singleLine = true
                        )
                        if (nikKeluargaYangDatangPindah.isNotEmpty() && !isNIKKelDatangPindahValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = namaKeluargaYangDatangPindah,
                            onValueChange = {
                                namaKeluargaYangDatangPindah = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama Keluarga Yang Pindah") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }
                }

                // Informasi Wilayah Daerah Tujuan
                SectionCard(
                    title = "Informasi Wilayah Daerah Tujuan",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DropdownField(
                            value = selectedRWTujuan,
                            label = "RW",
                            expanded = expandedRWTujuan,
                            onExpandedChange = { expandedRWTujuan = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWTujuan = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRTTujuan,
                            label = "RT",
                            expanded = expandedRTTujuan,
                            onExpandedChange = { expandedRTTujuan = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTTujuan = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Data Daerah Tujuan
                SectionCard(
                    title = "Data Daerah Tujuan",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = nokkTujuan,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nokkTujuan = it
                                }
                            },
                            label = { Text("Nomor KK Tujuan (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nokkTujuan.isNotEmpty() && !isNoKKValid,
                            singleLine = true
                        )
                        if (nokkTujuan.isNotEmpty() && !isNoKKValid) {
                            Text(
                                text = "No KK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = namaKepalaKeluargaTujuan,
                            onValueChange = {
                                namaKepalaKeluargaTujuan = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama Kepala Keluarga") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        OutlinedTextField(
                            value = nikKepalaKeluargaTujuan,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikKepalaKeluargaTujuan = it
                                }
                            },
                            label = { Text("NIK Kepala Keluarga Tujuan (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikKepalaKeluargaTujuan.isNotEmpty() && !isNikKepalaValid,
                            singleLine = true
                        )
                        if (nikKepalaKeluargaTujuan.isNotEmpty() && !isNikKepalaValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        DropdownField(
                            value = statusKKPindahTujuan,
                            label = "Status KK Pindah Tujuan",
                            expanded = statusKKPindahTujuanExpanded,
                            onExpandedChange = { statusKKPindahTujuanExpanded = it },
                            options = statusKKPindahTujuanOptions,
                            onOptionSelected = { statusKKPindahTujuan = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tanggalKedatanganTujuan,
                            onValueChange = {},
                            label = { Text("Tanggal Kedatangan Tujuan") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialogTujuan.show() }) {
                                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = alamatTujuan,
                            onValueChange = { alamatTujuan = it },
                            label = { Text("Alamat Tujuan") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 5
                        )
                        OutlinedTextField(
                            value = nikKeluargaYangDatangTujuan,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikKeluargaYangDatangTujuan = it
                                }
                            },
                            label = { Text("NIK Keluarga Yang Datang") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikKeluargaYangDatangTujuan.isNotEmpty() && !isNIKKelDatangTujuanValid,
                            singleLine = true
                        )
                        if (nikKeluargaYangDatangTujuan.isNotEmpty() && !isNIKKelDatangTujuanValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = namaKeluargaYangDatangTujuan,
                            onValueChange = {
                                namaKeluargaYangDatangTujuan = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama Keluarga Yang Datang") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }
                }

                // Berkas Lampiran
                SectionCard(
                    title = "Berkas Lampiran",
                    icon = Icons.Default.Email,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        FileUploadSection(
                            title = "Surat Pengantar RT",
                            fileName = namaFileSuratPengantarRT,
                            onPickFileClick = { pemilihFileSuratRT.launch("application/pdf") },
                            primaryColor = primaryColor,
                            infoText = "Upload file PDF maksimal 2MB"
                        )
                        FileUploadSection(
                            title = "Berkas Persyaratan",
                            fileName = namaFilePersyaratan,
                            onPickFileClick = { pemilihFilePersyaratan.launch("application/pdf") },
                            primaryColor = primaryColor,
                            infoText = "Upload file PDF maksimal 2MB"
                        )

                        Spacer(modifier = Modifier.height(8.dp)) // beri jarak agar tidak menumpuk

                        Text(
                            text = "Untuk berkas persyaratan dijadikan 1 PDF saja!",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                ActionButtonsSection(
                    onCancel = {
                        resetForm()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Form telah direset")
                        }
                    },
                    onConfirm = {
                        if (isFormValid) {
                            coroutineScope.launch {
                                try {
                                    val urlSuratPengantarRT = uriFileSuratPengantarRT?.let {
                                        uploadToSupabaseSDK(context, "surat_pengantar_rt", it)
                                    }
                                    val urlPersyaratan = uriFilePersyaratan?.let {
                                        uploadToSupabaseSDK(context, "persyaratan", it)
                                    }

                                    if (urlSuratPengantarRT != null && urlPersyaratan != null && urlSuratPengantarRT.isNotEmpty() && urlPersyaratan.isNotEmpty()) {
                                        val data = PindahDatangData(
                                            kkAsal = kkAsal,
                                            namaKepalaKeluargaAsal = namaKepalaKeluargaAsal,
                                            alamatAsal = alamatAsal,
                                            noTeleponAsal = noTeleponAsal,
                                            kodePosAsal = kodePosAsal,
                                            rtAsal = selectedRTAsal,
                                            rwAsal = selectedRWAsal,
                                            alasanPindah = alasanPindah,
                                            alamatPindah = alamatPindah,
                                            kodePosPindah = kodePosPindah,
                                            noTeleponPindah = noTeleponPindah,
                                            klasifikasiPindah = klasifikasiPindah,
                                            jenisKepindahan = jenisKepindahan,
                                            statusNoKkTidakPindah = statusNoKKTidakPindah,
                                            statusNoKkPindah = statusNoKKPindah,
                                            rencanaTanggalPindah = rencanaTanggalPindah,
                                            nikKeluargaYangDatangPindah = nikKeluargaYangDatangPindah,
                                            namaKeluargaYangDatangPindah = namaKeluargaYangDatangPindah,
                                            rtPindah = selectedRTPindah,
                                            rwPindah = selectedRWPindah,
                                            noKkTujuan = nokkTujuan,
                                            namaKepalaKeluargaTujuan = namaKepalaKeluargaTujuan,
                                            nikKepalaKeluargaTujuan = nikKepalaKeluargaTujuan,
                                            statusKkPindahTujuan = statusKKPindahTujuan,
                                            tanggalKedatanganTujuan = tanggalKedatanganTujuan,
                                            alamatTujuan = alamatTujuan,
                                            nikKeluargaYangDatangTujuan = nikKeluargaYangDatangTujuan,
                                            namaKeluargaYangDatangTujuan = namaKeluargaYangDatangTujuan,
                                            rtTujuan = selectedRTTujuan,
                                            rwTujuan = selectedRWTujuan,
                                            namaFileSuratPengantarRT = urlSuratPengantarRT,
                                            namaFilePersyaratan = urlPersyaratan,
                                            id_user = SessionManager.currentUser?.id ?: -1
                                        )
                                        viewModel.simpanPindahDatangData(data)
                                        snackbarHostState.showSnackbar("Data Tersimpan")
                                        navController.navigate("list_data_pindah")
                                    } else {
                                        snackbarHostState.showSnackbar("Upload file gagal, coba lagi")
                                    }
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Terjadi kesalahan: ${e.message}")
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Harap lengkapi semua field dengan benar")
                            }
                        }
                    },
                    primaryColor = primaryColor,
                    isConfirmEnabled = isFormValid
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun HeaderSection(primaryColor: Color) {
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
                        .background(primaryColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Form Pengajuan",
                        tint = primaryColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Form Pengajuan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF2C3E50),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Lengkapi data dengan benar",
                        fontSize = 14.sp,
                        color = Color(0xFF5D6D7E),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownField(
    value: String,
    label: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            singleLine = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun FileUploadSection(
    title: String,
    fileName: String,
    onPickFileClick: () -> Unit,
    primaryColor: Color,
    infoText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (infoText.isNotEmpty()) {
            Text(
                text = infoText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2C3E50)
        )
        Button(
            onClick = onPickFileClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                Icons.Default.AddCircle,
                contentDescription = "Pilih file $title",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Pilih File PDF")
        }
        if (fileName.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "File terpilih",
                    tint = Color(0xFF27AE60),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = fileName,
                    fontSize = 12.sp,
                    color = Color(0xFF27AE60),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ActionButtonsSection(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    primaryColor: Color,
    isConfirmEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = primaryColor,
                disabledContentColor = primaryColor.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = "Ulangi",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
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
            enabled = isConfirmEnabled
        ) {
            Text(
                text = "Konfirmasi",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataDaerahAsalPreview() {
    DataDaerahAsal(rememberNavController())
}