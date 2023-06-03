package com.mujapps.composetesterx.screens.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val mStudentRepo: StudentDataRepository) : ViewModel() {

    //private val _signUpState = MutableStateFlow(SignUpState())
    //val mSignUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()
    var mSignUpState by mutableStateOf(SignUpState())
        private set

    fun signUpUser(userEmail: String, password: String) {
        if (userEmail.isEmpty() || password.isEmpty()) return
        LoggerUtil.logMessage("Button Clicked View Model:$userEmail : $password")
        //_signUpState.value = SignUpState(isLoading = true)
        mSignUpState = mSignUpState.copy(isLoading = true)
        viewModelScope.launch {
            mStudentRepo.signUpUser(userEmail, password) {
                if (it.data != null) {
                    LoggerUtil.logMessage("VModel SignUpSuccess")
                    //_signUpState.value = SignUpState(isSuccess = true, isLoading = false)
                    mSignUpState = mSignUpState.copy(isSuccess = true, isLoading = false)
                } else {
                    //_signUpState.value = SignUpState(isSuccess = false, errorMessage = it.message, isLoading = false)
                    mSignUpState = mSignUpState.copy(isSuccess = false, errorMessage = it.message, isLoading = false)
                }
            }
        }
    }

    fun confirmUser(confirmationCode: String) {
        if (confirmationCode.isEmpty()) return
        //_signUpState.value = SignUpState(isLoading = true, signUpSuccess = false, errorMessage = null)
        mSignUpState = mSignUpState.copy(isLoading = true, signUpSuccess = false, errorMessage = null)
        viewModelScope.launch {
            mStudentRepo.verifySignUppedUser(confirmationCode) {
                if (it.first) {
                    LoggerUtil.logMessage("User Confirmation Success In Run Blocking")
                    //_signUpState.value = SignUpState(isLoading = false, signUpSuccess = true, errorMessage = null)
                    mSignUpState = mSignUpState.copy(isLoading = false, signUpSuccess = true, errorMessage = null)
                } else {
                    LoggerUtil.logMessage("User Confirmation Failure In Run Blocking")
                    //_signUpState.value = SignUpState(errorMessage = it.second, isLoading = false)
                    mSignUpState = mSignUpState.copy(errorMessage = it.second, isLoading = false)
                }
            }
        }
    }

    fun onNavigateAway() {
        /*_signUpState.update {
            it.copy(isLoading = false, signUpSuccess = false, errorMessage = null)
        }*/
        mSignUpState = mSignUpState.copy(isLoading = false, signUpSuccess = false, errorMessage = null)
    }
}