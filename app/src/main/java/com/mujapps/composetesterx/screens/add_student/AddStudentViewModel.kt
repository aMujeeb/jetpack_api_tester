package com.mujapps.composetesterx.screens.add_student

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.Resource
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.models.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddStudentViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository
) : ViewModel() {

    var mAddStudentViewState by mutableStateOf(AddStudentViewState())
        private set

    fun requestStudentApi() {
        viewModelScope.launch {
            //mStudentRepo.createStudent(Student(23, 343.0f, 123.0f))
            //mStudentRepo.deleteStudent()
            //mStudentRepo.getStudent("all")
            //LoggerUtil.logMessage("Key board action Clicked")
        }
    }

    fun addNewStudentDetails(age: Int, height: Float, income: Float) {
        mStudentRepo.createStudent(Student(age, height, income, UUID.randomUUID().toString())).onEach { results ->
            when (results) {
                is Resource.Success -> {
                    mAddStudentViewState = mAddStudentViewState.copy(isLoading = false, errorMessage = null, isSuccess = true)
                }

                is Resource.Error -> {
                    mAddStudentViewState = mAddStudentViewState.copy(
                        isLoading = false,
                        errorMessage = results.message ?: "Cannot collect data error",
                    )
                }

                else -> {
                    //mSplashState = mSplashState.copy(is = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onNavigateAway() {
        mAddStudentViewState = mAddStudentViewState.copy(isLoading = false, isSuccess = false, errorMessage = null)
    }
}