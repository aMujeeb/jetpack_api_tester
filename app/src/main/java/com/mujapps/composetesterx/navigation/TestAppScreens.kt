package com.mujapps.composetesterx.navigation

enum class TestAppScreens {
    SplashScreen,
    LoginScreen,
    SignUpScreen,
    HomeScreen,
    AddStudentScreen;

    companion object {
        fun fromRoute(route: String): TestAppScreens = when (route.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            SignUpScreen.name -> SignUpScreen
            AddStudentScreen.name -> AddStudentScreen
            null -> LoginScreen
            else -> throw IllegalArgumentException("Route Exception")
        }
    }
}