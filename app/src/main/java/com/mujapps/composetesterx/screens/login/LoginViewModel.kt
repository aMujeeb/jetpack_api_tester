package com.mujapps.composetesterx.screens.login

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.utils.StudentAppConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository,
    private val mSharedPreferences: SharedPreferences
) : ViewModel() {

    var mLoginState by mutableStateOf(LoginState())
        private set

    fun loginUser(userName: String, password: String) {
        mLoginState = mLoginState.copy(isLoading = true)
        viewModelScope.launch {
            mStudentRepo.loginUser(userName, password) {
                mLoginState = if (it.mLoginFailed) {
                    mLoginState.copy(isLoading = false, errorMessage = it.mError)
                } else {
                    if (it.mIsValid) {
                        mSharedPreferences.edit().putString(StudentAppConfig.LOGIN_ACCESS_TOKEN, it.mSessionToken).apply()
                        mSharedPreferences.edit().putString(StudentAppConfig.LOGIN_REFRESH_TOKEN, it.mRefreshToken).apply()
                        mSharedPreferences.edit().putString(StudentAppConfig.LOGIN_USER_NAME, userName).apply()
                        mLoginState.copy(isLoading = false, isSuccess = true)
                    } else {
                        mLoginState.copy(isLoading = false, errorMessage = "Invalid User. Contact Admin")
                    }
                }
            }
        }
    }

    fun onNavigateAway() {
        mLoginState = mLoginState.copy(isLoading = false, isSuccess = false, errorMessage = null)
    }
}