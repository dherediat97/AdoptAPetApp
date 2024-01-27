package com.dherediat97.adoptapet.presentation.pets.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dherediat97.adoptapet.presentation.pets.constants.cornerRadiusBig

@Composable
fun CardView(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadiusBig),
        shadowElevation = 16.dp,
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    )
}