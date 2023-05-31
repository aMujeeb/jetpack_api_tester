package com.mujapps.composetesterx.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mujapps.composetesterx.R

@Composable
fun MainHeader(resourceId: Int, modifier: Modifier = Modifier, cardElevation: Dp = 0.dp) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 24.dp, end = 24.dp),
        elevation = cardElevation,
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = resourceId),
                modifier = modifier,
                style = MaterialTheme.typography.h1
            )
        }
    }
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
fun GeneralTextInput(
    mModifier: Modifier = Modifier,
    textState: MutableState<String>,
    mLabelId: String = "Password",
    mIsEnabled: Boolean = true,
    mImeAction: ImeAction,
    mKeyBoardType: KeyboardType,
    mOnAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        valueState = textState,
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
    fColor: Color = MaterialTheme.colors.surface,
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
                color = fColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

@Composable
fun GeneralAlertDialog(messageBody: String, mModifier: Modifier = Modifier, onButtonClick: (Boolean) -> Unit) {
    Surface(color = MaterialTheme.colors.background, modifier = mModifier) {
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

            TextFieldMediumBold(labelText = stringResource(id = R.string.alert), modifier = Modifier.padding(top = 12.dp)) {}
            TextFieldRegular(labelText = messageBody, modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)) {}

            GenericButton(
                labelText = stringResource(id = R.string.ok),
                isLoading = false,
                mModifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            ) {
                onButtonClick(false)
            }
        }
    }
}

@Composable
fun TextFieldSmall(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 8.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.ubuntu_regular))
        )
    )
}

@Composable
fun TextFieldSmallBold(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
        )
    )
}

@Composable
fun TextFieldSmallThin(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 8.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = FontFamily(Font(R.font.ubuntu_light))
        )
    )
}

@Composable
fun TextFieldRegular(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.ubuntu_regular))
        )
    )
}

@Composable
fun TextFieldRegularBold(
    labelText: String,
    modifier: Modifier = Modifier,
    fColor: Color = MaterialTheme.colors.onSurface,
    onButtonClick: () -> Unit = {}
) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
        )
    )
}

@Composable
fun TextFieldSRegularThin(
    labelText: String,
    modifier: Modifier = Modifier,
    fColor: Color = MaterialTheme.colors.onSurface,
    onButtonClick: () -> Unit = {}
) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = FontFamily(Font(R.font.ubuntu_light))
        )
    )
}

@Composable
fun TextFieldMedium(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.ubuntu_regular))
        )
    )
}

@Composable
fun TextFieldMediumBold(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
        )
    )
}

@Composable
fun TextFieldSMediumThin(
    labelText: String,
    modifier: Modifier = Modifier,
    fColor: Color = MaterialTheme.colors.onSurface,
    onButtonClick: () -> Unit
) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = FontFamily(Font(R.font.ubuntu_light))
        )
    )
}

@Composable
fun TextFieldLarge(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.ubuntu_regular))
        )
    )
}

@Composable
fun TextFieldLargeBold(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
        )
    )
}

@Composable
fun TextFieldLargeThin(labelText: String, modifier: Modifier = Modifier, fColor: Color = MaterialTheme.colors.onSurface, onButtonClick: () -> Unit) {
    Text(
        text = labelText,
        modifier = modifier,
        style = TextStyle(
            color = fColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = FontFamily(Font(R.font.ubuntu_light))
        )
    )
}

@Composable
fun TextFieldCustom(
    labelText: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    color: Color,
    textDecoration: TextDecoration = TextDecoration.None,
    onClick: (Int) -> Unit = {}
) {
    ClickableText(
        text = AnnotatedString(labelText),
        modifier = modifier,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration
        ),
        onClick = onClick,
    )
}

@Composable
fun ShowAlertDialog(isShow: Boolean, title: String = stringResource(id = R.string.alert), messageBody: String) {
    val openDialog = remember { mutableStateOf(isShow) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                TextFieldMediumBold(labelText = title, modifier = Modifier.padding(top = 12.dp)) {}
            },
            text = {
                TextFieldMedium(labelText = messageBody, modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp)) {}
            },
            confirmButton = {},
            dismissButton = {
                GenericButton(
                    labelText = stringResource(id = R.string.ok),
                    isLoading = false,
                    mModifier = Modifier
                        .padding(bottom = 16.dp, top = 16.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth()
                ) {
                    openDialog.value = false
                }
            }
        )
    }
}
