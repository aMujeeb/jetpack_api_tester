package com.mujapps.composetesterx.screens.add_student

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.components.GeneralTextInput
import com.mujapps.composetesterx.components.GenericButton
import com.mujapps.composetesterx.components.ShowAlertDialog
import com.mujapps.composetesterx.components.TextFieldMediumBold
import com.mujapps.composetesterx.navigation.TestAppScreens
import com.mujapps.composetesterx.screens.home.StudentList
import com.mujapps.composetesterx.utils.LoggerUtil

@Composable
fun AddStudentScreen(navController: NavController?, mAddStudentViewModel: AddStudentViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BackHandler() {
                mAddStudentViewModel.onNavigateAway()
                navController?.navigate(TestAppScreens.HomeScreen.name)
            }

            TextFieldMediumBold(labelText = stringResource(id = R.string.add_student), modifier = Modifier.padding(top = 24.dp))
            AddStudentForm(mAddStudentViewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddStudentForm(mAddStudentViewModel: AddStudentViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val mAddStudentState = mAddStudentViewModel.mAddStudentViewState

    val ageValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isAgeValid = remember(ageValueState.value) {
        ageValueState.value.isNotEmpty() && (ageValueState.value).toInt() > 0
    }

    val heightValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isHeightValid = remember(heightValueState.value) {
        heightValueState.value.isNotEmpty() && (heightValueState.value).toFloat() > 0.0
    }

    val incomeValueState = rememberSaveable {
        mutableStateOf("")
    }

    val isIncomeValid = remember(incomeValueState.value) {
        incomeValueState.value.isNotEmpty() && (incomeValueState.value).toFloat() > 0.0
    }

    Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {

        GeneralTextInput(textState = ageValueState, mModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            mImeAction = ImeAction.Next,
            mLabelId = stringResource(id = R.string.age),
            mKeyBoardType = KeyboardType.Number,
            mOnAction = KeyboardActions {
                if (!isAgeValid) return@KeyboardActions
                focusManager.moveFocus(FocusDirection.Down)
            })

        GeneralTextInput(textState = heightValueState, mModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            mImeAction = ImeAction.Next,
            mLabelId = stringResource(id = R.string.height),
            mKeyBoardType = KeyboardType.Decimal,
            mOnAction = KeyboardActions {
                if (!isHeightValid) return@KeyboardActions
                focusManager.moveFocus(FocusDirection.Down)
            })

        GeneralTextInput(textState = incomeValueState, mModifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            mImeAction = ImeAction.Done,
            mLabelId = stringResource(id = R.string.income),
            mKeyBoardType = KeyboardType.Decimal,
            mOnAction = KeyboardActions {
                if (!isIncomeValid) return@KeyboardActions
                focusManager.moveFocus(FocusDirection.Enter)
                keyboardController?.hide()
            })

        GenericButton(
            labelText = "Click to Add", isLoading = false, mModifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        ) {
            if (!(isAgeValid || isHeightValid || isIncomeValid)) return@GenericButton
            keyboardController?.hide()
            mAddStudentViewModel.addNewStudentDetails(ageValueState.value.toInt(), heightValueState.value.toFloat(), incomeValueState.value.toFloat())
            ageValueState.value = ""
            heightValueState.value = ""
            incomeValueState.value = ""
        }

        if (mAddStudentState.isLoading) {
            CircularProgressIndicator()
        } else if (mAddStudentState.isSuccess) {
            ShowAlertDialog(messageBody = stringResource(id = R.string.add_success), isShow = true)
        } else if (mAddStudentState.errorMessage.isNullOrEmpty().not()) {
            ShowAlertDialog(messageBody = mAddStudentState.errorMessage ?: "", isShow = true)
        }
        LoggerUtil.logMessage("Adding Student View")
    }
}