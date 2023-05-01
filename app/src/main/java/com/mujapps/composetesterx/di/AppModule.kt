package com.mujapps.composetesterx.di

import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.data.StudentsDataApiService
import com.mujapps.composetesterx.utils.StudentAppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMyStudentApi(builder: OkHttpClient): StudentsDataApiService {
        return Retrofit.Builder().baseUrl(StudentAppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(builder).build()
            .create(StudentsDataApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideStudentsDataRepository(mStudentsApiService: StudentsDataApiService) =
        StudentDataRepository(mStudentsApiService)
}