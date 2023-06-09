package com.mujapps.composetesterx.screens.home

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.models.Student
import com.mujapps.composetesterx.utils.LoggerUtil
import com.mujapps.composetesterx.utils.StudentAppConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository,
    private val mSharedPreferences: SharedPreferences
) : ViewModel() {

    var mHomeViewState by mutableStateOf(HomeViewState())
        private set

    fun requestStudentApi() {
        viewModelScope.launch {
            //mStudentRepo.createStudent(Student(23, 343.0f, 123.0f))
            //mStudentRepo.deleteStudent()
            mStudentRepo.getStudent("all")
            //LoggerUtil.logMessage("Key board action Clicked")
        }
    }

    init {
        viewModelScope.launch {
            mHomeViewState = mHomeViewState.copy(isLoading = true)
            val resource = mStudentRepo.getStudent("all")
            mHomeViewState = if (resource.data != null) {
                mHomeViewState.copy(isLoading = false, data = resource.data as List<Student>?, errorMessage = null, isSuccess = true)
            } else {
                mHomeViewState.copy(isLoading = false, data = null, errorMessage = resource.message ?: "Cannot collect data error")
            }
        }
    }

    fun checkTokens() {
        //LoggerUtil.logMessage("Saved Token :" + mSharedPreferences.getString(StudentAppConfig.LOGIN_ACCESS_TOKEN, "NoColasßß"))
        //mStudentRepo.getAuthenticatedUser()
    }

    fun onDeleteStudent() {
        viewModelScope.launch {
            mHomeViewState = mHomeViewState.copy(isLoading = true)
            val resource = mStudentRepo.deleteStudent()
            mHomeViewState = if (resource.data == true) {
                mHomeViewState.copy(isLoading = false, data = null, errorMessage = null, isSuccess = true, isStudentDeleted = true)
            } else {
                mHomeViewState.copy(
                    isLoading = false,
                    data = null,
                    errorMessage = resource.message ?: "Cannot collect data error",
                    isStudentDeleted = false
                )
            }
        }
    }

    fun onNavigateAway() {
        mHomeViewState = mHomeViewState.copy(isLoading = false, isSuccess = false, errorMessage = null, data = null)
    }
}