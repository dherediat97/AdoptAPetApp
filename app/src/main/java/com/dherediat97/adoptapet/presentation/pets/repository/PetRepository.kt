package com.dherediat97.adoptapet.presentation.pets.repository

import androidx.lifecycle.MutableLiveData
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.data.PetDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetRepository(private val employeeDao: PetDao) {

    val allPets = MutableLiveData<List<Pet>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addPet(newPet: Pet) {
        coroutineScope.launch(Dispatchers.IO) {
            employeeDao.addPet(newPet)
        }
    }

}