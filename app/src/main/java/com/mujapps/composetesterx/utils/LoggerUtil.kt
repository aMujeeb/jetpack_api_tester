package com.mujapps.composetesterx.utils

import android.util.Log
import com.mujapps.composetesterx.utils.StudentAppConfig.LOG_TAG

object LoggerUtil {
    fun logMessage(message: String) {
        Log.d(LOG_TAG, message)
    }
}