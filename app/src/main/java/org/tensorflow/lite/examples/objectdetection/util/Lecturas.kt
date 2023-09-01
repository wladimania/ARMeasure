package org.tensorflow.lite.examples.objectdetection.util

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class Lecturas {
    private val collectionName = "lecturas"

    private val firestoreManager: FirestoreManager
    constructor() {
        firestoreManager = FirestoreManager(this.collectionName)
    }

    fun listarTodosLosRegistros(completion: (List<Map<String, Any>>) -> Unit) {
        firestoreManager.listarRegistros(completion)
    }

    fun buscarRegistrosPorCondicion(campo: String, valor: Any, completion: (List<Map<String, Any>>) -> Unit) {
        firestoreManager.listarRegistrosConCondicion(campo, valor, completion)
    }

    fun guardarRegistro(idJugador: String, idCurso: String, nombre: String, figura: String, acerto: String, onComplete: () -> Unit, onError: (Exception) -> Unit) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        val registro = hashMapOf(
            "idJugador" to idJugador,
            "idCurso" to idCurso,
            "nombre" to nombre,
            "figura" to figura,
            "fecha" to fechaActual,
            "acerto" to acerto
        )
        firestoreManager.guardarRegistro(registro, onComplete, onError)
    }

    fun actualizarRegistro(documentId: String, nuevosDatos: Map<String, Any>, onComplete: () -> Unit, onError: (Exception) -> Unit) {
        firestoreManager.actualizarRegistro(documentId, nuevosDatos, onComplete, onError)
    }
}