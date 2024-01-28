package com.dherediat97.adoptapet.presentation.presentation.cardlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dherediat97.adoptapet.R
import com.dherediat97.adoptapet.presentation.constants.cardHeight
import com.dherediat97.adoptapet.presentation.constants.cardWidth
import com.dherediat97.adoptapet.presentation.constants.pets
import com.dherediat97.adoptapet.presentation.presentation.CardView
import com.dherediat97.adoptapet.presentation.presentation.CardsContent
import com.dherediat97.adoptapet.presentation.presentation.animations.CardDeckEvents
import com.dherediat97.adoptapet.presentation.presentation.animations.CardDeckModel
import com.dherediat97.adoptapet.presentation.viewmodel.PetViewModel


private const val TOP_CARD_INDEX = 0
private const val TOP_Z_INDEX = 100f

@Composable
fun CardPetList(petViewModel: PetViewModel = viewModel()) {
    var topCardIndex by remember { mutableIntStateOf(0) }
    val petListData = petViewModel.petList.collectAsState(initial = pets)
    val petAdoptedNumberData = petViewModel.petAdoptedNumber.collectAsState()

    LaunchedEffect(Unit) {
        petViewModel.fetchAllPets()
    }
    LaunchedEffect(Unit) {
        petViewModel.fetchAdoptedPets()
    }
    val petList by remember { petListData }
    val petAdoptedNumber by remember { petAdoptedNumberData }
    var isPetAdopting by remember { mutableIntStateOf(-1) }
    val configuration = LocalConfiguration.current

    val model = CardDeckModel(
        current = topCardIndex,
        dataSource = petList,
        screenWidth = configuration.screenWidthDp.dp.value.toInt(),
        screenHeight = configuration.screenHeightDp.dp.value.toInt()
    )
    val events = CardDeckEvents(
        cardWidth = model.cardWidthPx(),
        cardHeight = model.cardHeightPx(),
        model = model,
        nextHandler = {
            if (topCardIndex < pets.lastIndex) {
                topCardIndex += 1
            } else {
                topCardIndex = 0
            }
        }
    )
    events.apply {
        cardsInDeck.Init()
        cardSwipe.Init()
    }
    val coroutineScope = rememberCoroutineScope()
    Box(
        Modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxSize()
    ) {
        if (petAdoptedNumber != petList.size) {
            repeat(model.visibleCards) { visibleIndex ->
                val pet = model.cardVisible(visibleIndex)
                isPetAdopting = -1
                val cardZIndex = TOP_Z_INDEX - visibleIndex
                val cardModifier = events
                    .makeCardModifier(
                        coroutineScope,
                        TOP_CARD_INDEX,
                        visibleIndex,
                        onDrag = { amount ->
                            isPetAdopting = when {
                                amount > 0 -> {
                                    1
                                }

                                amount < 0 -> {
                                    0
                                }

                                else -> {
                                    -1
                                }
                            }
                            println(isPetAdopting)
                        },
                        acceptPet = { petIsAccepted ->
                            if (petIsAccepted)
                                petViewModel.updatePet(petList[topCardIndex], true)
                            else
                                petViewModel.updatePet(petList[topCardIndex], false)
                        })
                    .align(Alignment.TopCenter)
                    .zIndex(cardZIndex)
                    .size(cardWidth, cardHeight)

                CardView(
                    modifier = cardModifier,
                    content = {
                        CardsContent(
                            isPetAdopting,
                            pet
                        )
                    }
                )
                events.cardSwipe.backToInitialState(coroutineScope)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pets_not_found),
                    contentDescription = "pets empty state",
                    contentScale = ContentScale.Inside
                )
                Text(
                    "No se ha encontrado más mascotas",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonColors(
                        containerColor = Color.Magenta,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.White,
                    ),
                    onClick = {
                        petViewModel.cleanAllPets()
                    }) {
                    Text("Olvidar todas las mascotas".uppercase())
                }
            }
        }
        //Imagen de <a href="https://www.freepik.es/vector-gratis/adopta-mensaje-concepto-mascota-lindo-perro_7764386.htm#query=adopt%20a%20pet&position=7&from_view=search&track=ais&uuid=087856db-2569-4633-af36-6a95dd5af977">Freepik</a>
    }
}