package com.dherediat97.adoptapet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val petRepository: PetRepository) : ViewModel() {

    var petsAdopted: Int = 0

    fun fetchPetsAdopted() {
        petsAdopted = petRepository.allPets.size
    }

    suspend fun addPet(pet: Pet) {
        petRepository.addPet(pet)
    }

}