package com.mjramirez.openweatherapp.ui

import androidx.compose.runtime.*
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.mjramirez.openweatherapp.ui.auth.AuthScreen
import com.mjramirez.openweatherapp.ui.home.HomeScreen

@Composable
fun AppRoot() {
    val user = Firebase.auth.currentUser
    var isLoggedIn by remember { mutableStateOf(user != null) }

    if (!isLoggedIn) {
        AuthScreen(onAuthed = { isLoggedIn = true })
    } else {
        HomeScreen(onSignOut = {
            Firebase.auth.signOut()
            isLoggedIn = false
        })
    }
}

