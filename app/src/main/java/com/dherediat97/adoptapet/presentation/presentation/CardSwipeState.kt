package com.dherediat97.adoptapet.presentation.presentation

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dherediat97.adoptapet.data.Pet
import java.util.Locale

enum class CardSwipeState {
    INITIAL, SWIPED, DRAGGING
}

@Composable
fun DraggableCard(isFront: Boolean, pet: Pet) {
    if (isFront) {
        Image(
            painter = painterResource(id = pet.picturePet),
            contentDescription = "pet image front",
            alpha = 0.6f,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.padding(24.dp)) {
                //Name pet
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxSize(0.90f)
                ) {
                    Text(
                        text = pet.name,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
                //Basic Pet Info
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Filled.DateRange),
                        contentDescription = "age pet icon",
                        tint = Color.White,
                    )
                    Text("Edad: ${pet.age}", color = Color.White)
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                    Icon(
                        painter =
                        if (pet.gender.lowercase(Locale.ROOT) == "macho") rememberVectorPainter(
                            image = Icons.Filled.Male
                        )
                        else rememberVectorPainter(
                            image = Icons.Filled.Female
                        ),
                        tint = Color.White,
                        contentDescription = "sex pet icon"
                    )
                    Text(pet.gender, color = Color.White)
                }

            }
        }
    } else {
        Image(
            painter = painterResource(id = pet.picturePet),
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