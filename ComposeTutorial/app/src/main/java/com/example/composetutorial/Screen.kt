package com.example.composetutorial

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object ChatScreen : Screen("chat_screen")
    object ProfileScreen : Screen("profile_screen")
}