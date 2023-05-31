package com.mujapps.composetesterx.screens.sign_up

data class SignUpState(
    val isLoading: Boolean = false,
    val error: Int? = 0,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val isRegistered: Boolean = false,
    val navigateToRegister: Boolean = false,
    val userNameEmpty: Boolean = false,
    val passwordEmpty: Boolean = false,
    val signUpSuccess: Boolean = false
)
