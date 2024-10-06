package com.jetbrains.kmpapp.data

import kotlinx.serialization.Serializable

@Serializable
data class MuseumObject(
    val time : String,
    val session : String,
    val room : String
)
