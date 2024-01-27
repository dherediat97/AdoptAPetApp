package com.dherediat97.adoptapet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dherediat97.adoptapet.R
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.pets.presentation.CardPetDeck
import com.dherediat97.adoptapet.presentation.pets.viewmodel.PetViewModel
import com.dherediat97.adoptapet.presentation.theme.AdoptAPetTheme
import dagger.hilt.android.AndroidEntryPoint


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
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AdoptAPetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen(petViewModel: PetViewModel = viewModel()) {
        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
            Column(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
                    .fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        "Adopta a una Mascota",
                        fontSize = 30.sp,
                        modifier = Modifier.weight(1f),
                        fontFamily = FontFamily.Cursive
                    )

                    IconButton(modifier = Modifier.weight(0.2f), onClick = { }) {
                        val petsSaved = remember {
                            0
                        }
                        if (petsSaved != null) {
                            if (petsSaved > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            contentColor = Color.White,
                                            containerColor = Color.Magenta
                                        ) { Text(petsSaved.toString()) }
                                    }) {
                                    Icon(
                                        painter = rememberVectorPainter(image = Icons.Filled.FavoriteBorder),
                                        contentDescription = "favorite pets icon"
                                    )
                                }
                            }
                        }
                    }
                }
                CardPetDeck()
            }
        }
    }
}

