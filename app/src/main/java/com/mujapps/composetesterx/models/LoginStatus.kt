package com.mujapps.composetesterx.models

import java.util.Date

data class LoginStatus(
    val mIsValid: Boolean = false,
    val mSessionToken: String? = null,
    val mRefreshToken: String? = null,
    val mUserId: String? = null,
    val mError: String? = null,
    val mLoginFailed: Boolean = false,
    val mExpiredTime: Date? = null
)
