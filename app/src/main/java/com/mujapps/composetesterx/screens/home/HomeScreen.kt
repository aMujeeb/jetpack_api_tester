package com.mujapps.composetesterx.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mujapps.composetesterx.R
import com.mujapps.composetesterx.components.GenericButton
import com.mujapps.composetesterx.components.ShowAlertDialog
import com.mujapps.composetesterx.components.StudentListItem
import com.mujapps.composetesterx.components.TextFieldMediumBold
import com.mujapps.composetesterx.models.Student
import com.mujapps.composetesterx.navigation.TestAppScreens

@Composable
fun HomeScreen(navController: NavHostController?, mHomeViewModel: HomeScreenViewModel = hiltViewModel()) {

    val mHomeViewState = mHomeViewModel.mHomeViewState

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFieldMediumBold(labelText = stringResource(id = R.string.student_details), modifier = Modifier.padding(top = 24.dp))

            // Student List Area
            if (mHomeViewState.isLoading) {
                CircularProgressIndicator()
            } else if (mHomeViewState.isSuccess) {
                StudentList(mHomeViewState.data ?: emptyList(), mHomeViewModel, navController)
            } else if (mHomeViewState.errorMessage.isNullOrEmpty().not()) {
                ShowAlertDialog(messageBody = mHomeViewState.errorMessage ?: "", isShow = true)
            } else if (mHomeViewState.isStudentDeleted) {
                ShowAlertDialog(messageBody = stringResource(id = R.string.deletion_success), isShow = true) {
                    mHomeViewModel.requestStudents()
                }
            }

            GenericButton(
                labelText = stringResource(id = R.string.click_to_add_student),
                isLoading = false,
                mModifier = Modifier.padding(top = 24.dp)
            ) {
                mHomeViewModel.onNavigateAway()
                navController?.navigate(TestAppScreens.AddStudentScreen.name) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun StudentList(studentsList: List<Student>, mHomeViewModel: HomeScreenViewModel, navController: NavHostController?) {
    val mContext = LocalContext.current
    if (studentsList.isEmpty()) {
        Surface(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                TextFieldMediumBold(labelText = stringResource(id = R.string.no_students_message), fColor = MaterialTheme.colors.secondary)
            }
        }
    } else {
        for (student in studentsList) {
            StudentListItem(student) {
                //On Click to show toast
                //showToast(mContext, it)
                //mHomeViewModel.onDeleteStudent(student.stId ?: "")
                mHomeViewModel.onNavigateAway()
                navController?.navigate(TestAppScreens.ProfileScreen.name + "/$it") {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
                //navController?.navigate(TestAppScreens.ProfileScreen.name + "/$it")
            }
        }
    }
}