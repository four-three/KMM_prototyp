package com.example.masterproject.notes.domain

data class Note(
    //saved automatically
    val id: Long?,
    val date: String, //datetime?
    val location: String?,
    //saved by the user
    val title: String,
    val note: String,
    val photoBytes: ByteArray? //cannot use bitmape because it is android specific
)
