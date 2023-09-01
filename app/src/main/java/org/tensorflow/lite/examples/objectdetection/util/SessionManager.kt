package org.tensorflow.lite.examples.objectdetection.util

import com.google.firebase.auth.FirebaseUser

object SessionManager {
    private var currentUser: FirebaseUser? = null

    fun setCurrentUser(user: FirebaseUser?) {
        currentUser = user
    }

    fun getCurrentUser(): FirebaseUser? {
        return currentUser
    }
}
