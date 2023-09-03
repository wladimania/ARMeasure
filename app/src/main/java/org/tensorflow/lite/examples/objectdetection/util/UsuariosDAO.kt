package org.tensorflow.lite.examples.objectdetection.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.tensorflow.lite.examples.objectdetection.data.model.DataUser


class UsuariosDAO {
    private val collectionName = "usuarios"

    private val firestoreManager: FirestoreManager
    private val auth: FirebaseAuth

    constructor() {
        firestoreManager = FirestoreManager(this.collectionName)
        auth = FirebaseAuth.getInstance()
    }

    private fun Login(
        email: String,
        password: String,
        callback: OnSignInCompleteCallback
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    callback.onSignInSuccess(user)
                } else {
                    val errorMessage = task.exception?.message
                    callback.onSignInFailure(errorMessage ?: "Error desconocido")
                }
            }
    }

    private fun searchUsuarioById(userId: FirebaseUser, callback: OnSearchUserCompleteCallback) {
        firestoreManager.getDocumentdata(userId.uid).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Document exists, you can access its data
                    val dataUser = DataUser()
                    dataUser.setCorreo(documentSnapshot.getString("correo").toString())
                    dataUser.setNombre(documentSnapshot.getString("nombre").toString())
                    dataUser.setEsAlumno(documentSnapshot.getString("esAlumno").toString())
                    dataUser.setIdCurso(documentSnapshot.getString("IdCurso").toString())

                    val user = auth.currentUser
                    SessionManager.setCurrentUser(user)
                    SessionManager.setDataUser(dataUser)
                    callback.onSuccess(dataUser)
                } else {
                    callback.onFailure("No se encontraron datos adicionales del usuario.")
                }
            }
            .addOnFailureListener { e -> // Handle any errors
                callback.onFailure(e.message)
            };
    }

    fun iniciarSesion(email: String, password: String, callback: OnSignInCompleteCallback) {
        Login(email, password, object : OnSignInCompleteCallback {
            override fun onSignInSuccess(user: FirebaseUser?) {
                if (user != null) {
                    searchUsuarioById(user, object : OnSearchUserCompleteCallback {
                        override fun onSuccess(dataUser: DataUser?) {
                            callback.onSignInSuccess(user)
                        }
                        override fun onFailure(errorMessage: String?) {
                            callback.onSignInFailure(errorMessage)
                        }
                    })
                } else {
                    callback.onSignInFailure("No se encontró un usuario después del inicio de sesión")
                }
            }
            override fun onSignInFailure(errorMessage: String?) {
                callback.onSignInFailure(errorMessage)
            }
        })
    }

    fun actualizarRegistro(documentId: String, nuevosDatos: Map<String, Any>, onComplete: () -> Unit, onError: (Exception) -> Unit) {
        firestoreManager.actualizarRegistro(documentId, nuevosDatos, onComplete, onError)
    }

    interface OnSignInCompleteCallback {
        fun onSignInSuccess(user: FirebaseUser?)
        fun onSignInFailure(errorMessage: String?)
    }

    interface OnSearchUserCompleteCallback {
        fun onSuccess(user: DataUser?)
        fun onFailure(errorMessage: String?)
    }
}