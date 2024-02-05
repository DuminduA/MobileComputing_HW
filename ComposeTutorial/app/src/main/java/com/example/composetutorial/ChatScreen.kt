package com.example.composetutorial

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.composetutorial.data.SampleData
import com.example.composetutorial.data.UserEvent
import com.example.composetutorial.data.UserState
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

@Composable
fun ChatScreen(
    navController: NavHostController,
    state: UserState,
    onEvent: (UserEvent) ->(Unit)
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Conversation(messages = SampleData.conversationSample, state, onEvent)
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
                onClick = {},
                shape = RoundedCornerShape(0)

            ) {
                Text(text = "Messages")
            }
            Button(
                onClick = { navController.navigate(route = Screen.ProfileScreen.route)},
                shape = RoundedCornerShape(0)
            ) {
                Text(text = "Me")
            }
        }
    }
}

data class Message(val author: String, val body:String)

@Composable
fun NavPanel() {
    Column {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Home")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Messages")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Me")
        }
    }
}

@Composable
fun MessageCard(msg: Message, state: UserState, onEvent: (UserEvent) -> Unit) {
    Row {
        AsyncImage(
            model = state.photo,
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = "",
        )


        Column (modifier = Modifier.clickable { isExpanded = !isExpanded })
        {
            Text(text = state.firstName,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
            Surface (shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)

            ) {
                Text(text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface{
//            MessageCard(msg = Message("Lexi", "I am Learning Jetpack Compose at Uni Oulu"))
        }
    }
}

@Composable
fun Conversation(messages : List<Message>, state: UserState, onEvent: (UserEvent) -> Unit) {
    LazyColumn (
    ){
        items(messages) { message ->
            MessageCard(message, state, onEvent)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
//        Conversation(SampleData.conversationSample)
    }
}

@Composable
@Preview(showBackground = true)
fun ChatScreenPreview() {
//    ChatScreen(navController = rememberNavController(), state = UserState(), onEvent = UserEvent())
}