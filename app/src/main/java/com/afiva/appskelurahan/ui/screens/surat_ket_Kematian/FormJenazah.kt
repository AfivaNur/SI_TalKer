package com.afiva.appskelurahan.ui.layar.surat_ket_kematian

import androidx.lifecycle.viewmodel.compose.viewModel
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.uploadToSupabaseSDK
import kotlinx.coroutines.launch
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.KematianData
import com.afiva.appskelurahan.ui.screens.Domisili.SectionCard
import com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT.FileUploadSection
import com.afiva.appskelurahan.viewmodel.KematianViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormJenazah(navController: NavController) { BackHandler(enabled = true) {}
    val supabase = SupabaseProvider.client

    var namaJenazah by remember { mutableStateOf("") }
    var jenisKelaminJenazah by remember { mutableStateOf("") }
    var umurJenazah by remember { mutableStateOf("") }
    var pekerjaanJenazah by remember { mutableStateOf("") }
    var pekerjaanJenazahExpanded by remember { mutableStateOf(false) }
    var alamatJenazah by remember { mutableStateOf("") }
    var tanggalKematianJenazah by remember { mutableStateOf("") }
    var sebabKematianJenazah by remember { mutableStateOf("") }
    var sebabKematianExpanded by remember { mutableStateOf(false) }
    var namayangMenerangkan by remember { mutableStateOf("") }


    var selectedRTKematian by remember { mutableStateOf("") }
    var selectedRWKematian by remember { mutableStateOf("") }
    var expandedRWKematian by remember { mutableStateOf(false) }
    var expandedRTKematian by remember { mutableStateOf(false) }

    var namaYangMelapor by remember { mutableStateOf(SessionManager.currentUser?.username ?: "")  }
    var hubunganPelapor by remember { mutableStateOf("") }
    var hubunganPelaporExpanded by remember { mutableStateOf(false) }
    var namayangMenerangkanExpanded by remember { mutableStateOf(false) }

    val pekerjaanList = listOf(
        "Buruh",
        "Dokter",
        "Dosen",
        "Guru",
        "Ibu Rumah Tangga",
        "Nelayan",
        "Notaris",
        "PNS",
        "Pegawai Swasta",
        "Pedagang",
        "Pelajar/Mahasiswa",
        "Pengacara",
        "Penulis",
        "Perawat",
        "Petani",
        "Polri",
        "Seniman",
        "Sopir",
        "TNI",
        "Tidak Bekerja",
        "Wiraswasta"
    )

    val hubunganList = listOf(
        "Ayah",
        "Ibu",
        "Anak",
        "Suami",
        "Istri",
        "Kakak",
        "Adik",
        "Kakek",
        "Nenek",
        "Cucu",
        "Paman",
        "Bibi",
        "Keponakan",
        "Sepupu",
        "Tetangga",
        "Teman",
        "Pengurus RT/RW",
        "Perangkat Desa/Kelurahan",
        "Lainnya"
    )


    val sebabKematianJenazahList = listOf(
        "Sakit Biasa/Tua",
        "Kecelakaan",
        "Wabah Penyakit",
        "Kriminalitas",
        "Bunuh Diri",
        "Lainnya"
    )

    val ygMenerangkanList = listOf(
        "Dokter",
        "Petugas medis",
        "Petugas rumah sakit",
        "Petugas kamar jenazah",
        "Koroner",
        "Pemeriksa medis"
    )

    // Color theme
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

// Options lists
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }

    // Date picker meninggal
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialogKematian = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
            tanggalKematianJenazah = formatter.format(selectedDate.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    // Atur tanggal maksimal: hari ini
    datePickerDialogKematian.datePicker.maxDate = Calendar.getInstance().timeInMillis

    // File upload states
    var namaFileSuratPengantarRT by remember { mutableStateOf("") }
    var namaFilePersyaratan by remember { mutableStateOf("") }
    var uriFileSuratPengantarRT by remember { mutableStateOf<Uri?>(null) }
    var uriFilePersyaratan by remember { mutableStateOf<Uri?>(null) }


    val pemilihFileSuratRT = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                uriFileSuratPengantarRT = it
                namaFileSuratPengantarRT =
                    it.lastPathSegment?.substringAfterLast("/") ?: "surat_pengantar_rt.pdf"
            }
        }
    )

    val pemilihFilePersyaratan = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                uriFilePersyaratan = it
                namaFilePersyaratan =
                    it.lastPathSegment?.substringAfterLast("/") ?: "berkas_persyaratan.pdf"
            }
        }
    )

    val isFormValid = namaJenazah.isNotBlank() &&
            jenisKelaminJenazah.isNotBlank() &&
            umurJenazah.isNotBlank() &&
            pekerjaanJenazah.isNotBlank() &&
            alamatJenazah.isNotBlank() &&
            tanggalKematianJenazah.isNotBlank() &&
            sebabKematianJenazah.isNotBlank() &&
            namaYangMelapor!!.isNotBlank() &&
            selectedRTKematian.isNotBlank() &&
            selectedRWKematian.isNotBlank() &&
            namayangMenerangkan.isNotBlank() &&
            hubunganPelapor.isNotBlank()


    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    val resetForm = {
        namaJenazah = ""
        jenisKelaminJenazah = ""
        umurJenazah = ""
        pekerjaanJenazah = ""
        alamatJenazah = ""
        tanggalKematianJenazah = ""
        sebabKematianJenazah = ""
        sebabKematianExpanded = false
        namayangMenerangkan = ""


        selectedRTKematian = ""
        selectedRWKematian = ""
        expandedRWKematian = false
        expandedRTKematian = false

        namaYangMelapor = SessionManager.currentUser?.username.toString()
        hubunganPelapor = ""
        hubunganPelaporExpanded = false
    }

    val viewModel: KematianViewModel = viewModel() // atau pakai Koin/Hilt kalau inject

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
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header dengan gradient
            HeaderSection(primaryColor = primaryColor)

            // Form sections
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section Informasi Wilayah
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
                            value = selectedRWKematian,
                            label = "RW",
                            expanded = expandedRWKematian,
                            onExpandedChange = { expandedRWKematian = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWKematian = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRTKematian,
                            label = "RT",
                            expanded = expandedRTKematian,
                            onExpandedChange = { expandedRTKematian = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTKematian = it },
                            modifier = Modifier.weight(1f)
                        )

                    }
                }
                // yg bertanda tangan
                SectionCard(
                    title = "Data Jenazah",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = namaJenazah,
                            onValueChange = {
                                val filtered =
                                    it.filter { char -> char.isLetter() || char.isWhitespace() }
                                namaJenazah = filtered
                            },
                            label = { Text("Nama") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Jenis Kelamin
                            GenderSelection(
                                selectedGender = jenisKelaminJenazah,
                                onGenderSelected = { jenisKelaminJenazah = it }
                            )
                            DropdownField(
                                value = pekerjaanJenazah,
                                label = "Pekerjaan",
                                expanded = pekerjaanJenazahExpanded,
                                onExpandedChange = { pekerjaanJenazahExpanded = it },
                                options = pekerjaanList,
                                onOptionSelected = { pekerjaanJenazah = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                OutlinedTextField(
                                    value = alamatJenazah,
                                    onValueChange = { alamatJenazah = it },
                                    label = { Text("Alamat Lengkap") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 150.dp),
                                    maxLines = 8
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    OutlinedTextField(
                                        value = umurJenazah,
                                        onValueChange = { newValue ->
                                            // Hanya izinkan angka dan maksimal 2 digit
                                            if (newValue.length <= 3 && newValue.all { it.isDigit() }) {
                                                umurJenazah = newValue
                                            }
                                        },
                                        label = { Text("Umur") },
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        singleLine = true,
                                    )
                                    OutlinedTextField(
                                        value = tanggalKematianJenazah,
                                        onValueChange = {},
                                        label = { Text("Tanggal Kematian") },
                                        readOnly = true,
                                        trailingIcon = {
                                            IconButton(onClick = { datePickerDialogKematian.show() }) {
                                                Icon(
                                                    Icons.Default.DateRange,
                                                    contentDescription = "Pilih Tanggal"
                                                )
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )

                                    // Penyebab Kematian Dropdown
                                    DropdownField(
                                        value = sebabKematianJenazah,
                                        label = "Penyebab Kematian",
                                        expanded = sebabKematianExpanded,
                                        onExpandedChange = { sebabKematianExpanded = it },
                                        options = sebabKematianJenazahList,
                                        onOptionSelected = { sebabKematianJenazah = it },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
// Section Data Pribadi
                SectionCard(
                    title = "Pelapor",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = namaYangMelapor,
                            onValueChange = {
                                val filtered =
                                    it.filter { char -> char.isLetter() || char.isWhitespace() }
                                namaYangMelapor = filtered
                            },
                            label = { Text("Nama") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        DropdownField(
                            value = hubunganPelapor,
                            label = "Hubungan dengan Jenazah",
                            expanded = hubunganPelaporExpanded,
                            onExpandedChange = { hubunganPelaporExpanded = it },
                            options = hubunganList,
                            onOptionSelected = { hubunganPelapor = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownField(
                            value = namayangMenerangkan,
                            label = "Yang Menerangkan Kematian ",
                            expanded = namayangMenerangkanExpanded,
                            onExpandedChange = { namayangMenerangkanExpanded = it },
                            options = ygMenerangkanList,
                            onOptionSelected = { namayangMenerangkan = it },
                            modifier = Modifier.fillMaxWidth()
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

                ActionButtons(
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
                                    uploadToSupabaseSDK(
                                        context, "surat_pengantar_rt",
                                        it
                                    )
                                }
                                val urlPersyaratan = uriFilePersyaratan?.let {
                                    uploadToSupabaseSDK(
                                        context, "persyaratan",
                                        it
                                    )
                                }

                                if (urlSuratPengantarRT != null) {
                                    if (urlPersyaratan != null) {
                                        if (urlSuratPengantarRT.isNotEmpty() && urlPersyaratan.isNotEmpty()) {
                                            val data = urlSuratPengantarRT?.let {
                                                urlPersyaratan?.let { it1 ->
                                                    KematianData(
                                                        namaJenazah = namaJenazah,
                                                        jenisKelaminJenazah = jenisKelaminJenazah,
                                                        umurJenazah = umurJenazah,
                                                        pekerjaanJenazah = pekerjaanJenazah,
                                                        alamatJenazah = alamatJenazah,
                                                        tanggalKematianJenazah = tanggalKematianJenazah,
                                                        sebabKematianJenazah = sebabKematianJenazah,
                                                        namaYangMenerangkan = namayangMenerangkan,
                                                        rtKematian = selectedRTKematian,
                                                        rwKematian = selectedRWKematian,
                                                        namaYangMelapor = namaYangMelapor,
                                                        hubunganPelapor = hubunganPelapor,
                                                        namaFileSuratPengantarRT = urlSuratPengantarRT,
                                                        namaFilePersyaratan = urlPersyaratan,
                                                        id_user = SessionManager.currentUser?.id ?: -1
                                                    )
                                                }
                                            }

                                            if (data != null) {

                                                viewModel.simpanDataKematian(data)
                                                snackbarHostState.showSnackbar("Data Tersimpan")
                                            }
                                            navController.navigate("display_surat_kematian_preview")
                                        } else {
                                            snackbarHostState.showSnackbar("Upload file gagal, coba lagi")
                                        }
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
                    isFormValid = isFormValid
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


fun readBytesFromUri(context: Context, uri: Uri): ByteArray {
    return context.contentResolver.openInputStream(uri)?.readBytes() ?: ByteArray(0)
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
                        .background(
                            primaryColor.copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Form Pengajuan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF2C3E50)
                    )
                    Text(
                        text = "Lengkapi data dengan benar",
                        fontSize = 14.sp,
                        color = Color(0xFF5D6D7E)
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
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        onExpandedChange(false)
                    }
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
    onPickFileClick: () -> Unit, // ganti dari onFileSelect
    primaryColor: Color,
    infoText: String = ""
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (infoText.isNotEmpty()) {
            Text(
                text = infoText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2C3E50)
        )

        Button(
            onClick = onPickFileClick, // langsung panggil callback
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
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
                    imageVector = Icons.Default.CheckCircle,
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
internal fun ActionButtons(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    primaryColor: Color,
    isFormValid: Boolean
) {
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
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = primaryColor
            )
        ) {
            Text("Ulangi", fontWeight = FontWeight.SemiBold)
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
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.7f)
            ),
            enabled = isFormValid
        ) {
            Text("Konfirmasi", fontWeight = FontWeight.SemiBold)
        }
    }
}


@Preview
@Composable
fun FormJenazahPreview() {
    FormJenazah(rememberNavController())
}
