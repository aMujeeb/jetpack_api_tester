package com.mujapps.composetesterx.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    mKeyBoardType: KeyboardType = KeyboardType.Email,
    mOnAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        valueState = emailState,
        labelId = mLabelId,
        modifier = mModifier,
        isEnabled = mIsEnabled,
        mImeAction = mImeAction,
        onAction = mOnAction, keyBoardType = mKeyBoardType,
        mVisualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordInput(
    mModifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    mLabelId: String = "Password",
    mIsEnabled: Boolean = true,
    mImeAction: ImeAction,
    mKeyBoardType: KeyboardType,
    mOnAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        valueState = passwordState,
        labelId = mLabelId,
        modifier = mModifier,
        isEnabled = mIsEnabled,
        mImeAction = mImeAction,
        onAction = mOnAction, keyBoardType = mKeyBoardType,
        mVisualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    isEnabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyBoardType: KeyboardType,
    mVisualTransformation: VisualTransformation,
    mImeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value, onValueChange = {
            valueState.value = it
        },
        modifier = modifier,
        enabled = isEnabled,
        textStyle = MaterialTheme.typography.body1,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = mImeAction),
        keyboardActions = onAction,
        label = {
            Text(text = labelId, style = MaterialTheme.typography.h4)
        },
        visualTransformation = mVisualTransformation
    )
}

@Composable
fun GenericButton(
    labelText: String,
    isLoading: Boolean,
    mModifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Button(
        onClick = onButtonClick,
        modifier = mModifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
    ) {
        Text(
            text = labelText,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                color = MaterialTheme.colors.surface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}