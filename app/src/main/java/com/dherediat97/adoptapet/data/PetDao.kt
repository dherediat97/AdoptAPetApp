package com.dherediat97.adoptapet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import javax.inject.Singleton

@Dao
@Singleton
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPet(pet: Pet)

    @Query("SELECT * FROM pets")
    fun getAllPets(): MutableList<Pet>

    @Query("DELETE FROM pets")
    suspend fun deleteAllPets()
}