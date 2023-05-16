package com.mujapps.composetesterx.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.models.Student
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val mStudentRepo: StudentDataRepository) : ViewModel() {

    fun requestStudentApi() {
        viewModelScope.launch {
            //mStudentRepo.createStudent(Student(23, 0.0f, 123.0f))
            //mStudentRepo.deleteStudent()
            mStudentRepo.getStudent("ngle")
            LoggerUtil.logMessage("Key board action Clicked")
        }
    }
}