package com.mujapps.composetesterx.data

import retrofit2.http.POST

interface StudentsDataApiService {

    @POST("compare_your_self")
    suspend fun publishStudent(): Any
}