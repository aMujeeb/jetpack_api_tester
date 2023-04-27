package com.mujapps.composetesterx.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mujapps.composetesterx.screens.LoginScreen
import com.mujapps.composetesterx.screens.SplashScreen

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
    }
}