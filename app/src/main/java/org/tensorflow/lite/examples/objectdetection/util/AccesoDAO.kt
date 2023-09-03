package org.tensorflow.lite.examples.objectdetection.util

import com.google.firebase.firestore.DocumentReference
import java.text.SimpleDateFormat
import java.util.Date

class AccesoDAO {
    private val collectionName = "accesos"

    private val firestoreManager: FirestoreManager

    constructor() {
        firestoreManager = FirestoreManager(this.collectionName)
    }

    fun listarTodosLosRegistros(completion: (List<Map<String, Any>>) -> Unit) {
        firestoreManager.listarRegistros(completion)
    }

    fun buscarRegistrosPorCondicion(
        campo: String,
        valor: Any,
        completion: (List<Map<String, Any>>) -> Unit
    ) {
        firestoreManager.listarRegistrosConCondicion(campo, valor, completion)
    }

    fun guardarRegistro(
        idUsuario: String,
        onComplete: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        val registro = hashMapOf(
            "userId" to idUsuario,
            "fecha" to fechaActual
        )
        firestoreManager.guardarRegistro(
            registro,
            { documentReference ->
                if (documentReference is DocumentReference) {
                    val idDelRegistro = documentReference.id
                    SessionManager.setIdAcceso(idDelRegistro)
                    onComplete(idDelRegistro)
                } else {
                    onError(IllegalStateException("El objeto no es un DocumentReference"))
                }
            }, onError
        )
    }

}