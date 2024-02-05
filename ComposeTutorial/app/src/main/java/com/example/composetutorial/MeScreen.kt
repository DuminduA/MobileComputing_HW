package com.example.composetutorial

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.composetutorial.data.UserEvent
import com.example.composetutorial.data.UserState
import com.example.composetutorial.data.UserViewModel

@Composable
fun MeScreen(
    navController: NavHostController,
    state: UserState,
    onEvent: (UserEvent) -> Unit
) {
    var selectedUri by remember {
        mutableStateOf<Uri?>(value=null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->
            selectedUri = uri
            onEvent(UserEvent.SetPhoto(uri.toString()))
        }
    )

    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Hello ${state.firstName}",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier
            .width(8.dp)
            .height(50.dp))
        AsyncImage(
            model = state.photo,
            contentDescription = "Contact profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                // Set image size to 40 dp
                .size(200.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
        )
        Spacer(modifier = Modifier
            .width(8.dp)
            .height(50.dp))
        TextField(
            value = state.firstName,
            onValueChange = {
                onEvent(UserEvent.SetFirstName(it))
            },
            label = { Text("Enter your first name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier
            .width(8.dp)
            .height(10.dp))
        Button(onClick = {
            onEvent(UserEvent.SaveUser)
        }) {
            Text(text = "Save")
        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row {
            Button(
                onClick = { navController.popBackStack()},
                shape = RoundedCornerShape(0)
            ) {
                Text(text = "Home")
            }
            Button(
                onClick = { navController.navigate(route = Screen.ChatScreen.route)},
                shape = RoundedCornerShape(0)

            ) {
                Text(text = "Messages")
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(0)
            ) {
                Text(text = "Me")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MeScreenPreview() {
//    MeScreen(navController = rememberNavController())
}