package com.mujapps.composetesterx.screens.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mujapps.composetesterx.data.Resource
import com.mujapps.composetesterx.data.StudentDataRepository
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class SignUpViewModel @Inject constructor(private val mStudentRepo: StudentDataRepository) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val mSignUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    private val _signConfirmationState = MutableStateFlow(UserConfirmationState())
    val mSignConfirmationState = _signConfirmationState.asStateFlow()

    private val mCoroutineScope = CoroutineScope(Dispatchers.IO)

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
                    _signUpState.value = SignUpState(isSuccess = false, errorMessage = it.message, isLoading = false)
                }
            }
        }
    }

    fun confirmUser(confirmationCode: String) {
        if (confirmationCode.isEmpty()) return
        _signUpState.value = SignUpState(isLoading = true, signUpSuccess = false, errorMessage = null)

        mCoroutineScope.launch {
            var result = Pair(false, "")
            mStudentRepo.verifySignUppedUser(confirmationCode) {
                result = it
            }

            withContext(Dispatchers.Main) {
                if (result.first) {
                    LoggerUtil.logMessage("User Confirmation Success In Run Blocking")
                    _signUpState.value = SignUpState(isLoading = false, signUpSuccess = true, errorMessage = null)
                } else {
                    LoggerUtil.logMessage("User Confirmation Failure In Run Blocking")
                    _signUpState.value = SignUpState(errorMessage = result.second, isLoading = false)
                }
            }
        }
    }
}