package com.example.composetutorial.data

data class UserState(
    val users: List<User> = emptyList(),
    val firstName: String = "",
    val photo: String = "",
)
