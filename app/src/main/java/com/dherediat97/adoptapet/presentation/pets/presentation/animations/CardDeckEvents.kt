package com.dherediat97.adoptapet.presentation.pets.presentation.animations

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import com.dherediat97.adoptapet.presentation.CardSwipeState
import com.dherediat97.adoptapet.presentation.paddingOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class CardDeckEvents(
    val cardWidth: Float,
    val cardHeight: Float,
    val model: CardDeckModel,
    val peepHandler: () -> Unit,
    val playHandler: () -> Unit,
    val nextHandler: () -> Unit,
    val actionCallback: (String) -> Unit = {}
) {
    val cardSwipe: CardSwipeAnimation = CardSwipeAnimation(
        model = model,
        cardWidth = cardWidth,
        cardHeight = cardHeight
    )
    val flipCard = FlipCardAnimation(peepHandler)
    val cardsInDeck = CardsInDeckAnimation(paddingOffset, model.count)

    @SuppressLint("ModifierFactoryExtensionFunction")
    fun makeCardModifier(
        coroutineScope: CoroutineScope,
        topCardIndex: Int,
        idx: Int
    ): Modifier {
        return if (idx > topCardIndex) {
            Modifier
                .scale(cardsInDeck.scaleX(idx), 1f)
                .offset { cardsInDeck.offset(idx) }
        } else {
            Modifier
                .scale(flipCard.scaleX(), flipCard.scaleY())
                .offset { cardSwipe.toIntOffset() }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            cardSwipe.animateToTarget(
                                coroutineScope,
                                CardSwipeState.DRAGGING
                            ) {
                                if (it) {
                                    nextHandler()
                                    coroutineScope.launch {
                                        flipCard.backToInitState()
                                    }

                                }
                                cardsInDeck.backToInitState()
                            }
                        },
                        onDrag = { change, _ ->
                            cardSwipe.draggingCard(coroutineScope, change) {
                                cardsInDeck.pushBackToTheFront()
                            }
                        }
                    )
                }
        }
    }
}