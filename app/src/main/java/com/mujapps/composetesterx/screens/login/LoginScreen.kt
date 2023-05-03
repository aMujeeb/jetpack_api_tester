package com.mujapps.composetesterx.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.components.EmailInput
import com.mujapps.composetesterx.components.GenericButton
import com.mujapps.composetesterx.components.LoginHeader
import com.mujapps.composetesterx.components.PasswordInput
import com.mujapps.composetesterx.screens.login.LoginViewModel
import com.mujapps.composetesterx.utils.LoggerUtil

@Composable
fun LoginScreen(navController: NavController?) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader(resourceId = R.string.login, modifier = Modifier.padding(top = 24.dp))
            LoginForm()

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginForm() {
    val mLoginViewModel: LoginViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    //Email Related validations and states
    val emailValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isEmailValid = remember(emailValueState.value) {
        emailValueState.value.trim().isNotEmpty()
    }

    //Password Related validations and states
    val passwordValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isPasswordValid = remember(passwordValueState.value) {
        passwordValueState.value.trim().isNotEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        EmailInput(
            emailState = emailValueState,
            mModifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            mOnAction = KeyboardActions {
                if (!isEmailValid) return@KeyboardActions
                //mLoginViewModel.requestStudentApi()
                //emailValueState.value = ""
                //keyboardController?.hide()
                focusManager.moveFocus(FocusDirection.Down)
            }
        )

        PasswordInput(passwordState = passwordValueState, mModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            mImeAction = ImeAction.Done,
            mKeyBoardType = KeyboardType.Password,
            mOnAction = KeyboardActions {
                if (!isPasswordValid) return@KeyboardActions
                mLoginViewModel.requestStudentApi()
                emailValueState.value = ""
                passwordValueState.value = ""
                keyboardController?.hide()
            })

        GenericButton(
            labelText = "Login",
            isLoading = false,
            mModifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        ) {
            LoggerUtil.logMessage("Button Clicked")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginScreen(null)
}