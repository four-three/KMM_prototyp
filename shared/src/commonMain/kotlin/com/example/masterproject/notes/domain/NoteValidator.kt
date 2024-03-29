package com.example.masterproject.notes.domain

object NoteValidator {
    fun validateNote(note: Note): ValidationResult {
        var result = ValidationResult()

//        if (note.createdAt.equals(0) || note.updatedAt.equals(0)) {
//            result = result.copy(dateError = "Datetime error")
//        }

        if (note.title.isBlank()) {
            result = result.copy(titleError = "The Title is missing!")
        }

        return result
    }

    data class ValidationResult(
        val dateError: String? = null,
        val titleError: String? = null
    )
}