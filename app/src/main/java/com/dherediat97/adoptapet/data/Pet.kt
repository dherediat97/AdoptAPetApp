package com.dherediat97.adoptapet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pets")
data class Pet(
    @PrimaryKey
    val id: Int,
    val name: String,
    val age: String,
    val gender: String,
    val picturePet: Int
)
