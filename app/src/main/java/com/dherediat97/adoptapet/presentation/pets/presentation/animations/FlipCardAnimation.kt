package com.dherediat97.adoptapet.presentation.pets.presentation.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.dherediat97.adoptapet.presentation.animationTime
import com.dherediat97.adoptapet.presentation.petcard.PetCard

data class FlipCardAnimation(
    val peepHandler: () -> Unit
) {
    private lateinit var flipState: MutableState<PetCard>
    private lateinit var scaleXAnimation: State<Float>
    private lateinit var scaleYAnimation: State<Float>

    @Composable
    fun Init() {
        flipState = remember { mutableStateOf(PetCard.FRONT_FACE) }

        scaleXAnimation = animateFloatAsState(
            if (flipState.value == PetCard.FRONT_FACE ||
                flipState.value == PetCard.BACK_FACE
            ) 1f else 0.8f,
            animationSpec = animationSpec,
            finishedListener = {
            }, label = ""
        )
        scaleYAnimation = animateFloatAsState(
            if (flipState.value == PetCard.FRONT_FACE ||
                flipState.value == PetCard.BACK_FACE
            ) 1f else 0.1f,
            animationSpec = animationSpec,
            finishedListener = {
                if (flipState.value == PetCard.FLIP_BACK) {
                    flipState.value = PetCard.BACK_FACE
                } else if (flipState.value == PetCard.FLIP_FRONT) {
                    flipState.value = PetCard.FRONT_FACE
                }
            }, label = ""
        )
    }

    private val animationSpec: TweenSpec<Float> = tween(
        durationMillis = animationTime,
        easing = LinearEasing
    )

    fun flipToBackSide() {
        flipState.value = PetCard.FLIP_BACK
        peepHandler.invoke()
    }

    fun flipToFrontSide() {
        flipState.value = PetCard.FLIP_FRONT
    }

    fun backToInitState() {
        flipState.value = PetCard.FRONT_FACE
    }

    fun isFrontSide(): Boolean {
        return flipState.value == PetCard.FRONT_FACE
    }

    fun scaleX(): Float {
        return scaleXAnimation.value
    }

    fun scaleY(): Float {
        return scaleYAnimation.value
    }

    fun cardSide(): PetCard {
        return flipState.value
    }
}