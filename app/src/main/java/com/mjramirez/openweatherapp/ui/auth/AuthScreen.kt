package com.mjramirez.openweatherapp.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mjramirez.openweatherapp.ui.presenter.AuthContract
import com.mjramirez.openweatherapp.ui.presenter.AuthPresenter

@Composable
fun AuthScreen(onAuthed: () -> Unit) {
    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val presenter = remember { AuthPresenter(object : AuthContract.View {
        override fun onAuthSuccess() { onAuthed() }
        override fun onAuthError(message: String) { error = message }
    }) }

    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(if (isLogin) "Sign In" else "Register", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            if (isLogin) presenter.signIn(email, password) else presenter.register(email, password)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(if (isLogin) "Sign In" else "Create Account")
        }
        TextButton(onClick = { isLogin = !isLogin }) {
            Text(if (isLogin) "No account? Register" else "Have an account? Sign In")
        }
    }
}

