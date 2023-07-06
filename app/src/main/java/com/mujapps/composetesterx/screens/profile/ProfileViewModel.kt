package com.mujapps.composetesterx.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.models.Student
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository
) : ViewModel() {

    fun requestStudentData(stData : String) {
        LoggerUtil.logMessage("Received Student ID :$stData")
        viewModelScope.launch {
            val resource = mStudentRepo.getStudent(stData)
            LoggerUtil.logMessage("Received Student Data :$resource")
        }
    }
}