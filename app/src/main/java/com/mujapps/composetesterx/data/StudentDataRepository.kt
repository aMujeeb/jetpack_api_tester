package com.mujapps.composetesterx.data

import com.mujapps.composetesterx.models.Student
import java.lang.Exception
import javax.inject.Inject

class StudentDataRepository @Inject constructor(private val mStudentsApiService: StudentsDataApiService) {
    suspend fun createStudent(student: Student): Any {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.publishStudent(student)
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }

    suspend fun deleteStudent(): Any {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.removeStudent()
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }

    suspend fun getStudent(path: String?): Any {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.getStudentDetails(path)
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }
}