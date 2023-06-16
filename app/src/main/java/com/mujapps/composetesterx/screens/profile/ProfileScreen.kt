package com.mujapps.composetesterx.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController?, mProfilerViewModel: ProfileViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {

    }
}