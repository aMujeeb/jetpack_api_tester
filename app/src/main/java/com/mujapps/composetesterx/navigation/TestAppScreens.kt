package com.mujapps.composetesterx.navigation

enum class TestAppScreens {
    SplashScreen,
    LoginScreen,
    SignUpScreen,
    HomeScreen;

    companion object {
        fun fromRoute(route: String): TestAppScreens = when (route.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            SignUpScreen.name -> SignUpScreen
            null -> LoginScreen
            else -> throw IllegalArgumentException("Route Exception")
        }
    }
}