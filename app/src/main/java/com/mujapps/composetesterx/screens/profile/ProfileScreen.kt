package com.mujapps.composetesterx.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.components.TextFieldMedium

@Composable
fun ProfileScreen(navController: NavController?, mProfilerViewModel: ProfileViewModel = hiltViewModel(), userId: String) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

            val isLoadingState = rememberSaveable {
                mutableStateOf(true)
            }
            if (isLoadingState.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextFieldMedium(labelText = stringResource(id = R.string.age), modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.width(40.dp))
                    TextFieldMedium(labelText = "--", modifier = Modifier.padding(4.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextFieldMedium(labelText = stringResource(id = R.string.height), modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.width(40.dp))
                    TextFieldMedium(labelText = "--", modifier = Modifier.padding(4.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextFieldMedium(labelText = stringResource(id = R.string.income), modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.width(40.dp))
                    TextFieldMedium(labelText = "--", modifier = Modifier.padding(4.dp))
                }

                CircularProgressIndicator()
            } else {

            }
            mProfilerViewModel.requestStudentData(userId)
        }
    }
}