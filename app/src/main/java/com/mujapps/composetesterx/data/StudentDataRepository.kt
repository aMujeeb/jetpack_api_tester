package com.mujapps.composetesterx.data

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.regions.Regions
import com.mujapps.composetesterx.models.Configuration
import com.mujapps.composetesterx.models.LoginStatus
import com.mujapps.composetesterx.models.MessageResponse
import com.mujapps.composetesterx.models.Student
import com.mujapps.composetesterx.utils.LoggerUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StudentDataRepository @Inject constructor(
    private val mStudentsApiService: StudentsDataApiService,
    private val mAppContext: Context
) {
    fun createStudent(student: Student): Flow<Resource<MessageResponse>> = flow {
        try {
            emit(Resource.Loading<MessageResponse>())
            val response =mStudentsApiService.publishStudent(student)
            emit(Resource.Success<MessageResponse>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<MessageResponse>(e.message))
        } catch (e: IOException) {
            emit(Resource.Error<MessageResponse>(e.message))
        }
    }

    fun deleteStudent(studentId : String) : Flow<Resource<MessageResponse>> = flow {
        try {
            emit(Resource.Loading<MessageResponse>())
            val response = mStudentsApiService.removeStudent(studentId)
            emit(Resource.Success<MessageResponse>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<MessageResponse>(e.message))
        } catch (e: IOException) {
            emit(Resource.Error<MessageResponse>(e.message))
        }
    }

    suspend fun getStudent(path: String?): Resource<Any> {
        val response = try {
            Resource.Loading(data = true)
            mStudentsApiService.getStudentDetails(path)
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString(), data = null)
        }
        return Resource.Success(data = response)
    }

    suspend fun getConfigs(): Flow<Resource<Configuration>> = flow {
        try {
            emit(Resource.Loading<Configuration>())
            val configs = mStudentsApiService.getConfigurations().getConfigurations()
            setConfigs(configs)
            emit(Resource.Success<Configuration>(configs))
        } catch (e: HttpException) {
            emit(Resource.Error<Configuration>(e.message))
        } catch (e: IOException) {
            emit(Resource.Error<Configuration>(e.message))
        }
    }

    private lateinit var mCognitoUserPool: CognitoUserPool
    private lateinit var mCognitoUserAttributes: CognitoUserAttributes
    private lateinit var mCognitoUser: CognitoUser
    private var mUserId = ""
    private var mUser: CognitoUser? = null

    private fun setConfigs(configs: Configuration) {
        mCognitoUserPool =
            CognitoUserPool(mAppContext, configs.mPoolId, configs.mClientId, configs.mClientSecret, Regions.AP_SOUTHEAST_2)
    }

    fun signUpUser(userEmail: String, password: String, onSignUp: (Resource<Any>) -> Unit) {
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
                    onSignUp(Resource.Error(data = null, message = exception?.localizedMessage?.lastIndexOf("(")
                        ?.let { exception.localizedMessage?.substring(0, it) }
                        ?: ""))
                }
            })
    }

    fun verifySignUppedUser(verifyCode: String, onConfirmed: (Pair<Boolean, String>) -> Unit) {
        //https://<your user pool domain>/confirmUser/?client_id=abcdefg12345678&user_name=emailtest&confirmation_code=123456
        mUser?.confirmSignUpInBackground(verifyCode, false, object : GenericHandler {
            override fun onSuccess() {
                LoggerUtil.logMessage("User Confirmation Success")
                onConfirmed(Pair(true, ""))
            }

            override fun onFailure(exception: Exception?) {
                LoggerUtil.logMessage("User Confirmation Failure :" + exception.toString())
                onConfirmed(Pair(false, exception?.message ?: ""))
            }
        })
    }

    fun loginUser(userEmail: String, password: String, onLoginRequest: (LoginStatus) -> Unit) {
        mCognitoUser = mCognitoUserPool.getUser(userEmail)
        mCognitoUser.getSessionInBackground(object : AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                /*LoggerUtil.logMessage("User Login Success")
                LoggerUtil.logMessage("User Login Access Token :" + userSession?.idToken?.jwtToken)
                LoggerUtil.logMessage("User Login Is Valid :" + userSession?.isValid)
                LoggerUtil.logMessage(("User Login Refresh Token :" + userSession?.refreshToken?.token) ?: "")
                LoggerUtil.logMessage("User Login Token Type :" + userSession?.accessToken?.jwtToken)*/
                onLoginRequest(
                    LoginStatus(
                        mLoginFailed = false,
                        mIsValid = userSession?.isValid ?: false,
                        mSessionToken = userSession?.idToken?.jwtToken ?: "",
                        mRefreshToken = userSession?.refreshToken?.token,
                        mUserId = userSession?.accessToken?.username ?: "",
                        mExpiredTime = userSession?.accessToken?.expiration
                    )
                )
            }

            override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation?, userId: String?) {
                val authenticationDetails = AuthenticationDetails(userEmail, password, null)
                authenticationContinuation?.setAuthenticationDetails(authenticationDetails)
                authenticationContinuation?.continueTask()
            }

            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {}

            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                LoggerUtil.logMessage("User Authentication Challenged :" + continuation.toString())
            }

            override fun onFailure(exception: Exception?) {
                LoggerUtil.logMessage("User Login Failure :$exception")
                onLoginRequest(LoginStatus(mLoginFailed = true, mError = exception?.message ?: ""))
            }
        })
    }

    fun getCurrentUser(isAlreadyLoggedIn: (Boolean) -> Unit) {
        mCognitoUser = mCognitoUserPool.currentUser
        mCognitoUser.getDetailsInBackground(object : GetDetailsHandler {
            override fun onSuccess(cognitoUserDetails: CognitoUserDetails?) {
                LoggerUtil.logMessage("Check For User Success :" + cognitoUserDetails?.attributes?.attributes.toString())
                isAlreadyLoggedIn(true)
            }

            override fun onFailure(exception: Exception?) {
                LoggerUtil.logMessage("Check For User Failed:$exception")
                isAlreadyLoggedIn(false)
            }
        })
    }

    fun getAuthenticatedUser(isAlreadyLoggedIn: (Boolean) -> Unit) {
        mCognitoUser = mCognitoUserPool.currentUser
        if (mCognitoUser.userId.isNullOrEmpty()) isAlreadyLoggedIn(false)
        else {
            mCognitoUser.getSessionInBackground(object : AuthenticationHandler {
                override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                    LoggerUtil.logMessage("User Is Valid :" + userSession?.isValid)
                    LoggerUtil.logMessage("User Is Valid Threshold:" + userSession?.isValidForThreshold)
                    isAlreadyLoggedIn(userSession?.isValid ?: false)
                }

                override fun getAuthenticationDetails(authenticationContinuation: AuthenticationContinuation?, userId: String?) {

                }

                override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {}

                override fun authenticationChallenge(continuation: ChallengeContinuation?) {}

                override fun onFailure(exception: java.lang.Exception?) {
                    isAlreadyLoggedIn(false)
                }
            })
        }
    }

    fun logOutUser(isLoggedOutSuccess: (Boolean) -> Unit) {
        mCognitoUser.globalSignOutInBackground(object : GenericHandler {
            override fun onSuccess() {
                isLoggedOutSuccess(true)
            }

            override fun onFailure(exception: java.lang.Exception?) {
                isLoggedOutSuccess(false)
            }
        })
    }
}