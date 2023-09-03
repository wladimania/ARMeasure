package org.tensorflow.lite.examples.objectdetection.util

import com.google.firebase.auth.FirebaseUser
import org.tensorflow.lite.examples.objectdetection.data.model.DataUser

object SessionManager {
    private var currentUser: FirebaseUser? = null
    private var dataPerson: DataUser? = null
    private var idAcceso: String? = ""

    fun setCurrentUser(user: FirebaseUser?) {
        this.currentUser = user
    }
    fun getCurrentUser(): FirebaseUser? {
        return this.currentUser
    }
    fun setDataUser(data: DataUser?) {
        this.dataPerson = data
    }
    fun getDataUser(): DataUser? {
        return this.dataPerson
    }
    fun setIdAcceso(idAcceso: String?) {
        this.idAcceso = idAcceso
    }
    fun getIdAcceso(): String? {
        return this.idAcceso
    }
}
