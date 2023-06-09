package com.mujapps.composetesterx.screens.home

import com.mujapps.composetesterx.models.Student

data class HomeViewState(
    val isLoading: Boolean = false, val isSuccess: Boolean = false,
    val errorMessage: String? = null, val data: List<Student>? = null, val isStudentDeleted: Boolean = false
)
