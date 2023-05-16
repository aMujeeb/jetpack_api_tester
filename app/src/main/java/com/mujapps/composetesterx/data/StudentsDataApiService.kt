package com.mujapps.composetesterx.data

import com.mujapps.composetesterx.models.Student
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentsDataApiService {

    @POST("compare_your_self")
    suspend fun publishStudent(@Body student: Student): Any

    @DELETE("compare_your_self")
    suspend fun removeStudent(): Any

    @GET("compare_your_self/{type}")
    suspend fun getStudentDetails(@Path("type") mPath: String?): List<Any>
}