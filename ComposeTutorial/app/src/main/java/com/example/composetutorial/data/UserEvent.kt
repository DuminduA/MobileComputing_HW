package com.example.composetutorial.data

sealed interface UserEvent {
    object SaveUser: UserEvent
    data class SetFirstName(val firstName: String): UserEvent
    data class SetPhoto(val photo: String): UserEvent
    data class DeleteUser(val user: User): UserEvent
}