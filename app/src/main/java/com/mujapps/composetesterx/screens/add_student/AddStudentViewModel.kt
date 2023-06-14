package com.mujapps.composetesterx.screens.add_student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.models.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddStudentViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository
) : ViewModel() {
    fun requestStudentApi() {
        viewModelScope.launch {
            //mStudentRepo.createStudent(Student(23, 343.0f, 123.0f))
            //mStudentRepo.deleteStudent()
            //mStudentRepo.getStudent("all")
            //LoggerUtil.logMessage("Key board action Clicked")
        }
    }

    fun addNewStudentDetails(age: Int, height: Float, income: Float) {
        viewModelScope.launch {
            mStudentRepo.createStudent(Student(age, height, income, UUID.randomUUID().toString()))
        }
    }

}