package com.dherediat97.adoptapet.presentation.repository

import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.data.PetDao
import com.dherediat97.adoptapet.presentation.constants.pets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetRepository @Inject constructor(private val petDAO: PetDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun cleanAllPets() {
        coroutineScope.launch(Dispatchers.IO) {
            petDAO.deleteAllPets()
        }
    }

    fun fetchAdoptedPets(): MutableList<Pet> {
        return petDAO.getAllPets(true)
    }

    fun fetchAllPets(): MutableList<Pet> {
        return petDAO.getAllPets(false)
    }

    fun adoptPet(petId: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            petDAO.updatePet(petId, true)
        }
    }

    fun addAllPets() {
        coroutineScope.launch(Dispatchers.IO) {
            petDAO.addPets(pets)
        }
    }

}