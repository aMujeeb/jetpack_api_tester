package com.mujapps.composetesterx.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val mStudentRepo: StudentDataRepository) : ViewModel() {

    fun requestStudentApi() {
        viewModelScope.launch {
            //.createStudent()
            LoggerUtil.logMessage("Key board action Clicked")
        }
    }
}