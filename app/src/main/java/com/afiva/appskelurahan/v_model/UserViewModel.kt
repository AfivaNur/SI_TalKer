package com.afiva.appskelurahan.viewmodel

import androidx.lifecycle.ViewModel
import com.afiva.appskelurahan.model.DomisiliData
import com.afiva.appskelurahan.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {
    private val _dataUser = MutableStateFlow<UserData?>(null)
    val dataUser: StateFlow<UserData?> = _dataUser.asStateFlow()
    fun simpanDataDomisili(data: UserData) {
        _dataUser.value = data
    }

    // Reset semua data
    fun resetData() {
        _dataUser.value = UserData()
    }
}
