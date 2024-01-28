package com.dherediat97.adoptapet.presentation.constants

import androidx.compose.ui.unit.dp
import com.dherediat97.adoptapet.R
import com.dherediat97.adoptapet.data.Pet

val cornerRadiusBig = 36.dp
val cardWidth = 400.dp
val cardHeight = 600.dp
const val paddingOffset = 48f
const val animationTime = 500

val toby = Pet(
    1,
    "Toby",
    "2 años",
    "Macho",
    R.drawable.toby
)
val loki = Pet(
    2,
    "Loki",
    "4 meses",
    "Hembra",
    R.drawable.loki
)
val laika = Pet(
    3,
    "Laika",
    "7 meses",
    "Hembra",
    R.drawable.laika
)
val milo = Pet(
    4,
    "Milo",
    "10 meses",
    "Macho",
    R.drawable.milo
)
val lara = Pet(
    5,
    "Lara",
    "1 año",
    "Hembra",
    R.drawable.lara
)
val noa = Pet(
    6,
    "Noa",
    "7 meses",
    "Macho",
    R.drawable.noe
)
val motas = Pet(
    7,
    "Motas",
    "12 meses",
    "Macho",
    R.drawable.motas
)
val derek = Pet(
    8,
    "Derek",
    "5 años",
    "Macho",
    R.drawable.derek
)

val pets = mutableListOf(toby, loki, laika, milo, lara, noa, motas, derek)