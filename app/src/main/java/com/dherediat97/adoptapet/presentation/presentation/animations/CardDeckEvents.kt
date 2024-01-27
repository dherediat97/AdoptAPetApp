package com.dherediat97.adoptapet.presentation.presentation.animations

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import com.dherediat97.adoptapet.presentation.constants.paddingOffset
import com.dherediat97.adoptapet.presentation.presentation.CardSwipeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class CardDeckEvents(
    val cardWidth: Float,
    val cardHeight: Float,
    val model: CardDeckModel,
    val nextHandler: () -> Unit,
    val actionCallback: (String) -> Unit = {}
) {
    val cardSwipe: CardSwipeAnimation = CardSwipeAnimation(
        model = model,
        cardWidth = cardWidth,
        cardHeight = cardHeight
    )
    val cardsInDeck = CardsInDeckAnimation(paddingOffset, model.count)

    fun makeCardModifier(
        coroutineScope: CoroutineScope,
        topCardIndex: Int,
        idx: Int,
        acceptPet: (Boolean) -> Unit,
    ): Modifier {
        return if (idx > topCardIndex) {
            Modifier
                .scale(cardsInDeck.scaleX(idx), 1f)
                .offset { cardsInDeck.offset(idx) }
        } else {
            Modifier
                .scale(cardsInDeck.scaleX(idx), 1f)
                .offset { cardSwipe.toIntOffset() }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            cardSwipe.animateToTarget(
                                coroutineScope,
                                CardSwipeState.DRAGGING
                            ) { isDraggedFinished ->
                                if (isDraggedFinished) {
                                    nextHandler()
                                    coroutineScope.launch {
                                        acceptPet(true)
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