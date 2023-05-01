package com.mujapps.composetesterx.data

import java.lang.Exception
import javax.inject.Inject

class StudentDataRepository @Inject constructor(private val mStudentsApiService: StudentsDataApiService) {
    suspend fun createStudent(): Any {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.publishStudent()
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }
}