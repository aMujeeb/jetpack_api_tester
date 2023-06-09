package com.mujapps.composetesterx.screens.sign_up

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.components.EmailInput
import com.mujapps.composetesterx.components.GenericButton
import com.mujapps.composetesterx.components.MainHeader
import com.mujapps.composetesterx.components.GeneralTextInput
import com.mujapps.composetesterx.components.ShowAlertDialog
import com.mujapps.composetesterx.navigation.TestAppScreens
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

            SignUpForm(navController)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpForm(navController: NavController?) {

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

    val confirmationValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isConfirmationValueValid = remember(confirmationValueState.value) {
        confirmationValueState.value.isNotEmpty() && (confirmationValueState.value.length == 6)
    }

    //val mSignUpState by mSignUpViewModel.mSignUpState.collectAsStateWithLifecycle()
    val mSignUpState = mSignUpViewModel.mSignUpState

    var mIsSignUpState by rememberSaveable {
        mutableStateOf(true)
    }

    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        if (mIsSignUpState) {
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

            GeneralTextInput(textState = passWordValueState, mModifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 24.dp, end = 24.dp),
                mImeAction = ImeAction.Next,
                mKeyBoardType = KeyboardType.Password,
                mLabelId = stringResource(id = R.string.password),
                mVisualTransformation = PasswordVisualTransformation(),
                mOnAction = KeyboardActions {
                    if (!isPasswordValid) return@KeyboardActions
                    focusManager.moveFocus(FocusDirection.Down)
                })

            GeneralTextInput(textState = confirmPassWordValueState, mModifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 24.dp, end = 24.dp),
                mImeAction = ImeAction.Done,
                mKeyBoardType = KeyboardType.Password,
                mVisualTransformation = PasswordVisualTransformation(),
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
                keyboardController?.hide()
            }

            if (mSignUpState.isLoading) {
                CircularProgressIndicator()
            } else if (mSignUpState.errorMessage?.isEmpty()?.not() == true) {
                //GeneralAlertDialog(mSignUpState.errorMessage!!, mModifier = Modifier.padding(top = 24.dp))r
                ShowAlertDialog(isShow = true, messageBody = mSignUpState.errorMessage ?: "", title = stringResource(id = R.string.error))
            } else if (mSignUpState.isSuccess) {
                mIsSignUpState = false
            }
        } else {
            GeneralTextInput(textState = confirmationValueState, mModifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 24.dp, end = 24.dp),
                mImeAction = ImeAction.Done,
                mKeyBoardType = KeyboardType.Number,
                mLabelId = stringResource(id = R.string.confirmation_code),
                mOnAction = KeyboardActions {
                    if (!isConfirmationValueValid) return@KeyboardActions
                    keyboardController?.hide()
                })

            GenericButton(
                labelText = stringResource(id = R.string.confirmation),
                isLoading = false,
                mModifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            ) {
                if (!isConfirmationValueValid) return@GenericButton
                mSignUpViewModel.confirmUser(confirmationValueState.value)
                confirmationValueState.value = ""
                keyboardController?.hide()
            }

            if (mSignUpState.isLoading) {
                CircularProgressIndicator()
            } else if (mSignUpState.signUpSuccess) {
                LoggerUtil.logMessage("Hit SignUpSuccess")
                //Toast.makeText(mContext, stringResource(id = R.string.confirmation_success), Toast.LENGTH_SHORT).show()
                mSignUpViewModel.onNavigateAway()
                navController?.navigate(TestAppScreens.LoginScreen.name) {
                    popUpTo(navController.graph.id) {
                        inclusive = false
                    }
                }
            }
        }
    }
}


