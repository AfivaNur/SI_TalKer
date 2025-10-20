package com.afiva.appskelurahan.ui.screens.Domisili // Adjust package as needed

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
import com.afiva.appskelurahan.model.DomisiliData // Adjust import path as needed
import com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.FileUploadSection
import com.afiva.appskelurahan.uploadToSupabaseSDK
import com.afiva.appskelurahan.viewmodel.DomisiliViewModel // Adjust import path as needed
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuratKeteranganDomisili(navController: NavController) { BackHandler(enabled = true) {}
    val context = LocalContext.current
    val viewModel: DomisiliViewModel = viewModel() // Adjust ViewModel as needed
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Color theme
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    // Data Pribadi
    var nikDomisili by remember { mutableStateOf(SessionManager.currentUser?.nik ?: "") }
    var namaDomisili by remember { mutableStateOf(SessionManager.currentUser?.username ?: "") }
    var tempatLahirDomisili by remember { mutableStateOf(SessionManager.currentUser?.tempatLahir ?: "") }
    var tanggalLahirDomisili by remember { mutableStateOf(SessionManager.currentUser?.tanggalLahir ?: "") }
    var jenisKelaminDomisili by remember { mutableStateOf(SessionManager.currentUser?.jenisKelamin ?: "") }
    var agamaDomisili by remember { mutableStateOf(SessionManager.currentUser?.agama ?: "") }
    var statusPerkawinanDomisili by remember { mutableStateOf(SessionManager.currentUser?.statusPerkawinan ?: "") }
    var pekerjaanDomisili by remember { mutableStateOf(SessionManager.currentUser?.pekerjaan ?: "") }
    var alamatDomisili by remember { mutableStateOf(SessionManager.currentUser?.alamat ?: "") }
    var tujuanSuratDomisili by remember { mutableStateOf("") }
    var selectedRTDomisili by remember { mutableStateOf(SessionManager.currentUser?.rt ?: "") }
    var selectedRWDomisili by remember { mutableStateOf(SessionManager.currentUser?.rw ?: "") }


    // File upload states
    var namaFileSuratPengantarRT by remember { mutableStateOf("") }
    var namaFilePersyaratan by remember { mutableStateOf("") }
    var uriFileSuratPengantarRT by remember { mutableStateOf<Uri?>(null) }
    var uriFilePersyaratan by remember { mutableStateOf<Uri?>(null) }

    // Expanded states
    var expandedRTDomisili by remember { mutableStateOf(false) }
    var expandedRWDomisili by remember { mutableStateOf(false) }
    var expandedTempatLahirDomisili by remember { mutableStateOf(false) }
    var expandedAgamaDomisili by remember { mutableStateOf(false) }
    var expandedStatusPerkawinanDomisili by remember { mutableStateOf(false) }
    var expandedPekerjaanDomisili by remember { mutableStateOf(false) }

    // Options
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }
    val agamaOptions = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
    val statusPerkawinanOptions = listOf("Belum Kawin", "Kawin", "Cerai Hidup", "Cerai Mati")
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
    val isNikValid = nikDomisili.length == 16 && nikDomisili.all { it.isDigit() }
    val isFormValid = isNikValid &&
            namaDomisili!!.isNotBlank() &&
            tempatLahirDomisili!!.isNotBlank() &&
            tanggalLahirDomisili!!.isNotBlank() &&
            jenisKelaminDomisili!!.isNotBlank() &&
            agamaDomisili!!.isNotBlank() &&
            statusPerkawinanDomisili!!.isNotBlank() &&
            pekerjaanDomisili!!.isNotBlank() &&
            alamatDomisili!!.isNotBlank() &&
            tujuanSuratDomisili.isNotBlank() &&
            selectedRTDomisili!!.isNotBlank() &&
            selectedRWDomisili!!.isNotBlank() &&
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
            tanggalLahirDomisili = formatter.format(selectedDate.time)
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
        nikDomisili = SessionManager.currentUser?.nik.toString()
        namaDomisili = SessionManager.currentUser?.username.toString()
        tempatLahirDomisili = SessionManager.currentUser?.tempatLahir.toString()
        tanggalLahirDomisili = SessionManager.currentUser?.tanggalLahir.toString()
        jenisKelaminDomisili = SessionManager.currentUser?.jenisKelamin.toString()
        agamaDomisili = SessionManager.currentUser?.agama.toString()
        statusPerkawinanDomisili = SessionManager.currentUser?.statusPerkawinan.toString()
        pekerjaanDomisili = SessionManager.currentUser?.pekerjaan.toString()
        alamatDomisili = SessionManager.currentUser?.alamat.toString()
        tujuanSuratDomisili = ""
        selectedRTDomisili = SessionManager.currentUser?.rt.toString()
        selectedRWDomisili = SessionManager.currentUser?.rw.toString()
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
                                text = "KETERANGAN DOMISILI",
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
                            value = selectedRTDomisili,
                            label = "RT",
                            expanded = expandedRTDomisili,
                            onExpandedChange = { expandedRTDomisili = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTDomisili = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRWDomisili,
                            label = "RW",
                            expanded = expandedRWDomisili,
                            onExpandedChange = { expandedRWDomisili = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWDomisili = it },
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
                            value = nikDomisili,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikDomisili = it
                                }
                            },
                            label = { Text("NIK (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikDomisili.isNotEmpty() && !isNikValid,
                            singleLine = true
                        )
                        if (nikDomisili.isNotEmpty() && !isNikValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = namaDomisili,
                            onValueChange = {
                                namaDomisili = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        DropdownField(
                            value = tempatLahirDomisili,
                            label = "Tempat Lahir",
                            expanded = expandedTempatLahirDomisili,
                            onExpandedChange = { expandedTempatLahirDomisili = it },
                            options = kotaOptions,
                            onOptionSelected = { tempatLahirDomisili = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tanggalLahirDomisili,
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
                            selectedGender = jenisKelaminDomisili,
                            onGenderSelected = { jenisKelaminDomisili = it }
                        )
                        DropdownField(
                            value = agamaDomisili,
                            label = "Agama",
                            expanded = expandedAgamaDomisili,
                            onExpandedChange = { expandedAgamaDomisili = it },
                            options = agamaOptions,
                            onOptionSelected = { agamaDomisili = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownField(
                            value = statusPerkawinanDomisili,
                            label = "Status Perkawinan",
                            expanded = expandedStatusPerkawinanDomisili,
                            onExpandedChange = { expandedStatusPerkawinanDomisili = it },
                            options = statusPerkawinanOptions,
                            onOptionSelected = { statusPerkawinanDomisili = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownField(
                            value = pekerjaanDomisili,
                            label = "Pekerjaan",
                            expanded = expandedPekerjaanDomisili,
                            onExpandedChange = { expandedPekerjaanDomisili = it },
                            options = pekerjaanOptions,
                            onOptionSelected = { pekerjaanDomisili = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Alamat & Tujuan
                SectionCard(
                    title = "Alamat & Tujuan",
                    icon = Icons.Default.Home,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = alamatDomisili,
                            onValueChange = { alamatDomisili = it },
                            label = { Text("Alamat Lengkap") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 4
                        )
                        OutlinedTextField(
                            value = tujuanSuratDomisili,
                            onValueChange = { tujuanSuratDomisili = it },
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
                                        val data = DomisiliData(
                                            rt = selectedRTDomisili,
                                            rw = selectedRWDomisili,
                                            nik = nikDomisili,
                                            nama = namaDomisili,
                                            tempatLahir = tempatLahirDomisili,
                                            tanggalLahir = tanggalLahirDomisili,
                                            jenisKelamin = jenisKelaminDomisili,
                                            agama = agamaDomisili,
                                            statusPerkawinan = statusPerkawinanDomisili,
                                            pekerjaan = pekerjaanDomisili,
                                            alamat = alamatDomisili,
                                            tujuanSurat = tujuanSuratDomisili,
                                            namaFileSuratPengantarRT = urlSuratPengantarRT,
                                            namaFilePersyaratan = urlPersyaratan,
                                            id_user = SessionManager.currentUser?.id

                                        )
                                        viewModel.simpanDataDomisili(data)
                                        snackbarHostState.showSnackbar("Data Tersimpan")
                                        navController.navigate("display_surat_keterangan_domisili")
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
                        text = "Form Surat Keterangan Domisili",
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
fun SuratKeteranganDomisiliPreview() {
    SuratKeteranganDomisili(rememberNavController())
}