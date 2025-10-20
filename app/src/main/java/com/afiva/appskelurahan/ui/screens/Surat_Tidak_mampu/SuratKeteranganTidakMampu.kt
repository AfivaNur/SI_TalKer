package com.afiva.appskelurahan.ui.screens.Surat_Tidak_mampu // Adjust package as needed

import android.app.DatePickerDialog
import android.net.Uri
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.afiva.appskelurahan.model.TidakMampuData // Adjust import path as needed
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.ui.screens.Domisili.SectionCard
import com.afiva.appskelurahan.uploadToSupabaseSDK
import com.afiva.appskelurahan.v_model.TidakMampuViewModel // Adjust import path as needed
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuratKeteranganTidakMampu(navController: NavController) { BackHandler(enabled = true) {}
    val context = LocalContext.current
    val viewModel: TidakMampuViewModel = viewModel() // Adjust ViewModel as needed
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Theme colors
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFB2DFDB)
    val surfaceColor = Color(0xFFF8F9FA)

    // State variables
    var nama by remember { mutableStateOf(SessionManager.currentUser?.username ?: "") }
    var nikTidakMampu by remember { mutableStateOf(SessionManager.currentUser?.nik ?: "") }
    var tanggalLahir by remember { mutableStateOf(SessionManager.currentUser?.tanggalLahir ?: "") }
    var tempatLahir by remember { mutableStateOf(SessionManager.currentUser?.tempatLahir ?: "") }
    var jenisKelamin by remember { mutableStateOf(SessionManager.currentUser?.jenisKelamin ?: "") }
    var agama by remember { mutableStateOf(SessionManager.currentUser?.agama ?: "") }
    var kewarganegaraan by remember { mutableStateOf(SessionManager.currentUser?.kewarganegaraan ?: "") }
    var pekerjaan by remember { mutableStateOf(SessionManager.currentUser?.pekerjaan ?: "") }
    var alamat by remember { mutableStateOf(SessionManager.currentUser?.alamat ?: "") }
    var tujuanSurat by remember { mutableStateOf("") }
    var kategoriKeterangan by remember { mutableStateOf("") }
    var selectedRT by remember { mutableStateOf(SessionManager.currentUser?.rt ?: "") }
    var selectedRW by remember { mutableStateOf(SessionManager.currentUser?.rw ?: "") }

    // Dropdown states
    var expandedRT by remember { mutableStateOf(false) }
    var expandedRW by remember { mutableStateOf(false) }
    var expandedTempatLahir by remember { mutableStateOf(false) }
    var expandedAgama by remember { mutableStateOf(false) }
    var expandedKategori by remember { mutableStateOf(false) }
    var expandedPekerjaan by remember { mutableStateOf(false) }

    // File upload states
    var namaFileSuratPengantarRT by remember { mutableStateOf("") }
    var namaFilePersyaratan by remember { mutableStateOf("") }
    var uriFileSuratPengantarRT by remember { mutableStateOf<Uri?>(null) }
    var uriFilePersyaratan by remember { mutableStateOf<Uri?>(null) }

    // Data lists
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }
    val agamaOptions = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
    val kategoriOptions = listOf(
        "Keringanan Biaya Sekolah",
        "Keringanan Biaya Rumah Sakit",
        "Keringanan Biaya Listrik",
        "Pengajuan Bantuan Sosial",
        "Penyandang Disabilitas",
        "Lainnya"
    )
    val pekerjaanOptions = listOf(
        "Buruh", "Dokter", "Dosen", "Guru", "Ibu Rumah Tangga", "Nelayan", "Notaris",
        "PNS", "Pegawai Swasta", "Pedagang", "Pelajar/Mahasiswa", "Pengacara", "Penulis",
        "Perawat", "Petani", "Polri", "Seniman", "Sopir", "TNI", "Tidak Bekerja", "Wiraswasta"
    )
    val kotaOptions = listOf(
        "Ambon", "Balikpapan", "Banda Aceh", "Bandar Lampung", "Bandung", "Banjar",
        "Banjarbaru", "Banjarmasin", "Batam", "Batu", "Baubau", "Bekasi", "Bengkulu",
        "Bima", "Binjai", "Bitung", "Blitar", "Bogor", "Bontang", "Bukittinggi",
        "Cilegon", "Cimahi", "Cirebon", "Denpasar", "Depok", "Dumai", "Gorontalo",
        "Gunungsitoli", "Jakarta", "Jambi", "Jayapura", "Kediri", "Kendari",
        "Kotamobagu", "Kupang", "Langsa", "Lhokseumawe", "Lubuklinggau", "Madiun",
        "Magelang", "Makassar", "Malang", "Manado", "Mataram", "Medan", "Metro",
        "Mojokerto", "Padang", "Padang Panjang", "Padang Sidempuan", "Pagar Alam",
        "Palangka Raya", "Palembang", "Palopo", "Palu", "Pangkalpinang", "Parepare",
        "Pariaman", "Pasuruan", "Payakumbuh", "Pekalongan", "Pekanbaru",
        "Pematangsiantar", "Pontianak", "Prabumulih", "Probolinggo", "Sabang",
        "Salatiga", "Samarinda", "Sawahlunto", "Semarang", "Serang", "Sibolga",
        "Singkawang", "Solok", "Sorong", "Subulussalam", "Sukabumi", "Sungai Penuh",
        "Surabaya", "Surakarta", "Tangerang", "Tangerang Selatan", "Tanjungbalai",
        "Tanjungpinang", "Tarakan", "Tasikmalaya", "Tebing Tinggi", "Tegal",
        "Ternate", "Tidore Kepulauan", "Tomohon", "Tual", "Yogyakarta"
    )

    // Form validation
    val isNikValid = nikTidakMampu.length == 16 && nikTidakMampu.all { it.isDigit() }
    val isFormValid = isNikValid &&
            nama!!.isNotBlank() &&
            tanggalLahir!!.isNotBlank() &&
            tempatLahir!!.isNotBlank() &&
            jenisKelamin!!.isNotBlank() &&
            agama!!.isNotBlank() &&
            kewarganegaraan!!.isNotBlank() &&
            pekerjaan!!.isNotBlank() &&
            alamat!!.isNotBlank() &&
            tujuanSurat.isNotBlank() &&
            kategoriKeterangan.isNotBlank() &&
            selectedRT!!.isNotBlank() &&
            selectedRW!!.isNotBlank() &&
            namaFileSuratPengantarRT.isNotBlank() &&
            namaFilePersyaratan.isNotBlank()

    //TANGGAL
    val calendar = Calendar.getInstance()
// Atur default ke tahun 2000
    calendar.set(Calendar.YEAR, 2000)
    calendar.set(Calendar.MONTH, 0)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
            tanggalLahir = formatter.format(selectedDate.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

// Atur tanggal maksimal: hari ini
    datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis

// (Opsional) Atur tanggal minimal (misal: lahir paling tua 1800)
    val minDate = Calendar.getInstance()
    minDate.set(1800, 0, 1)
    datePickerDialog.datePicker.minDate = minDate.timeInMillis

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

    // Reset form
    val resetForm = {
        nama = SessionManager.currentUser?.username.toString()
        nikTidakMampu = SessionManager.currentUser?.nik.toString()
        tanggalLahir = SessionManager.currentUser?.tanggalLahir.toString()
        tempatLahir = SessionManager.currentUser?.tempatLahir.toString()
        jenisKelamin = SessionManager.currentUser?.jenisKelamin.toString()
        agama = SessionManager.currentUser?.agama.toString()
        kewarganegaraan = SessionManager.currentUser?.kewarganegaraan.toString()
        pekerjaan = SessionManager.currentUser?.pekerjaan.toString()
        alamat = SessionManager.currentUser?.alamat.toString()
        tujuanSurat = ""
        kategoriKeterangan = ""
        selectedRT = SessionManager.currentUser?.rt.toString()
        selectedRW = SessionManager.currentUser?.rw.toString()
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
                                text = "KETERANGAN TIDAK MAMPU",
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
                        onClick = { navController.navigate(Screen.Beranda.route) },
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
                // Informasi Wilayah
                SectionCard(
                    title = "Informasi Wilayah",
                    icon = Icons.Default.LocationOn,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DropdownField(
                            value = selectedRW,
                            label = "RW",
                            expanded = expandedRW,
                            onExpandedChange = { expandedRW = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRW = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRT,
                            label = "RT",
                            expanded = expandedRT,
                            onExpandedChange = { expandedRT = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRT = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Data Pribadi
                SectionCard(
                    title = "Data Pribadi",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = nikTidakMampu,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikTidakMampu = it
                                }
                            },
                            label = { Text("NIK (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikTidakMampu.isNotEmpty() && !isNikValid,
                            singleLine = true
                        )
                        if (nikTidakMampu.isNotEmpty() && !isNikValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = nama,
                            onValueChange = {
                                nama = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        DropdownField(
                            value = tempatLahir,
                            label = "Tempat Lahir",
                            expanded = expandedTempatLahir,
                            onExpandedChange = { expandedTempatLahir = it },
                            options = kotaOptions,
                            onOptionSelected = { tempatLahir = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tanggalLahir,
                            onValueChange = {},
                            label = { Text("Tanggal Lahir") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        GenderSelection(
                            selectedGender = jenisKelamin,
                            onGenderSelected = { jenisKelamin = it }
                        )
                        DropdownField(
                            value = agama,
                            label = "Agama",
                            expanded = expandedAgama,
                            onExpandedChange = { expandedAgama = it },
                            options = agamaOptions,
                            onOptionSelected = { agama = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        CitizenshipSelection(
                            selectedCitizenship = kewarganegaraan,
                            onCitizenshipSelected = { kewarganegaraan = it }
                        )
                        DropdownField(
                            value = pekerjaan,
                            label = "Pekerjaan",
                            expanded = expandedPekerjaan,
                            onExpandedChange = { expandedPekerjaan = it },
                            options = pekerjaanOptions,
                            onOptionSelected = { pekerjaan = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Kategori & Tujuan
                SectionCard(
                    title = "Kategori & Tujuan",
                    icon = Icons.Default.Edit,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        DropdownField(
                            value = kategoriKeterangan,
                            label = "Kategori Keterangan Tidak Mampu",
                            expanded = expandedKategori,
                            onExpandedChange = { expandedKategori = it },
                            options = kategoriOptions,
                            onOptionSelected = { kategoriKeterangan = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = alamat,
                            onValueChange = { alamat = it },
                            label = { Text("Alamat Lengkap") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 4
                        )
                        OutlinedTextField(
                            value = tujuanSurat,
                            onValueChange = { tujuanSurat = it },
                            label = { Text("Tujuan Pembuatan Keterangan") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 100.dp),
                            maxLines = 4
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

                Spacer(modifier = Modifier.height(16.dp))

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
                                    if (urlSuratPengantarRT != null && urlPersyaratan != null &&
                                        urlSuratPengantarRT.isNotEmpty() && urlPersyaratan.isNotEmpty()
                                    ) {
                                        val data = TidakMampuData(
                                            nama = nama,
                                            nik = nikTidakMampu,
                                            tanggalLahir = tanggalLahir,
                                            tempatLahir = tempatLahir,
                                            jenisKelamin = jenisKelamin,
                                            agama = agama,
                                            kewarganegaraan = kewarganegaraan,
                                            pekerjaan = pekerjaan,
                                            alamat = alamat,
                                            tujuanSurat = tujuanSurat,
                                            rt = selectedRT,
                                            rw = selectedRW,
                                            kategoriKeterangan = kategoriKeterangan,
                                            namaFileSuratPengantarRT = urlSuratPengantarRT,
                                            namaFilePersyaratan = urlPersyaratan,
                                            id_user = SessionManager.currentUser?.id
                                        )
                                        viewModel.simpanDataTidakMampu(data)
                                        snackbarHostState.showSnackbar("Data Tersimpan")
                                        navController.navigate("review_surat_tidak_mampu")
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
fun SectionCard(
    title: String,
    icon: ImageVector,
    primaryColor: Color,
    lightColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                content()
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
                        text = "Form Surat Keterangan Tidak Mampu",
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
private fun GenderSelection(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Jenis Kelamin",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF2C3E50)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            listOf("Laki-laki", "Perempuan").forEach { gender ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RadioButton(
                        selected = selectedGender == gender,
                        onClick = { onGenderSelected(gender) }
                    )
                    Text(
                        text = gender,
                        fontSize = 14.sp,
                        color = Color(0xFF2C3E50)
                    )
                }
            }
        }
    }
}

@Composable
private fun CitizenshipSelection(
    selectedCitizenship: String,
    onCitizenshipSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Kewarganegaraan",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF2C3E50)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            listOf("WNI", "WNA").forEach { citizenship ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RadioButton(
                        selected = selectedCitizenship == citizenship,
                        onClick = { onCitizenshipSelected(citizenship) }
                    )
                    Text(
                        text = citizenship,
                        fontSize = 14.sp,
                        color = Color(0xFF2C3E50)
                    )
                }
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
fun SuratKeteranganTidakMampuPreview() {
    SuratKeteranganTidakMampu(rememberNavController())
}