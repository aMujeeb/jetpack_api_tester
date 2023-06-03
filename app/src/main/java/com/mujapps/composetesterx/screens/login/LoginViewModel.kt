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

    //private val _loginState = MutableStateFlow(LoginState())
    //val mLoginState: StateFlow<LoginState> = _loginState.asStateFlow()

    var mLoginState by mutableStateOf(LoginState())
        private set

    fun requestStudentApi() {
        viewModelScope.launch {
            //mStudentRepo.createStudent(Student(23, 343.0f, 123.0f))
            //mStudentRepo.deleteStudent()
            //mStudentRepo.getStudent("all")
            //LoggerUtil.logMessage("Key board action Clicked")
        }
    }

    fun loginUser(userName: String, password: String) {
        mLoginState = mLoginState.copy(isLoading = true)
        viewModelScope.launch {
            mStudentRepo.loginUser(userName, password) {
                if (it.mLoginFailed) {
                    mLoginState = mLoginState.copy(isLoading = false, errorMessage = it.mError)
                } else {
                    if (it.mIsValid) {
                        mSharedPreferences.edit().putString(StudentAppConfig.LOGIN_ACCESS_TOKEN, it.mSessionToken).apply()
                        mSharedPreferences.edit().putString(StudentAppConfig.LOGIN_REFRESH_TOKEN, it.mRefreshToken).apply()
                        mSharedPreferences.edit().putString(StudentAppConfig.LOGIN_USER_NAME, userName).apply()
                        mLoginState = mLoginState.copy(isLoading = false, isSuccess = true)
                    } else {
                        mLoginState = mLoginState.copy(isLoading = false, errorMessage = "Invalid User. Contact Admin")
                    }
                }
            }
        }
    }

    fun onNavigateAway() {
        /*_signUpState.update {
            it.copy(isLoading = false, signUpSuccess = false, errorMessage = null)
        }*/
        mLoginState = mLoginState.copy(isLoading = false, isSuccess = false, errorMessage = null)
    }
}