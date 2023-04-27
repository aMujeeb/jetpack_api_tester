package com.mujapps.composetesterx.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mujapps.composetesterx.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = text_color
    ),
    h1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ubuntu_bold)),
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        color = text_color
    ),
    h2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = text_color
    ),
    h3 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = text_color
    ),
    h4 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = accent_color
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)