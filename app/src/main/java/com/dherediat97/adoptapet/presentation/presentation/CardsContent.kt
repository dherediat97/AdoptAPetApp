package com.dherediat97.adoptapet.presentation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dherediat97.adoptapet.data.Pet

@Composable
fun CardsContent(pet: Pet) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var cardRotation by remember { mutableStateOf(PetCard.FRONT_FACE) }
        FlipCard(petCard = cardRotation, onClick = { cardRotation = cardRotation.next }, back = {
            DraggableCard(isFront = false, pet)
        }, front = {
            DraggableCard(isFront = true, pet)
        })
    }
}