package com.mujapps.composetesterx.navigation

enum class TestAppScreens {
    SplashScreen,
    LoginScreen,
    SignUpScreen,
    HomeScreen,
    AddStudentScreen,
    ProfileScreen;

    companion object {
        fun fromRoute(route: String): TestAppScreens = when (route.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            SignUpScreen.name -> SignUpScreen
            AddStudentScreen.name -> AddStudentScreen
            ProfileScreen.name -> ProfileScreen
            null -> LoginScreen
            else -> throw IllegalArgumentException("Route Exception")
        }
    }
}