package com.example.composetutorial

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.composetutorial.data.User
import com.example.composetutorial.data.UserDatabase
import com.example.composetutorial.data.UserViewModel
import com.example.composetutorial.notifications.SensorNotificationService
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity(), SensorEventListener {

    lateinit var navController: NavHostController
    lateinit var sensorManager: SensorManager

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user_db"
        ).allowMainThreadQueries().build()
    }

    private val viewModel by viewModels<UserViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(db.userDao()) as T

                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme (
                darkTheme = false,
            ) {
                val user = db.userDao().findFirstUser()
                if(user == null) {
                    db.userDao().insertUser(User(1, "Alex", ""))
                }
                val state by viewModel._state.collectAsState()
                navController = rememberNavController()
                var sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)?.let {
                    sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
                }
                SetupNavGraph(navController = navController, state, viewModel::onEvent, applicationContext)
            }
        }
    }

    private fun getBrightness(brightness: Float): String {
        return when (brightness.toInt()) {
            0 -> "Super Dark"
            in 1..10 -> "Dark"
            in 51..5000 -> "Normal"
            in 5001..25000 -> "Bright"
            else -> "Super bright"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        print("Sensor event changed")
        if(event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light = event.values[0]

            if(light > 5000) {
                val sensorNotificationService = SensorNotificationService(applicationContext)
                sensorNotificationService.showNotification("Sensor $light\n${getBrightness(light)}")

            }
      }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}



