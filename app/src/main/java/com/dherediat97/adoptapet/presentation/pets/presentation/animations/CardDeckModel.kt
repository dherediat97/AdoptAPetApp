package com.dherediat97.adoptapet.presentation.pets.presentation.animations

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.cardHeight
import com.dherediat97.adoptapet.presentation.cardWidth

data class CardDeckModel(
    var current: Int,
    val dataSource: MutableList<Pet>,
    val visible: Int = 3,
    val screenWidth: Int,
    val screenHeight: Int
) {
    val count = dataSource.size
    val visibleCards: Int = StrictMath.min(visible, dataSource.size - current)

    fun cardVisible(visibleIndex: Int) = dataSource[dataSourceIndex(visibleIndex)]

    private fun dataSourceIndex(visibleIndex: Int): Int {
        return current + visibleIndex
    }


    @Composable
    fun cardWidthPx(): Float {
        return with(LocalDensity.current) { cardWidth.toPx() }
    }

    @Composable
    fun cardHeightPx(): Float {
        return with(LocalDensity.current) { cardHeight.toPx() }
    }
}