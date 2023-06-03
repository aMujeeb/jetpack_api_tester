package com.mujapps.composetesterx.screens.login

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.components.EmailInput
import com.mujapps.composetesterx.components.GeneralAlertDialog
import com.mujapps.composetesterx.components.GenericButton
import com.mujapps.composetesterx.components.MainHeader
import com.mujapps.composetesterx.components.GeneralTextInput
import com.mujapps.composetesterx.components.ShowAlertDialog
import com.mujapps.composetesterx.components.TextFieldCustom
import com.mujapps.composetesterx.navigation.TestAppScreens
import com.mujapps.composetesterx.utils.LoggerUtil

@Composable
fun LoginScreen(navController: NavController?) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainHeader(
                resourceId = R.string.login,
                modifier = Modifier.padding(top = 28.dp, bottom = 24.dp),
                cardElevation = 0.dp
            )
            LoginForm(navController)

            TextFieldCustom(
                labelText = stringResource(R.string.sign_up), fontSize = 16.sp, fontWeight = FontWeight.Normal, fontFamily = FontFamily(
                    Font(R.font.ubuntu_bold)
                ), color = Color.Blue, modifier = Modifier.padding(top = 16.dp)
            ) {
                navController?.navigate(TestAppScreens.SignUpScreen.name) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginForm(navController: NavController?) {
    val mLoginViewModel: LoginViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    //Email Related validations and states
    val emailValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isEmailValid = remember(emailValueState.value) {
        emailValueState.value.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(
            emailValueState.value.trim()
        ).matches()
    }

    //Password Related validations and states
    val passwordValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isPasswordValid = remember(passwordValueState.value) {
        passwordValueState.value.isNotEmpty()
    }

    //val mLoginState by mLoginViewModel.mLoginState.collectAsState()
    val mLoginState = mLoginViewModel.mLoginState

    val openErrorDialog = rememberSaveable { mutableStateOf(false) }
    val errorReported = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
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
                focusManager.moveFocus(FocusDirection.Down)
            }
        )

        GeneralTextInput(textState = passwordValueState, mModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            mImeAction = ImeAction.Done,
            mKeyBoardType = KeyboardType.Password,
            mOnAction = KeyboardActions {
                if (!isPasswordValid) return@KeyboardActions
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
            if (!(isEmailValid && isPasswordValid)) return@GenericButton
            keyboardController?.hide()
            LoggerUtil.logMessage("Button Clicked :" + emailValueState.value + "-" + passwordValueState.value)
            mLoginViewModel.loginUser(emailValueState.value, passwordValueState.value)
        }

        if (mLoginState.isLoading) {
            CircularProgressIndicator()
        } else if (mLoginState.isSuccess) {
            LoggerUtil.logMessage("Navigated to home")
            mLoginViewModel.onNavigateAway()
            navController?.navigate(TestAppScreens.HomeScreen.name) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        } else if (mLoginState.errorMessage != null) {
            ShowAlertDialog(messageBody = mLoginState.errorMessage ?: "", isShow = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginScreen(null)
}