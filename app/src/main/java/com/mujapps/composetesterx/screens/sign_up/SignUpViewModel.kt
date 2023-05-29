package com.mujapps.composetesterx.screens.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val mStudentRepo: StudentDataRepository) : ViewModel() {

    private var _signUpState = MutableStateFlow(SignUpState())
    var mSignUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()


    fun signUpUser(userEmail: String, password: String) {
        if (userEmail.isEmpty() || password.isEmpty()) return
        LoggerUtil.logMessage("Button Clicked View Model:$userEmail : $password")
        _signUpState.value = SignUpState(isLoading = true)
        viewModelScope.launch {
            mStudentRepo.signUpUser(userEmail, password) {
                if (it.data != null) {
                    //User SignUp Success
                    _signUpState.value = SignUpState(isSuccess = true, isLoading = false)
                } else {
                    _signUpState.value = SignUpState(isSuccess = true, errorMessage = it.message, isLoading = false)
                }
            }
        }
    }
}