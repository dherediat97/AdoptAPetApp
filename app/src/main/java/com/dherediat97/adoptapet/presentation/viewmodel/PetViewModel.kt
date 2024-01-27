package com.dherediat97.adoptapet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.repository.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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


    fun fetchNotAdopted() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val notAdoptedPetList = petRepository.fetchNotAdoptedPets()
            val adoptedPetList = petRepository.fetchNotAdoptedPets()
            delay(200)
            _uiState.update {
                it.copy(
                    petAdoptedNumber = adoptedPetList.size,
                    notAdoptedList = notAdoptedPetList,
                    isLoading = false
                )
            }
        }
    }

    fun cleanAllPets() {
        petRepository.cleanAllPets()
        _uiState.update { it.copy(petAdoptedNumber = 0, notAdoptedList = mutableListOf()) }
    }


    suspend fun addPet(pet: Pet) {
        petRepository.addPet(pet)
    }


    data class UiState(
        val petAdoptedNumber: Int = 0,
        val notAdoptedList: List<Pet> = mutableListOf(),
        val isLoading: Boolean = false
    )

}