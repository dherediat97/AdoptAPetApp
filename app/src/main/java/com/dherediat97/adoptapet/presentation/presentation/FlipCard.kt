package com.dherediat97.adoptapet.presentation.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

enum class PetCard(val angle: Float) {
    FRONT_FACE(0f) {
        override val next: PetCard
            get() = BACK_FACE
    },
    BACK_FACE(180f) {
        override val next: PetCard
            get() = FRONT_FACE
    },
    FLIP_FRONT(0f) {
        override val next: PetCard
        get() = FLIP_FRONT
    },
    FLIP_BACK(0f) {
        override val next: PetCard
            get() = FLIP_BACK
    };

    abstract val next: PetCard
}

enum class RotationAxis {
    AxisX,
    AxisY,
}


@Composable
fun FlipCard(
    petCard: PetCard,
    onClick: (PetCard) -> Unit,
    modifier: Modifier = Modifier,
    axis: RotationAxis = RotationAxis.AxisY,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = petCard.angle,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing,
        ), label = ""
    )
    Card(
        onClick = { onClick(petCard) },
        modifier = modifier
            .graphicsLayer {
                if (axis == RotationAxis.AxisX) {
                    rotationX = rotation.value
                } else {
                    rotationY = rotation.value
                }
                cameraDistance = 28f * density
            },
    ) {
        if (rotation.value <= 90f) {
            Box(
                Modifier.fillMaxSize()
            ) {
                front()
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (axis == RotationAxis.AxisX) {
                            rotationX = 180f
                        } else {
                            rotationY = 180f
                        }
                    },
            ) {
                back()
            }
        }
    }
}
