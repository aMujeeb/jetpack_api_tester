package com.mujapps.composetesterx.data

import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.regions.Region
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.models.Student
import com.mujapps.composetesterx.utils.LoggerUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class StudentDataRepository @Inject constructor(
    private val mStudentsApiService: StudentsDataApiService,
    private val mAppContext: Context
) {
    suspend fun createStudent(student: Student): Any {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.publishStudent(student)
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }

    suspend fun deleteStudent(): Any {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.removeStudent()
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }

    suspend fun getStudent(path: String?): Any {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.getStudentDetails(path)
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }

    private lateinit var mCognitoUserPool: CognitoUserPool
    private lateinit var mCognitoAwsConfiguration: AWSConfiguration
    private lateinit var mCognitoUserAttributes: CognitoUserAttributes
    private var mRegion: Region = Region.getRegion("ap-southeast-2")
    private var mUserId = ""
    private var mUser: CognitoUser? = null

    suspend fun signUpUser(userEmail: String, password: String, onSignUp: (Resource<Any>) -> Unit) {
        mCognitoAwsConfiguration = AWSConfiguration(mAppContext, R.raw.aws_config)
        mCognitoUserPool = CognitoUserPool(mAppContext, mCognitoAwsConfiguration)
        mCognitoUserAttributes = CognitoUserAttributes()
        mCognitoUserAttributes.addAttribute("email", userEmail)
        mCognitoUserPool.signUpInBackground(
            userEmail.substring(0, userEmail.lastIndexOf("@")),
            password,
            mCognitoUserAttributes,
            null,
            object : SignUpHandler {
                override fun onSuccess(
                    user: CognitoUser?,
                    signUpConfirmationState: Boolean,
                    cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?
                ) {
                    mUserId = user?.userId ?: ""
                    mUser = user
                    LoggerUtil.logMessage("Sign Up Success :" + user.toString())
                    LoggerUtil.logMessage("Sign Up Success :" + cognitoUserCodeDeliveryDetails.toString())

                    onSignUp(Resource.Success(data = mUserId))
                }

                override fun onFailure(exception: Exception?) {
                    LoggerUtil.logMessage("Sign Up Failure :" + exception?.message)
                    onSignUp(Resource.Error(data = null, message = exception?.message ?: ""))
                }
            })
    }

    suspend fun verifySignUppedUser(verifyCode: String) {
        //https://<your user pool domain>/confirmUser/?client_id=abcdefg12345678&user_name=emailtest&confirmation_code=123456
        mUser?.confirmSignUp(verifyCode, false, object : GenericHandler {
            override fun onSuccess() {
                LoggerUtil.logMessage("User Confirmation Success")
            }

            override fun onFailure(exception: Exception?) {
                LoggerUtil.logMessage("User Confirmation Failure :" + exception?.message)
            }
        })
    }
}