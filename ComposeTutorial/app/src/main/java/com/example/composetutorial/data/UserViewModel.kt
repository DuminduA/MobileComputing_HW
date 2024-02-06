package com.example.composetutorial.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModel(
    private val dao: UserDao
): ViewModel() {

    val _state = MutableStateFlow(UserState())

    init {
        _state.update {
            val user = dao.findFirstUser()
            val firstName = dao.findFirstUser().firstName
            val photo = dao.findFirstUser().photo
            UserState(emptyList(), firstName, photo)
       }
    }

    fun onEvent(event: UserEvent) {
        when(event) {
            is UserEvent.DeleteUser -> {
                viewModelScope.launch {
                    dao.delete(event.user)
                }
            }
            is UserEvent.SaveUser -> {
                val firstName = _state.value.firstName
                val photo = _state.value.photo

                val user = User(
                    id = 1,
                    firstName = firstName,
                    photo = photo
                )
                viewModelScope.launch {
                    dao.upsertUser(user)
                }

            }
            is UserEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                ) }
                viewModelScope.launch {
                    dao.updateUserName(event.firstName, 1)
                }
            }
            is UserEvent.SetPhoto -> {
                _state.update { it.copy(
                    photo = event.photo
                ) }
                viewModelScope.launch {
                    dao.updateProPic(event.photo, 1)

                }
            }
        }
    }

}
