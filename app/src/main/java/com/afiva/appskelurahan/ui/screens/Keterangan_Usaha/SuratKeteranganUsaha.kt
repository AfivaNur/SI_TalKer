package com.afiva.appskelurahan.ui.screens.Keterangan_Usaha

import android.app.DatePickerDialog
import android.content.Context
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
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.UsahaData
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.ui.screens.Domisili.SectionCard
import com.afiva.appskelurahan.uploadToSupabaseSDK
import com.afiva.appskelurahan.v_model.UsahaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuratKeteranganUsaha(navController: NavController) { BackHandler(enabled = true) {}
    val context = LocalContext.current
    val supabase = SupabaseProvider.client
    val viewModel: UsahaViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // State variables
    var namaUsaha by remember { mutableStateOf(SessionManager.currentUser?.username ?: "") }
    var nikUsaha by remember { mutableStateOf(SessionManager.currentUser?.nik ?: "") }
    var tanggalLahirUsaha by remember { mutableStateOf(SessionManager.currentUser?.tanggalLahir ?: "") }
    var tempatLahirUsaha by remember { mutableStateOf(SessionManager.currentUser?.tempatLahir ?: "") }
    var jenisKelaminUsaha by remember { mutableStateOf(SessionManager.currentUser?.jenisKelamin ?: "")}
    var alamat by remember { mutableStateOf(SessionManager.currentUser?.alamat ?: "") }
    var jenisUsaha by remember { mutableStateOf("") }
    var ukuranTempatUsaha by remember { mutableStateOf("") }
    var lokasiUsaha by remember { mutableStateOf("") }
    var lamaUsaha by remember { mutableStateOf("") }
    var tujuanSuratUsaha by remember { mutableStateOf("") }
    var selectedRTUsaha by remember { mutableStateOf(SessionManager.currentUser?.rt ?: "") }
    var selectedRWUsaha by remember { mutableStateOf(SessionManager.currentUser?.rw ?: "") }
    var namaFileSuratPengantarRT by remember { mutableStateOf("") }
    var namaFilePersyaratan by remember { mutableStateOf("") }
    var uriFileSuratPengantarRT by remember { mutableStateOf<Uri?>(null) }
    var uriFilePersyaratan by remember { mutableStateOf<Uri?>(null) }

    // Dropdown states
    var jenisUsahaExpanded by remember { mutableStateOf(false) }
    var tempatLahirUsahaExpanded by remember { mutableStateOf(false) }
    var expandedRWUsaha by remember { mutableStateOf(false) }
    var expandedRTUsaha by remember { mutableStateOf(false) }

    // Constants
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)
    val jenisUsahaList = listOf("Toko", "Warung", "Kios", "Jasa", "Online", "Lainnya")
    val daftarKotaIndonesia = listOf(
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
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }

    // Form validation
    val isNikValid = nikUsaha.length == 16 && nikUsaha.all { it.isDigit() }
    val isFormValid = isNikValid &&
            namaUsaha!!.isNotBlank() &&
            tanggalLahirUsaha!!.isNotBlank() &&
            tempatLahirUsaha!!.isNotBlank() &&
            jenisKelaminUsaha!!.isNotBlank() &&
            alamat!!.isNotBlank() &&
            jenisUsaha!!.isNotBlank() &&
            ukuranTempatUsaha.isNotBlank() &&
            lokasiUsaha.isNotBlank() &&
            lamaUsaha.isNotBlank() &&
            tujuanSuratUsaha.isNotBlank() &&
            selectedRTUsaha!!.isNotBlank() &&
            selectedRWUsaha!!.isNotBlank() &&
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
            tanggalLahirUsaha = formatter.format(selectedDate.time)
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

    // Reset form function
    val resetForm = {
        namaUsaha = SessionManager.currentUser?.username.toString()
        nikUsaha = SessionManager.currentUser?.nik.toString()
        tanggalLahirUsaha = SessionManager.currentUser?.tanggalLahir.toString()
        tempatLahirUsaha = SessionManager.currentUser?.tempatLahir.toString()
        jenisKelaminUsaha = SessionManager.currentUser?.jenisKelamin.toString()
        alamat = SessionManager.currentUser?.alamat.toString()
        jenisUsaha = ""
        ukuranTempatUsaha = ""
        lokasiUsaha = ""
        lamaUsaha = ""
        tujuanSuratUsaha = ""
        selectedRTUsaha = SessionManager.currentUser?.rt.toString()
        selectedRWUsaha = SessionManager.currentUser?.rw.toString()
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
                                text = "KETERANGAN USAHA",
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
                            value = selectedRWUsaha,
                            label = "RW",
                            expanded = expandedRWUsaha,
                            onExpandedChange = { expandedRWUsaha = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWUsaha = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRTUsaha,
                            label = "RT",
                            expanded = expandedRTUsaha,
                            onExpandedChange = { expandedRTUsaha = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTUsaha = it },
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
                            value = nikUsaha,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikUsaha = it
                                }
                            },
                            label = { Text("NIK (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikUsaha.isNotEmpty() && !isNikValid,
                            singleLine = true
                        )
                        if (nikUsaha.isNotEmpty() && !isNikValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        OutlinedTextField(
                            value = namaUsaha,
                            onValueChange = {
                                namaUsaha = it.filter { c -> c.isLetter() || c.isWhitespace() }
                            },
                            label = { Text("Nama") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        DropdownField(
                            value = tempatLahirUsaha,
                            label = "Tempat Lahir",
                            expanded = tempatLahirUsahaExpanded,
                            onExpandedChange = { tempatLahirUsahaExpanded = it },
                            options = daftarKotaIndonesia,
                            onOptionSelected = { tempatLahirUsaha = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tanggalLahirUsaha,
                            onValueChange = {},
                            label = { Text("Tanggal Lahir") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = "Pilih Tanggal"
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        GenderSelection(
                            selectedGender = jenisKelaminUsaha,
                            onGenderSelected = { jenisKelaminUsaha = it }
                        )
                        OutlinedTextField(
                            value = alamat,
                            onValueChange = { alamat = it },
                            label = { Text("Alamat Lengkap") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 5
                        )
                    }
                }

                // Tentang Usaha
                SectionCard(
                    title = "Tentang Usaha",
                    icon = Icons.Default.ShoppingCart,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        DropdownField(
                            value = jenisUsaha,
                            label = "Jenis Usaha",
                            expanded = jenisUsahaExpanded,
                            onExpandedChange = { jenisUsahaExpanded = it },
                            options = jenisUsahaList,
                            onOptionSelected = { jenisUsaha = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = ukuranTempatUsaha,
                            onValueChange = { ukuranTempatUsaha = it },
                            label = { Text("Ukuran Tempat Usaha") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            trailingIcon = { Text(text = "m²", style = MaterialTheme.typography.bodyMedium) }
                        )
                        OutlinedTextField(
                            value = lokasiUsaha,
                            onValueChange = { lokasiUsaha = it },
                            label = { Text("Lokasi Usaha") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 150.dp),
                            maxLines = 8
                        )
                        OutlinedTextField(
                            value = lamaUsaha,
                            onValueChange = { newValue ->
                                if (newValue.length <= 2 && newValue.all { it.isDigit() }) {
                                    lamaUsaha = newValue
                                }
                            },
                            label = { Text("Lama Usaha") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            trailingIcon = { Text(text = "tahun", style = MaterialTheme.typography.bodyMedium) }
                        )
                        OutlinedTextField(
                            value = tujuanSuratUsaha,
                            onValueChange = { tujuanSuratUsaha = it },
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
                        com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.FileUploadSection(
                            title = "Surat Pengantar RT",
                            fileName = namaFileSuratPengantarRT,
                            onPickFileClick = { pemilihFileSuratRT.launch("application/pdf") },
                            primaryColor = primaryColor,
                            infoText = "Upload file PDF maksimal 2MB"
                        )
                        com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.FileUploadSection(
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

                ActionButtonsInput(
                    onCancel = {
                        resetForm()
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Form telah direset")
                        }
                    },
                    onConfirm = {
                        if (isFormValid) {
                            coroutineScope.launch {
                                val urlSuratPengantarRT = uriFileSuratPengantarRT?.let {
                                    uploadToSupabaseSDK(context, "surat_pengantar_rt", it)
                                }
                                val urlPersyaratan = uriFilePersyaratan?.let {
                                    uploadToSupabaseSDK(context, "persyaratan", it)
                                }

                                if (urlSuratPengantarRT != null && urlPersyaratan != null) {
                                    if (urlSuratPengantarRT.isNotEmpty() && urlPersyaratan.isNotEmpty()) {
                                        val idUser = 0
                                        val data = UsahaData(
                                            namaUsaha = namaUsaha,
                                            nikUsaha = nikUsaha,
                                            tanggalLahirUsaha = tanggalLahirUsaha,
                                            tempatLahirUsaha = tempatLahirUsaha,
                                            jenisKelaminUsaha = jenisKelaminUsaha,
                                            alamat = alamat,
                                            jenisUsaha = jenisUsaha,
                                            ukuranTempatUsaha = ukuranTempatUsaha,
                                            lokasiUsaha = lokasiUsaha,
                                            lamaUsaha = lamaUsaha,
                                            tujuanSuratUsaha = tujuanSuratUsaha,
                                            rtUsaha = selectedRTUsaha,
                                            rwUsaha = selectedRWUsaha,
                                            namaFileSuratPengantarRT = urlSuratPengantarRT,
                                            namaFilePersyaratan = urlPersyaratan,
                                            id_user = idUser
                                        )
                                        viewModel.simpanDataUsaha(data)
                                        snackbarHostState.showSnackbar("Data Tersimpan")
                                        navController.navigate(Screen.ReviewSuratUsaha.route)
                                    } else {
                                        snackbarHostState.showSnackbar("Upload file gagal, coba lagi")
                                    }
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
fun ActionButtonsInput(
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
                contentColor = Color.White
            ),
            enabled = isConfirmEnabled
        ) {
            Text(
                text = "Konfirmasi",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

fun readBytesFromUri(context: Context, uri: Uri): ByteArray {
    return context.contentResolver.openInputStream(uri)?.readBytes() ?: ByteArray(0)
}

@Preview(showBackground = true)
@Composable
fun SuratKeteranganUsahaPreview() {
    SuratKeteranganUsaha(rememberNavController())
}