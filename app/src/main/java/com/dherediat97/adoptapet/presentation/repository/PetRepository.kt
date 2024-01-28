package com.dherediat97.adoptapet.presentation.repository

import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.data.PetDao
import com.dherediat97.adoptapet.presentation.constants.pets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetRepository @Inject constructor(private val petDAO: PetDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun cleanAllPets() {
        coroutineScope.launch(Dispatchers.IO) {
            petDAO.deleteAllPets()
        }
    }

    fun fetchAdoptedPets(): Flow<List<Pet>> {
        return petDAO.getAllPets(true)
    }

    fun fetchAllPets(): Flow<List<Pet>> {
        return petDAO.getAllPets(false)
    }

    fun adoptPet(petId: Int, petAdopted: Boolean) {
        coroutineScope.launch(Dispatchers.IO) {
            petDAO.updatePet(petId, petAdopted)
        }
    }

    fun addAllPets() {
        coroutineScope.launch(Dispatchers.IO) {
            petDAO.addPets(pets)
        }
    }

}