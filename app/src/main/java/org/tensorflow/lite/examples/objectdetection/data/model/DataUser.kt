package org.tensorflow.lite.examples.objectdetection.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
class DataUser{
    private var nombre: String = ""
    private var correo: String = ""
    private var esAlumno: String = ""
    private var idCurso: String = ""
    // Constructor vacío
    constructor() {}

    // Getters y setters
    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun getCorreo(): String {
        return correo
    }

    fun setCorreo(correo: String) {
        this.correo = correo
    }

    fun getEsAlumno(): String {
        return esAlumno
    }

    fun setEsAlumno(esAlumno: String) {
        this.esAlumno = esAlumno
    }

    fun getIdCurso(): String {
        return idCurso
    }

    fun setIdCurso(idCurso: String) {
        this.idCurso = idCurso
    }
}
