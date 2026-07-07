package com.mjramirez.openweatherapp.ui.presenter

import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class AuthPresenter(private var view: AuthContract.View?) : AuthContract.Presenter {
    private val auth = Firebase.auth

    override fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) view?.onAuthSuccess() else view?.onAuthError(task.exception?.message ?: "Auth failed")
        }
    }

    override fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) view?.onAuthSuccess() else view?.onAuthError(task.exception?.message ?: "Registration failed")
        }
    }

    override fun signOut() {
        auth.signOut()
    }
}

