package com.mujapps.composetesterx.data

import javax.inject.Inject

class UserManager @Inject constructor() {
    private var mIsAuthenticated = false

    fun setAuthenticated(authStatus: Boolean) {
        mIsAuthenticated = authStatus
    }

    fun getAuthorization() = "allow"//if (mIsAuthenticated) "allow" else "deny"
}