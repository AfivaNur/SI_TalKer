package com.afiva.appskelurahan.ui.screens.Keterangan_Pengantar_RT

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.Toast
import android.provider.OpenableColumns
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
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
import com.afiva.appskelurahan.model.SuratPengantarRTData
import com.afiva.appskelurahan.v_model.SuratPengantarRTViewModel
import com.afiva.appskelurahan.viewmodel.KeteranganViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.afiva.appskelurahan.uploadToSupabaseSDK
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.ui.screens.Domisili.SectionCard // Adjust import path as needed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuratPengantar(navController: NavController) { BackHandler(enabled = true) {}
    val context = LocalContext.current
    val viewModel: SuratPengantarRTViewModel = viewModel() // Adjust ViewModel as needed
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Color theme
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    // Form Pembuat
    var selectedRWPengantar by remember { mutableStateOf(SessionManager.currentUser?.rw ?: "") }
    var selectedRTPengantar by remember { mutableStateOf(SessionManager.currentUser?.rt ?: "") }
    var namaKetuaRTPengantar by remember { mutableStateOf("") }
    var jabatanKetuaRT by remember { mutableStateOf("Ketua RT") }

    // Keterangan Pembuat
    var namaPengantar by remember { mutableStateOf(SessionManager.currentUser?.username ?: "") }
    var nikPengantar by remember { mutableStateOf(SessionManager.currentUser?.nik ?: "") }
    var tempatLahirPengantar by remember { mutableStateOf(SessionManager.currentUser?.tempatLahir ?: "") }
    var tanggalLahirPengantar by remember { mutableStateOf(SessionManager.currentUser?.tanggalLahir ?: "") }
    var jenisKelaminPengantar by remember { mutableStateOf(SessionManager.currentUser?.jenisKelamin ?: "") }
    var agamaPengantar by remember { mutableStateOf(SessionManager.currentUser?.agama ?: "") }
    var kewarganegaraan by remember { mutableStateOf(SessionManager.currentUser?.kewarganegaraan ?: "") }
    var statusPerkawinanPengantar by remember { mutableStateOf(SessionManager.currentUser?.statusPerkawinan ?: "") }
    var pekerjaanPengantar by remember { mutableStateOf(SessionManager.currentUser?.pekerjaan ?: "") }
    var alamatPengantar by remember { mutableStateOf(SessionManager.currentUser?.alamat ?: "") }
    var keteranganTujuanPengantar by remember { mutableStateOf("") }
    var namaFilePersyaratan by remember { mutableStateOf("") }
    var uriFilePersyaratan by remember { mutableStateOf<Uri?>(null) }

    // Expanded states
    var expandedRWPengantar by remember { mutableStateOf(false) }
    var expandedRTPengantar by remember { mutableStateOf(false) }
    var expandedTempatLahirPengantar by remember { mutableStateOf(false) }
    var expandedAgamaPengantar by remember { mutableStateOf(false) }
    var expandedStatusPerkawinanPengantar by remember { mutableStateOf(false) }
    var expandedKeteranganTujuanPengantar by remember { mutableStateOf(false) }
    var expandedPekerjaanPengantar by remember { mutableStateOf(false) }

    // Options
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }
    val agamaOptions = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
    val statusPerkawinanOptions = listOf("Belum Kawin", "Kawin", "Cerai Hidup", "Cerai Mati")
    val tujuanOptions = listOf("Surat Keterangan Domisili", "Surat Keterangan Tidak Mampu ", "Surat Keterangan Usaha", "Surat Keterangan Pindah", "Surat Keterangan Khusus", "Surat Keterangan Kematian")
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
    val isNikValid = nikPengantar.length == 16 && nikPengantar.all { it.isDigit() }
    val isFormValid = isNikValid &&
            selectedRWPengantar!!.isNotBlank() &&
            selectedRTPengantar!!.isNotBlank() &&
            namaKetuaRTPengantar.isNotBlank() &&
            jabatanKetuaRT.isNotBlank() &&
            namaPengantar!!.isNotBlank() &&
            nikPengantar!!.isNotBlank() &&
            tempatLahirPengantar!!.isNotBlank() &&
            tanggalLahirPengantar!!.isNotBlank() &&
            jenisKelaminPengantar!!.isNotBlank() &&
            agamaPengantar!!.isNotBlank() &&
            kewarganegaraan!!.isNotBlank() &&
            statusPerkawinanPengantar!!.isNotBlank() &&
            pekerjaanPengantar!!.isNotBlank() &&
            alamatPengantar!!.isNotBlank() &&
            keteranganTujuanPengantar.isNotBlank() &&
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
            tanggalLahirPengantar = formatter.format(selectedDate.time)
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


    // File picker
    val pemilihFilePersyaratan = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            uriFilePersyaratan = it
            namaFilePersyaratan = it.lastPathSegment?.substringAfterLast("/") ?: "berkas_persyaratan.pdf"
        }
    }

    // Reset form function
    val resetForm = {
        selectedRWPengantar = SessionManager.currentUser?.rw.toString()
        selectedRTPengantar = SessionManager.currentUser?.rt.toString()
        namaKetuaRTPengantar = ""
        jabatanKetuaRT = "Ketua RT"
        namaPengantar = SessionManager.currentUser?.username.toString()
        nikPengantar = SessionManager.currentUser?.nik.toString()
        tempatLahirPengantar = SessionManager.currentUser?.tempatLahir.toString()
        tanggalLahirPengantar = SessionManager.currentUser?.tanggalLahir.toString()
        jenisKelaminPengantar = SessionManager.currentUser?.jenisKelamin.toString()
        agamaPengantar = SessionManager.currentUser?.agama.toString()
        kewarganegaraan = SessionManager.currentUser?.kewarganegaraan.toString()
        statusPerkawinanPengantar = SessionManager.currentUser?.statusPerkawinan.toString()
        pekerjaanPengantar = SessionManager.currentUser?.pekerjaan.toString()
        alamatPengantar = SessionManager.currentUser?.alamat.toString()
        keteranganTujuanPengantar = ""
        namaFilePersyaratan = ""
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
                                text = "PENGANTAR RT",
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
                            value = selectedRWPengantar,
                            label = "RW",
                            expanded = expandedRWPengantar,
                            onExpandedChange = { expandedRWPengantar = it },
                            options = rwOptions,
                            onOptionSelected = { selectedRWPengantar = it },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            value = selectedRTPengantar,
                            label = "RT",
                            expanded = expandedRTPengantar,
                            onExpandedChange = { expandedRTPengantar = it },
                            options = rtOptions,
                            onOptionSelected = { selectedRTPengantar = it },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Informasi Ketua RT
                SectionCard(
                    title = "Informasi Ketua RT",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = namaKetuaRTPengantar,
                            onValueChange = {
                                namaKetuaRTPengantar = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama Ketua RT") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        OutlinedTextField(
                            value = jabatanKetuaRT,
                            onValueChange = {},
                            label = { Text("Jabatan") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Keterangan Pembuat
                SectionCard(
                    title = "Keterangan Pembuat",
                    icon = Icons.Default.Person,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedTextField(
                            value = namaPengantar,
                            onValueChange = {
                                namaPengantar = it.filter { char -> char.isLetter() || char.isWhitespace() }
                            },
                            label = { Text("Nama") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        OutlinedTextField(
                            value = nikPengantar,
                            onValueChange = {
                                if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                                    nikPengantar = it
                                }
                            },
                            label = { Text("NIK (16 digit)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nikPengantar.isNotEmpty() && !isNikValid,
                            singleLine = true
                        )
                        if (nikPengantar.isNotEmpty() && !isNikValid) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        DropdownField(
                            value = tempatLahirPengantar,
                            label = "Tempat Lahir",
                            expanded = expandedTempatLahirPengantar,
                            onExpandedChange = { expandedTempatLahirPengantar = it },
                            options = kotaOptions,
                            onOptionSelected = { tempatLahirPengantar = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tanggalLahirPengantar,
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
                        Text("Jenis Kelamin", fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            listOf("Laki-laki", "Perempuan").forEach { gender ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = jenisKelaminPengantar == gender,
                                        onClick = { jenisKelaminPengantar = gender }
                                    )
                                    Text(gender)
                                }
                            }
                        }
                        DropdownField(
                            value = agamaPengantar,
                            label = "Agama",
                            expanded = expandedAgamaPengantar,
                            onExpandedChange = { expandedAgamaPengantar = it },
                            options = agamaOptions,
                            onOptionSelected = { agamaPengantar = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("Kewarganegaraan", fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            listOf("WNI", "WNA").forEach { option ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = kewarganegaraan == option,
                                        onClick = { kewarganegaraan = option }
                                    )
                                    Text(option)
                                }
                            }
                        }
                        DropdownField(
                            value = statusPerkawinanPengantar,
                            label = "Status Perkawinan",
                            expanded = expandedStatusPerkawinanPengantar,
                            onExpandedChange = { expandedStatusPerkawinanPengantar = it },
                            options = statusPerkawinanOptions,
                            onOptionSelected = { statusPerkawinanPengantar = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownField(
                            value = pekerjaanPengantar,
                            label = "Pekerjaan",
                            expanded = expandedPekerjaanPengantar,
                            onExpandedChange = { expandedPekerjaanPengantar = it },
                            options = pekerjaanOptions,
                            onOptionSelected = { pekerjaanPengantar = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = alamatPengantar,
                            onValueChange = { alamatPengantar = it },
                            label = { Text("Alamat") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            maxLines = 4
                        )
                    }
                }

                // Tujuan
                SectionCard(
                    title = "Tujuan",
                    icon = Icons.Default.Edit,
                    primaryColor = primaryColor,
                    lightColor = lightTeal
                ) {
                    DropdownField(
                        value = keteranganTujuanPengantar,
                        label = "Tujuan Surat",
                        expanded = expandedKeteranganTujuanPengantar,
                        onExpandedChange = { expandedKeteranganTujuanPengantar = it },
                        options = tujuanOptions,
                        onOptionSelected = { keteranganTujuanPengantar = it },
                        modifier = Modifier.fillMaxWidth()
                    )
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
                            title = "Berkas Persyaratan",
                            fileName = namaFilePersyaratan,
                            onPickFileClick = { pemilihFilePersyaratan.launch("application/pdf") },
                            primaryColor = primaryColor,
                            infoText = "Upload file PDF maksimal 2MB"
                        )

                        Spacer(modifier = Modifier.height(8.dp)) // beri jarak agar tidak menumpuk

                        Text(
                            text = "Seluruh persyaratan dijadikan 1 PDF saja!",
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
                                    val urlPersyaratan = uriFilePersyaratan?.let {
                                        uploadToSupabaseSDK(context, "persyaratan", it)
                                    }
                                    if (urlPersyaratan != null && urlPersyaratan.isNotEmpty()) {
                                        val data = SuratPengantarRTData( // Adjust data class as needed
                                            rwPengantar = selectedRWPengantar,
                                            rtPengantar = selectedRTPengantar,
                                            namaKetuaRtPengantar = namaKetuaRTPengantar,
                                            jabatanKetuaRt = jabatanKetuaRT,
                                            namaPengantar = namaPengantar,
                                            nikPengantar = nikPengantar,
                                            tempatLahirPengantar = tempatLahirPengantar,
                                            tanggalLahirPengantar = tanggalLahirPengantar,
                                            jenisKelaminPengantar = jenisKelaminPengantar,
                                            agamaPengantar = agamaPengantar,
                                            kewarganegaraan = kewarganegaraan,
                                            statusPerkawinanPengantar = statusPerkawinanPengantar,
                                            pekerjaanPengantar = pekerjaanPengantar,
                                            alamatPengantar = alamatPengantar,
                                            keteranganTujuanPengantar = keteranganTujuanPengantar,
                                            namaFilePersyaratan = urlPersyaratan
                                        )
                                        viewModel.simpanDataSuratPengantarRT(data)
                                        snackbarHostState.showSnackbar("Data Tersimpan")
                                        navController.navigate("surat_pengantar_rt_output")
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
                        text = "Form Surat Pengantar RT",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF2C3E50),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "WAJIB DIBUAT!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFFFF003B),
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
fun SuratPengantarPreview() {
    SuratPengantar(rememberNavController())
}