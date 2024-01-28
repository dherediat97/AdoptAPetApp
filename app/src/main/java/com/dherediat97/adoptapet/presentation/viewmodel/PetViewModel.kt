package com.dherediat97.adoptapet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val petRepository: PetRepository) : ViewModel() {

    val petList: MutableStateFlow<List<Pet>> = MutableStateFlow(mutableListOf())
    val petAdoptedNumber: MutableStateFlow<Int> = MutableStateFlow(0)

    fun fetchAllPets() {
        viewModelScope.launch(Dispatchers.IO) {
            val allPets = petRepository.fetchAllPets()
            val adoptedPets = petRepository.fetchAdoptedPets()
            petList.update { allPets.first() }
            petAdoptedNumber.update { adoptedPets.first().size }
            delay(50)
        }
    }

    fun fetchAdoptedPets() {
        viewModelScope.launch(Dispatchers.Main) {
            val adoptedPetList = petRepository.fetchAdoptedPets()
            petList.update { adoptedPetList.first() }
            petAdoptedNumber.update { adoptedPetList.first().size }
        }
    }

    fun cleanAllPets() {
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.cleanAllPets()
            val allPets = petRepository.fetchAllPets()
            petList.update { allPets.first() }
            petAdoptedNumber.update { 0 }
        }
    }

    fun updatePet(pet: Pet, adoptPet: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.adoptPet(pet.id, adoptPet)
            delay(50)
            val adoptedPetList = petRepository.fetchAdoptedPets()
            petAdoptedNumber.update { adoptedPetList.first().size }
        }
    }

}