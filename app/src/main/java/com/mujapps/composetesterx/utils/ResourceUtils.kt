package com.mujapps.composetesterx.utils

import androidx.compose.ui.graphics.Color

fun getHexDecimalColor(colorCode: String) =
    Color(colorCode.removePrefix("#").toLong(16) or 0x00000000FF000000)