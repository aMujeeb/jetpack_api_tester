package com.mujapps.composetesterx.data

import android.content.SharedPreferences
import com.mujapps.composetesterx.utils.LoggerUtil
import com.mujapps.composetesterx.utils.StudentAppConfig
import javax.inject.Inject

class UserManager @Inject constructor(private val mSharedPreferences: SharedPreferences) {
    private var mIsAuthenticated = false

    fun setAuthenticated(authStatus: Boolean) {
        mIsAuthenticated = authStatus
    }

    //fun getAuthorization() = mSharedPreferences.getString(StudentAppConfig.LOGIN_ACCESS_TOKEN, "")//"allow"//if (mIsAuthenticated) "allow" else "deny"
    fun getAuthorization() : String? {
        LoggerUtil.logMessage("Token At user :" + mSharedPreferences.getString(StudentAppConfig.LOGIN_ACCESS_TOKEN, ""))
        return mSharedPreferences.getString(StudentAppConfig.LOGIN_ACCESS_TOKEN, "")
    }//"allow"//if (mIsAuthenticated) "allow" else "deny"
}