package com.dherediat97.adoptapet.presentation.pets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.pets.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val employeeRepository: PetRepository) :
    ViewModel() {

    val petList: LiveData<List<Pet>> = employeeRepository.allPets

    fun addEmployee(pet: Pet) {
        employeeRepository.addPet(pet)
    }

}