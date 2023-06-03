package com.mujapps.composetesterx.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.mujapps.composetesterx.app.ComposeApp
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.data.StudentsDataApiService
import com.mujapps.composetesterx.data.UserManager
import com.mujapps.composetesterx.utils.StudentAppConfig
import com.mujapps.composetesterx.utils.StudentAppConfig.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
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
    fun provideAuthInterceptor(userManager: UserManager): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
            val originalHeader = originalRequest.headers
            requestBuilder.headers(originalHeader)
            requestBuilder.header("Authorization", userManager.getAuthorization())
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideInterceptor(authInterceptor: Interceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor(authInterceptor)
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
    fun provideStudentsDataRepository(mStudentsApiService: StudentsDataApiService, @ApplicationContext context: Context) =
        StudentDataRepository(mStudentsApiService, context)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
}