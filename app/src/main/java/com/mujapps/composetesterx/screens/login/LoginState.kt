package com.mujapps.composetesterx.screens.login

data class LoginState(
    val isLoading: Boolean = false, val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
