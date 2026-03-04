package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.plague.GameViewModel
import com.example.plague.ui.theme.Background
import com.example.plague.ui.theme.BlueAccent
import com.example.plague.ui.theme.GreenAccent
import com.example.plague.ui.theme.Surface

@Composable
fun ResumeScreen(navController: NavController, viewModel: GameViewModel) {
    val state by viewModel.state.collectAsState()

    BackHandler {
        navController.navigate("home") {
            popUpTo("home") { inclusive = false }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Dialog(onDismissRequest = {
            navController.navigate("home") {
                popUpTo("home") { inclusive = false }
            }
        }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Surface
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Продолжить последнюю\nсохраненную игру?",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider(color = GreenAccent.copy(alpha = 0.3f))
                    Text(
                        text = "Болезнь: ${state.diseaseName}",
                        color = GreenAccent,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Сложность: ${state.difficulty}",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    HorizontalDivider(color = GreenAccent.copy(alpha = 0.3f))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.resumeGame()
                                navController.navigate("game") {
                                    popUpTo("home") { inclusive = false }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GreenAccent),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Да", color = Background, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = {
                                navController.navigate("newGame") {
                                    popUpTo("home") { inclusive = false }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BlueAccent),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Новая игра", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
