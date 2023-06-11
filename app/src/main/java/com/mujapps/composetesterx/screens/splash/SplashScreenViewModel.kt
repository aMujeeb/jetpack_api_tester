package com.mujapps.composetesterx.screens.splash

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.Resource
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.models.Configuration
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository,
    private val mSharedPreferences: SharedPreferences
) : ViewModel() {

    var mSplashState by mutableStateOf(SplashState())
        private set


    suspend fun requestConfigurations() {
        mStudentRepo.getConfigs().onEach { results ->
            when (results) {
                is Resource.Success -> {
                    checkIsUserLoggedIn()
                }

                is Resource.Error -> {
                    mSplashState = mSplashState.copy(isUserExists = false)
                }

                else -> {
                    //mSplashState = mSplashState.copy(is = false)
                }
            }
            LoggerUtil.logMessage("Is User Configs")
        }.launchIn(viewModelScope)
    }

    private fun checkIsUserLoggedIn() {
        //viewModelScope.launch {
        /*mStudentRepo.getCurrentUser() { isSuccess ->
            LoggerUtil.logMessage("Is User already log In :$isSuccess")
            mSplashState = mSplashState.copy(isUserExists = isSuccess)
        }*/

        mStudentRepo.getAuthenticatedUser() { isSuccess ->
            LoggerUtil.logMessage("Is User already log In :$isSuccess")
            mSplashState = mSplashState.copy(isUserExists = isSuccess)
        }
        //}
    }

    fun onNavigateAway() {
        mSplashState = mSplashState.copy(isUserExists = null)
    }
}