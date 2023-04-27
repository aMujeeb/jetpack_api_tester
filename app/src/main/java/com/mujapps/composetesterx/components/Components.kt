package com.mujapps.composetesterx.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mujapps.composetesterx.R

@Composable
fun LoginHeader(resourceId: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = resourceId),
        modifier = modifier,
        style = MaterialTheme.typography.h1
    )
}

@Composable
fun EmailInput(
    mModifier: Modifier = Modifier,
    emailState: MutableState<String>,
    mLabelId: String = "Email",
    mIsEnabled: Boolean = true,
    mImeAction: ImeAction = ImeAction.Next,
    mOnAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(valueState = emailState, labelId = mLabelId, modifier = mModifier, isEnabled = mIsEnabled, mImeAction = mImeAction, onAction = mOnAction)
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    isEnabled: Boolean = true,
    isSingleLine: Boolean = true,
    mKeyBoardType: KeyboardType = KeyboardType.Text,
    mImeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(value = valueState.value, onValueChange = {
        valueState.value = it
    },
        modifier = modifier,
        enabled = isEnabled,
        textStyle = MaterialTheme.typography.body1,
        keyboardOptions = KeyboardOptions(keyboardType = mKeyBoardType, imeAction = mImeAction),
        keyboardActions = onAction,
        label = {
            Text(text = labelId, style = MaterialTheme.typography.h4)
        }
    )
}