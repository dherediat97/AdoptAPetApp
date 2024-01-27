package com.dherediat97.adoptapet.presentation.pets.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.zIndex
import com.dherediat97.adoptapet.presentation.cardHeight
import com.dherediat97.adoptapet.presentation.cardWidth
import com.dherediat97.adoptapet.presentation.pets
import com.dherediat97.adoptapet.presentation.pets.presentation.animations.CardDeckEvents
import com.dherediat97.adoptapet.presentation.pets.presentation.animations.CardDeckModel


private const val TOP_CARD_INDEX = 0
private const val TOP_Z_INDEX = 100f

@Composable
fun CardPetDeck() {
    var topCardIndex by remember { mutableIntStateOf(0) }

    val model = CardDeckModel(
        current = topCardIndex,
        dataSource = pets,
        screenWidth = 1200,
        screenHeight = 1600
    )
    val events = CardDeckEvents(
        cardWidth = model.cardWidthPx(),
        cardHeight = model.cardHeightPx(),
        model = model,
        peepHandler = {},
        playHandler = {},
        nextHandler = {
            if (topCardIndex < pets.lastIndex) {
                topCardIndex += 1
            } else {
                topCardIndex = 0
            }
        }
    )
    val coroutineScope = rememberCoroutineScope()
    events.apply {
        flipCard.Init()
        cardsInDeck.Init()
        cardSwipe.Init()
    }
    Box(Modifier.fillMaxSize()) {
        repeat(model.visibleCards) { visibleIndex ->
            val pet = model.cardVisible(visibleIndex)
            val cardZIndex = TOP_Z_INDEX - visibleIndex
            val cardModifier = events
                .makeCardModifier(
                    coroutineScope,
                    TOP_CARD_INDEX,
                    visibleIndex
                )
                .align(Alignment.TopCenter)
                .zIndex(cardZIndex)
                .size(cardWidth, cardHeight)

            CardView(
                modifier = cardModifier,
                content = {
                    CardsContent(
                        pet
                    )
                }
            )
            events.cardSwipe.backToInitialState(coroutineScope)
        }
    }
}