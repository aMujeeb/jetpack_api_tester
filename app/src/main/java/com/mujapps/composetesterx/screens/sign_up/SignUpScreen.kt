package com.mujapps.composetesterx.screens.sign_up

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.components.EmailInput
import com.mujapps.composetesterx.components.GenericButton
import com.mujapps.composetesterx.components.MainHeader
import com.mujapps.composetesterx.components.PasswordInput
import com.mujapps.composetesterx.components.TextFieldMediumBold
import com.mujapps.composetesterx.components.TextFieldRegular
import com.mujapps.composetesterx.utils.LoggerUtil

@Composable
fun SignUpScreen(navController: NavController?) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            MainHeader(
                resourceId = R.string.sign_up,
                modifier = Modifier.padding(top = 28.dp, bottom = 24.dp),
                cardElevation = 0.dp
            )

            SignUpForm()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun SignUpForm() {

    val mSignUpViewModel: SignUpViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val emailValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isEmailValid = remember(emailValueState.value) {
        emailValueState.value.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(
            emailValueState.value.trim()
        ).matches()
    }

    val passWordValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isPasswordValid = remember(passWordValueState.value) {
        passWordValueState.value.isNotEmpty()
    }

    val confirmPassWordValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isConfirmPasswordValid = remember(confirmPassWordValueState.value) {
        confirmPassWordValueState.value.isNotEmpty() && (passWordValueState.value.trim() == confirmPassWordValueState.value.trim())
    }

    val mSignUpState by mSignUpViewModel.mSignUpState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState()),
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

        PasswordInput(passwordState = passWordValueState, mModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            mImeAction = ImeAction.Next,
            mKeyBoardType = KeyboardType.Password,
            mLabelId = stringResource(id = R.string.password),
            mOnAction = KeyboardActions {
                if (!isPasswordValid) return@KeyboardActions
                focusManager.moveFocus(FocusDirection.Down)
            })

        PasswordInput(passwordState = confirmPassWordValueState, mModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            mImeAction = ImeAction.Done,
            mKeyBoardType = KeyboardType.Password,
            mLabelId = stringResource(id = R.string.confirm_password),
            mOnAction = KeyboardActions {
                if (!isConfirmPasswordValid) return@KeyboardActions
                keyboardController?.hide()
            })

        GenericButton(
            labelText = stringResource(id = R.string.sign_up),
            isLoading = false,
            mModifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        ) {
            if (!isConfirmPasswordValid) return@GenericButton
            LoggerUtil.logMessage("Button Clicked :" + emailValueState.value + " - " + passWordValueState.value + " - " + confirmPassWordValueState.value)
            mSignUpViewModel.signUpUser(emailValueState.value, passWordValueState.value)
            confirmPassWordValueState.value = ""
        }

        if (mSignUpState.isLoading) {
            CircularProgressIndicator()
        }

        if (mSignUpState.errorMessage?.isEmpty()?.not() == true) {
            GeneralAlertDialog(mSignUpState.errorMessage!!)
        }
    }
}

@Composable
fun GeneralAlertDialog(messageBody: String) {
    Surface(color = MaterialTheme.colors.background) {
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
            val isOpenDialog = remember { mutableStateOf(false) }

            TextFieldMediumBold(labelText = stringResource(id = R.string.alert)) {}
            TextFieldRegular(labelText = messageBody, modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {}
        }
    }
}
