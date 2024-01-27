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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dherediat97.adoptapet.presentation.presentation.cardlist.CardPetList
import com.dherediat97.adoptapet.presentation.theme.AdoptAPetTheme
import com.dherediat97.adoptapet.presentation.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint


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
        val petAdoptedNumber = petViewModel.petAdoptedNumber.collectAsState()
        LaunchedEffect(Unit) {
            petViewModel.fetchAdoptedPets()
        }
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
                        BadgedBox(
                            badge = {
                                Badge(
                                    contentColor = Color.White,
                                    containerColor = Color.Magenta
                                ) { Text(petAdoptedNumber.value.toString()) }
                            }) {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.Filled.FavoriteBorder),
                                contentDescription = "favorite pets icon"
                            )
                        }
                    }
                }
                CardPetList()
            }
        }
    }
}

