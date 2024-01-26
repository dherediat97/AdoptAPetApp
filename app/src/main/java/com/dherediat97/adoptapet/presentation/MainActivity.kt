package com.dherediat97.adoptapet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.dherediat97.adoptapet.R
import com.dherediat97.adoptapet.data.Pet
import com.dherediat97.adoptapet.presentation.mainscreen.FlipCard
import com.dherediat97.adoptapet.presentation.mainscreen.PetCard
import com.dherediat97.adoptapet.presentation.theme.AdoptAPetTheme

val pumuki = Pet("Pumuki", 18, "Macho", R.drawable.pumuki)

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
                                            ) { Text("1") }
                                        }) {
                                        Icon(
                                            painter = rememberVectorPainter(image = Icons.Filled.FavoriteBorder),
                                            contentDescription = "favorite pets icon"
                                        )
                                    }
                                }
                            }

                            var cardRotation by remember {
                                mutableStateOf(PetCard.Front)
                            }
                            FlipCard(
                                petCard = cardRotation,
                                onClick = { cardRotation = cardRotation.next },
                                front = {
                                    DraggableCard(isFront = true)
                                },
                                back = {
                                    DraggableCard(isFront = false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraggableCard(isFront: Boolean) {
    val dismissState = rememberSwipeToDismissBoxState()
    val acceptPet = remember { mutableStateOf(false) }

    LaunchedEffect(acceptPet.value) {
        dismissState.reset()
    }


    if (isFront) {
        Image(
            painter = painterResource(id = pumuki.picturePet),
            contentDescription = "pet image front",
            alpha = 0.6f,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        SwipeToDismissBox(state = dismissState, backgroundContent = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart)
                        Text("${pumuki.name} ha sido elegido", color = Color.White)
                    if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd)
                        Text("${pumuki.name} ha sido descartado", color = Color.White)

                    if (dismissState.targetValue != SwipeToDismissBoxValue.Settled) {
                        Button(onClick = {
                            //Next Pet and save if accept
                            acceptPet.value = true
                        }) {
                            Text("Aceptar", color = Color.White)
                        }
                    }
                }
            }
        }) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                        contentDescription = "accept pet icon",
                        tint = Color.White,
                    )
                    Icon(
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowForward),
                        contentDescription = "decline pet icon",
                        tint = Color.White,
                    )
                }
                Column(modifier = Modifier.padding(24.dp)) {
                    //Basic Pet Info
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Filled.DateRange),
                            contentDescription = "age pet icon",

                            tint = Color.White,
                        )
                        Text("Edad: ${pumuki.months} meses", color = Color.White)
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Filled.Male),
                            tint = Color.White,
                            contentDescription = "sex pet icon"
                        )
                        Text(pumuki.gender, color = Color.White)
                    }

                    //Name and desc pet info
                    Text(
                        text = pumuki.name,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 8.dp),
                        thickness = 4.dp,
                        color = Color.White
                    )
                    Text(
                        text = "Es la mascota que siempre quisistes tener, es super cariñoso y juguetón.\n\nFue abandonado hace unos meses y necesita un nuevo dueño urgentemente",
                        color = Color.White,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.pumuki),
            contentDescription = "pet image back",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.4f,
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Más detalles",
                color = Color.White
            )
        }
    }

}