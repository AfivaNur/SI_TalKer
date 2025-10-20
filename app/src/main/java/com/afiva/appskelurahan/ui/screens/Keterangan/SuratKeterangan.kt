package com.afiva.appskelurahan.ui.screens.Keterangan // Adjust package as needed

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
import com.afiva.appskelurahan.model.KeteranganData // Adjust import path as needed
import com.afiva.appskelurahan.ui.screens.Domisili.SectionCard // Adjust import path as needed
import com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.FileUploadSection
import com.afiva.appskelurahan.uploadToSupabaseSDK
import com.afiva.appskelurahan.viewmodel.KeteranganViewModel // Adjust import path as needed
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuratKeterangan(navController: NavController) { BackHandler(enabled = true) {}
    val context = LocalContext.current
    val viewModel: KeteranganViewModel = viewModel() // Adjust ViewModel as needed
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Color theme
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    // Pejabat Penandatangan
    var namaPejabatKeterangan by remember { mutableStateOf("LENI MARLINA SY,SE") }
    var nipKeterangan by remember { mutableStateOf("19800626 201001 2011") }
    var jabatanKeterangan by remember { mutableStateOf("Lurah") }

    // Data Pribadi Tertuju
    var nikKeterangan by remember { mutableStateOf(SessionManager.currentUser?.nik ?: "") }
    var namaKeterangan by remember { mutableStateOf(SessionManager.currentUser?.username ?: "") }
    var tempatLahirKeterangan by remember { mutableStateOf(SessionManager.currentUser?.tempatLahir ?: "") }
    var tanggalLahirKeterangan by remember { mutableStateOf(SessionManager.currentUser?.tanggalLahir ?: "")}
    var jenisKelaminKeterangan by remember { mutableStateOf(SessionManager.currentUser?.jenisKelamin ?: "") }
    var wargaNegaraKeterangan by remember { mutableStateOf(SessionManager.currentUser?.kewarganegaraan ?: "") }
    var agamaKeterangan by remember { mutableStateOf(SessionManager.currentUser?.agama ?: "") }
    var pekerjaanKeterangan by remember { mutableStateOf(SessionManager.currentUser?.pekerjaan ?: "") }
    var alamatKeterangan by remember { mutableStateOf(SessionManager.currentUser?.alamat ?: "") }
    var tujuanSuratKeterangan by remember { mutableStateOf("") }
    var selectedRTKeterangan by remember { mutableStateOf(SessionManager.currentUser?.rt ?: "") }
    var selectedRWKeterangan by remember { mutableStateOf(SessionManager.currentUser?.rw ?: "") }

    // File upload states
    var namaFileSuratPengantarRT by remember { mutableStateOf("") }
    var namaFilePersyaratan by remember { mutableStateOf("") }
    var uriFileSuratPengantarRT by remember { mutableStateOf<Uri?>(null) }
    var uriFilePersyaratan by remember { mutableStateOf<Uri?>(null) }

    // Expanded states
    var expandedRWKeterangan by remember { mutableStateOf(false) }
    var expandedRTKeterangan by remember { mutableStateOf(false) }
    var expandedTempatLahirKeterangan by remember { mutableStateOf(false) }
    var expandedAgamaKeterangan by remember { mutableStateOf(false) }
    var expandedPekerjaanKeterangan by remember { mutableStateOf(false) }

    // Options
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }
    val agamaOptions = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
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
    val isNikValid = nikKeterangan.length == 16 && nikKeterangan.all { it.isDigit() }
    val isFormValid = isNikValid &&
            namaPejabatKeterangan.isNotBlank() &&
            nipKeterangan.isNotBlank() &&
            jabatanKeterangan.isNotBlank() &&
            nikKeterangan!!.isNotBlank() &&
            namaKeterangan!!.isNotBlank() &&
            tempatLahirKeterangan!!.isNotBlank() &&
            tanggalLahirKeterangan!!.isNotBlank() &&
            jenisKelaminKeterangan!!.isNotBlank() &&
            wargaNegaraKeterangan!!.isNotBlank() &&
            agamaKeterangan!!.isNotBlank() &&
            pekerjaanKeterangan!!.isNotBlank() &&
            alamatKeterangan!!.isNotBlank() &&
            tujuanSuratKeterangan.isNotBlank() &&
            selectedRTKeterangan!!.isNotBlank() &&
            selectedRWKeterangan!!.isNotBlank() &&
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
            tanggalLahirKeterangan = formatter.format(selectedDate.time)
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
        namaPejabatKeterangan = "LENI MARLINA SY,SE"
        nipKeterangan = "19800626 201001 2011"
        jabatanKeterangan = "Lurah"
        nikKeterangan = SessionManager.currentUser?.nik.toString()
        namaKeterangan = SessionManager.currentUser?.username.toString()
        tempatLahirKeterangan = SessionManager.currentUser?.tempatLahir.toString()
        tanggalLahirKeterangan = SessionManager.currentUser?.tanggalLahir.toString()
        jenisKelaminKeterangan = SessionManager.currentUser?.jenisKelamin.toString()
        wargaNegaraKeterangan = SessionManager.currentUser?.kewarganegaraan.toString()
        agamaKeterangan = SessionManager.currentUser?.agama.toString()
        pekerjaanKeterangan = SessionManager.currentUser?.pekerjaan.toString()
        alamatKeterangan = SessionManager.currentUser?.alamat.toString()
        tujuanSuratKeterangan = ""
        selectedRTKeterangan = SessionManager.currentUser?.rt.toString()
        selectedRWKeterangan = SessionManager.currentUser?.rw.toString()
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
                                text = "KETERANGAN KHUSUS",
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
                            value = selectedRWKeterangan,
                            label = "RW",
                            expanded = expandedRWKeterangan,
                            onExpandedChange = { expandedRWKeterangan = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWKeterangan = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRTKeterangan,
                            label = "RT",
                            expanded = expandedRTKeterangan,
                            onExpandedChange = { expandedRTKeterangan = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTKeterangan = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Pejabat Penandatangan
                SectionCard(
                    title = "Pejabat Penandatangan",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = namaPejabatKeterangan,
                            onValueChange = {},
                            label = { Text("Nama Pejabat") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = nipKeterangan,
                            onValueChange = {},
                            label = { Text("NIP") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = jabatanKeterangan,
                            onValueChange = {},
                            label = { Text("Jabatan") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }

                // Data Pribadi Tertuju
                SectionCard(
                    title = "Data Pribadi Tertuju",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = nikKeterangan,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikKeterangan = it
                                }
                            },
                            label = { Text("NIK (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikKeterangan.isNotEmpty() && !isNikValid,
                            singleLine = true
                        )
                        if (nikKeterangan.isNotEmpty() && !isNikValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = namaKeterangan,
                            onValueChange = {
                                namaKeterangan = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        DropdownField(
                            value = tempatLahirKeterangan,
                            label = "Tempat Lahir",
                            expanded = expandedTempatLahirKeterangan,
                            onExpandedChange = { expandedTempatLahirKeterangan = it },
                            options = kotaOptions,
                            onOptionSelected = { tempatLahirKeterangan = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tanggalLahirKeterangan,
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
                            selectedGender = jenisKelaminKeterangan,
                            onGenderSelected = { jenisKelaminKeterangan = it }
                        )
                        DropdownField(
                            value = agamaKeterangan,
                            label = "Agama",
                            expanded = expandedAgamaKeterangan,
                            onExpandedChange = { expandedAgamaKeterangan = it },
                            options = agamaOptions,
                            onOptionSelected = { agamaKeterangan = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        CitizenshipSelection(
                            selectedCitizenship = wargaNegaraKeterangan,
                            onCitizenshipSelected = { wargaNegaraKeterangan = it }
                        )
                        DropdownField(
                            value = pekerjaanKeterangan,
                            label = "Pekerjaan",
                            expanded = expandedPekerjaanKeterangan,
                            onExpandedChange = { expandedPekerjaanKeterangan = it },
                            options = pekerjaanOptions,
                            onOptionSelected = { pekerjaanKeterangan = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Alamat & Tujuan
                SectionCard(
                    title = "Alamat & Tujuan",
                    icon = Icons.Default.Edit,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = alamatKeterangan,
                            onValueChange = { alamatKeterangan = it },
                            label = { Text("Alamat Lengkap") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 4
                        )
                        OutlinedTextField(
                            value = tujuanSuratKeterangan,
                            onValueChange = { tujuanSuratKeterangan = it },
                            label = { Text("Tujuan Pengajuan") },
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
                                        val data = KeteranganData(
                                            namaPejabat = namaPejabatKeterangan,
                                            jabatan = jabatanKeterangan,
                                            nik = nikKeterangan,
                                            nip = nipKeterangan,
                                            nama = namaKeterangan,
                                            tempatLahir = tempatLahirKeterangan,
                                            tanggalLahir = tanggalLahirKeterangan,
                                            jenisKelamin = jenisKelaminKeterangan,
                                            wargaNegara = wargaNegaraKeterangan,
                                            agama = agamaKeterangan,
                                            pekerjaan = pekerjaanKeterangan,
                                            alamat = alamatKeterangan,
                                            tujuanSurat = tujuanSuratKeterangan,
                                            rt = selectedRTKeterangan,
                                            rw = selectedRWKeterangan,
                                            namaFileSuratPengantarRT = urlSuratPengantarRT,
                                            namaFilePersyaratan = urlPersyaratan,
                                            id_user = SessionManager.currentUser?.id ?: -1
                                        )
                                        viewModel.simpanDataKeterangan(data)
                                        snackbarHostState.showSnackbar("Data Tersimpan")
                                        navController.navigate("display_surat_keterangan")
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
                        text = "Form Surat Keterangan",
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
fun SuratKeteranganPreview() {
    SuratKeterangan(rememberNavController())
}