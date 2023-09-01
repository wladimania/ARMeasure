package org.tensorflow.lite.examples.objectdetection.util

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreManager(private val collectionName: String) {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCollection(): CollectionReference {
        return db.collection(collectionName)
    }
    fun listarRegistros(completion: (List<Map<String, Any>>) -> Unit) {
        getCollection().get()
            .addOnSuccessListener { querySnapshot ->
                val registrosList = mutableListOf<Map<String, Any>>()
                for (document in querySnapshot) {
                    val registro = document.data
                    registrosList.add(registro)
                }
                completion(registrosList)
            }
            .addOnFailureListener {
                // Manejar el error en caso de falla
                completion(emptyList())
            }
    }
    fun listarRegistrosConCondicion(
        campo: String,
        valor: Any,
        completion: (List<Map<String, Any>>) -> Unit
    ) {
        getCollection()
            .whereEqualTo(campo, valor) // Puedes usar otros operadores aquí
            .get()
            .addOnSuccessListener { querySnapshot ->
                val registrosList = mutableListOf<Map<String, Any>>()
                for (document in querySnapshot) {
                    val registro = document.data
                    registrosList.add(registro)
                }
                completion(registrosList)
            }
            .addOnFailureListener {
                // Manejar el error en caso de falla
                completion(emptyList())
            }
    }

    fun guardarRegistro(registro: Map<String, Any>, onComplete: () -> Unit, onError: (Exception) -> Unit) {
        getCollection()
            .add(registro)
            .addOnSuccessListener {
                onComplete.invoke()
            }
            .addOnFailureListener { exception ->
                onError.invoke(exception)
            }
    }

    fun actualizarRegistro(documentId: String, nuevosDatos: Map<String, Any>, onComplete: () -> Unit, onError: (Exception) -> Unit) {
        getCollection()
            .document(documentId)
            .update(nuevosDatos)
            .addOnSuccessListener {
                onComplete.invoke()
            }
            .addOnFailureListener { exception ->
                onError.invoke(exception)
            }
    }

}
