package com.dherediat97.adoptapet.presentation.repository

import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.data.PetDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetRepository @Inject constructor(private val petDAO: PetDao) {

    val allPets = petDAO.getAllPets()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun addPet(newPet: Pet) {
        coroutineScope.launch(Dispatchers.IO) {
            petDAO.addPet(newPet)
        }
    }

}