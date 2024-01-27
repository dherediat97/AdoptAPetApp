package com.dherediat97.adoptapet.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val petRepository: PetRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState

    val petAdoptedNumber: MutableStateFlow<Int> = MutableStateFlow(0)

    fun fetchAllPets() {
        _uiState.update { it.copy(isLoading = true) }
        val allPets = petRepository.fetchAllPets()
        val adoptedPetList = petRepository.fetchAdoptedPets()
        _uiState.update {
            it.copy(
                petList = allPets,
                isLoading = false
            )
        }
        petAdoptedNumber.update { adoptedPetList.size }
    }

    fun fetchAdoptedPets() {
        viewModelScope.launch(Dispatchers.Main) {
            val adoptedPetList = petRepository.fetchAdoptedPets()
            delay(10)
            petAdoptedNumber.update { adoptedPetList.size }
        }
    }

    fun cleanAllPets() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            petRepository.cleanAllPets()
            val allPets = petRepository.fetchAllPets()
            _uiState.update {
                it.copy(
                    petList = allPets,
                    isLoading = false
                )
            }
            petAdoptedNumber.update { 0 }
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.adoptPet(pet.id)
            val adoptedPetList = petRepository.fetchAdoptedPets()
            petAdoptedNumber.update { adoptedPetList.size }
        }
    }

    data class UiState(
        val petList: List<Pet> = mutableListOf(),
        val isLoading: Boolean = false
    )

}