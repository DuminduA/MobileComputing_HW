package com.example.composetutorial

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composetutorial.data.UserEvent
import com.example.composetutorial.data.UserState
import com.example.composetutorial.data.UserViewModel
import com.example.composetutorial.notifications.SensorNotificationService

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    state: UserState,
    onEvent: (UserEvent) -> Unit,
    context: Context
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, state, onEvent, SensorNotificationService(context))
        }
        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController = navController, state, onEvent)
        }
        composable(route = Screen.ProfileScreen.route) {
            MeScreen(navController = navController, state, onEvent, context)
        }
    }
}