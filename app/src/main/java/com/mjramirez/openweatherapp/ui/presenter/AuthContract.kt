package com.mjramirez.openweatherapp.ui.presenter

interface AuthContract {
    interface View {
        fun onAuthSuccess()
        fun onAuthError(message: String)
    }
    interface Presenter {
        fun signIn(email: String, password: String)
        fun register(email: String, password: String)
        fun signOut()
    }
}

