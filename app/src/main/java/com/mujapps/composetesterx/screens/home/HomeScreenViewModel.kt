package com.mujapps.composetesterx.screens.home

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.Resource
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.models.Student
import com.mujapps.composetesterx.utils.LoggerUtil
import com.mujapps.composetesterx.utils.StudentAppConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        requestStudents()
    }

    fun requestStudents() {
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

    fun onDeleteStudent(studentId: String) {
        mStudentRepo.deleteStudent(studentId).onEach { results ->
            when (results) {
                is Resource.Success -> {
                    mHomeViewState = mHomeViewState.copy(isLoading = false, data = null, errorMessage = null, isSuccess = false, isStudentDeleted = true)
                }

                is Resource.Error -> {
                    mHomeViewState = mHomeViewState.copy(
                        isLoading = false,
                        data = null,
                        errorMessage = results.message ?: "Cannot collect data error",
                        isStudentDeleted = false
                    )
                }

                else -> {
                    //mSplashState = mSplashState.copy(is = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onNavigateAway() {
        mHomeViewState = mHomeViewState.copy(isLoading = false, isSuccess = false, errorMessage = null, data = null, isStudentDeleted = false)
    }
}