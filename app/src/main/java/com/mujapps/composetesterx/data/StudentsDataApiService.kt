package com.mujapps.composetesterx.data

import com.mujapps.composetesterx.data.dao.ConfigurationsDao
import com.mujapps.composetesterx.models.MessageResponse
import com.mujapps.composetesterx.models.Student
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentsDataApiService {

    @POST("compare_your_self")
    suspend fun publishStudent(@Body student: Student): MessageResponse

    @DELETE("compare_your_self")
    suspend fun removeStudent(@Query("studentId") studentId : String): MessageResponse

    @GET("compare_your_self/{type}")
    suspend fun getStudentDetails(@Path("type") mPath: String?): List<Student>

    @GET("configs")
    suspend fun getConfigurations(): ConfigurationsDao
}