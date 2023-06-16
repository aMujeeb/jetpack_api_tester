package com.mujapps.composetesterx.screens.profile

import androidx.lifecycle.ViewModel
import com.mujapps.composetesterx.data.StudentDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mStudentRepo: StudentDataRepository
) : ViewModel() {
}