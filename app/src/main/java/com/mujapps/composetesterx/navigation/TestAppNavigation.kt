package com.mujapps.composetesterx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mujapps.composetesterx.screens.login.LoginScreen
import com.mujapps.composetesterx.screens.splash.SplashScreen
import com.mujapps.composetesterx.screens.home.HomeScreen
import com.mujapps.composetesterx.screens.sign_up.SignUpScreen

@Composable
fun TestAppNavigation() {
    val mNavController = rememberNavController()
    NavHost(navController = mNavController, startDestination = TestAppScreens.SplashScreen.name) {
        composable(TestAppScreens.SplashScreen.name) {
            SplashScreen(navController = mNavController)
        }
        composable(TestAppScreens.LoginScreen.name) {
            LoginScreen(navController = mNavController)
        }
        composable(TestAppScreens.SignUpScreen.name) {
            SignUpScreen(navController = mNavController)
        }
        composable(TestAppScreens.HomeScreen.name) {
            HomeScreen(navController = mNavController)
        }
    }
}