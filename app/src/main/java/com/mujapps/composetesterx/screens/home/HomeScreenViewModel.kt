package com.mujapps.composetesterx.screens.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.utils.LoggerUtil
import com.mujapps.composetesterx.utils.StudentAppConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository,
    private val mSharedPreferences: SharedPreferences
) : ViewModel() {

    fun checkTokens() {
        LoggerUtil.logMessage("Saved Token :" + mSharedPreferences.getString(StudentAppConfig.LOGIN_ACCESS_TOKEN, "NoColasßß"))
    }
}