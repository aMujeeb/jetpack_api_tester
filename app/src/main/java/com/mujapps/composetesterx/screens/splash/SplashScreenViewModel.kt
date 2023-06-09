package com.mujapps.composetesterx.screens.splash

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository,
    private val mSharedPreferences: SharedPreferences
) : ViewModel() {

    var mSplashState by mutableStateOf(SplashState())
        private set

    fun checkIsUserLoggedIn() {
        viewModelScope.launch {
            /*mStudentRepo.getCurrentUser() { isSuccess ->
                LoggerUtil.logMessage("Is User already log In :$isSuccess")
                mSplashState = mSplashState.copy(isUserExists = isSuccess)
            }*/

            mStudentRepo.getAuthenticatedUser() { isSuccess ->
                LoggerUtil.logMessage("Is User already log In :$isSuccess")
                mSplashState = mSplashState.copy(isUserExists = isSuccess)
            }
        }
    }

    fun onNavigateAway() {
        mSplashState = mSplashState.copy(isUserExists = null)
    }
}