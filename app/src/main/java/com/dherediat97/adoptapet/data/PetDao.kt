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
    suspend fun addPets(pets: List<Pet>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPet(pet: Pet)

    @Query("SELECT * FROM pets where isAdopted=:isAdopted")
    fun getAllPets(isAdopted: Boolean): MutableList<Pet>

    @Query("UPDATE pets SET isAdopted=:isAdopted WHERE id=:id")
    fun updatePet(id: Int, isAdopted: Boolean)

    @Query("UPDATE pets SET isAdopted=:isAdopted")
    suspend fun deleteAllPets(isAdopted: Boolean=false)
}