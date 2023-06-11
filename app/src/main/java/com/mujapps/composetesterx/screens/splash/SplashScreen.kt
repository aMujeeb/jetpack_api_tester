package com.mujapps.composetesterx.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.mujapps.composetesterx.navigation.TestAppScreens
import com.mujapps.composetesterx.ui.theme.ComposeTesterXTheme
import com.mujapps.composetesterx.utils.LoggerUtil
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController?, mSplashVieModel: SplashScreenViewModel = hiltViewModel()) {
    //Text(text = "Screen Splash")

    val mScale = remember {
        Animatable(0f)
    }

    //Launching
    LaunchedEffect(key1 = true) {
        mScale.animateTo(targetValue = 0.9f, animationSpec = tween(durationMillis = 800, easing = {
            OvershootInterpolator(8f).getInterpolation(it)
        }))
        delay(3000L)
        mSplashVieModel.requestConfigurations()
        //mSplashVieModel.checkIsUserLoggedIn()
        LoggerUtil.logMessage("Splash Screen Mechanism")
    }

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp)
            .scale(mScale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Screen Splash", style = MaterialTheme.typography.body1, fontSize = 20.sp)
        }

        if (mSplashVieModel.mSplashState.isUserExists == false) {
            //Navigate to Login screen -> This will facilitate back navigation not come back to
            mSplashVieModel.onNavigateAway()
            LoggerUtil.logMessage("Navigate to login")
            navController?.navigate(TestAppScreens.LoginScreen.name) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        } else if (mSplashVieModel.mSplashState.isUserExists == true) {
            //Navigate to Login screen -> This will facilitate back navigation not come back to
            mSplashVieModel.onNavigateAway()
            LoggerUtil.logMessage("Navigate to Home")
            navController?.navigate(TestAppScreens.HomeScreen.name) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
}