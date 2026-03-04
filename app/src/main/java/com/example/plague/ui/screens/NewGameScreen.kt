package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.GameViewModel
import com.example.plague.R
import com.example.plague.ui.theme.*

@Composable
fun NewGameScreen(navController: NavController, viewModel: GameViewModel) {
    var diseaseName by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("Средний") }
    val difficulties = listOf("Лёгкий", "Средний", "Сложный")
    val displayDifficulties = listOf("легко", "средне", "сложно")

    BackHandler { navController.navigate("home") { popUpTo("home") { inclusive = false } } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F171E))
    ) {
        Image(
            painter = painterResource(id = R.drawable.game_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Новая игра",
                color = Color.White,
                fontSize = 54.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(60.dp))

            TextField(
                value = diseaseName,
                onValueChange = { diseaseName = it },
                placeholder = {
                    Text(
                        "Введите имя болезни",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(64.dp)
                    .clip(RoundedCornerShape(32.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(80.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White.copy(alpha = 0.4f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "СЛОЖНОСТЬ",
                        color = Color(0xFF4DABF7),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Thin,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.White,
                                offset = Offset(1f, 1f),
                                blurRadius = 3f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF78AEE7)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        difficulties.forEachIndexed { index, difficulty ->
                            val isSelected = selectedDifficulty == difficulty
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(if (isSelected) 2.dp else 0.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) Color(0xFF98E971) else Color.Transparent)
                                    .clickable { selectedDifficulty = difficulty },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = displayDifficulties[index],
                                    color = if (isSelected) Color(0xFF4A4A4A) else Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Surface(
                modifier = Modifier
                    .size(130.dp)
                    .clickable(enabled = diseaseName.isNotBlank()) {
                        viewModel.startNewGame(diseaseName.trim(), selectedDifficulty)
                        navController.navigate("game") {
                            popUpTo("home") { inclusive = false }
                        }
                    },
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "PLAY",
                        modifier = Modifier.size(80.dp),
                        tint = Color(0xFF5D7B9C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
