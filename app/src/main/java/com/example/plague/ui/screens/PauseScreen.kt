package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.GameViewModel
import com.example.plague.R
import com.example.plague.ui.theme.*

@Composable
fun PauseScreen(navController: NavController, viewModel: GameViewModel) {
    var showExitDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    BackHandler {
        viewModel.resumeGame()
        navController.navigate("game") { popUpTo("game") { inclusive = false } }
    }

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
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "МЕНЮ",
                color = Color(0xFF39FF14),
                fontSize = 80.sp,
                fontWeight = FontWeight.Normal
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                LargePauseButton(onClick = {
                    viewModel.resumeGame()
                    navController.navigate("game") { popUpTo("game") { inclusive = false } }
                }) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Продолжить",
                        tint = Color(0xFF4469C1),
                        modifier = Modifier.size(60.dp)
                    )
                }

                LargePauseButton(onClick = { navController.navigate("currentStats") }) {
                    Icon(
                        Icons.Default.BarChart,
                        contentDescription = "Статистика",
                        tint = Color(0xFF4469C1),
                        modifier = Modifier.size(60.dp)
                    )
                }

                LargePauseButton(onClick = { showResetDialog = true }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Сброс",
                        tint = Color(0xFF4469C1),
                        modifier = Modifier.size(60.dp)
                    )
                }

                LargePauseButton(onClick = { showExitDialog = true }) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Домой",
                        tint = Color(0xFF4469C1),
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text("Выход") },
                text = { Text("Вы уверены, что хотите выйти в главное меню? Прогресс будет потерян.") },
                confirmButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        viewModel.resetGame()
                        navController.navigate("home") { popUpTo("home") { inclusive = true } }
                    }) { Text("Да") }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) { Text("Отмена") }
                }
            )
        }

        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text("Сброс") },
                text = { Text("Вы уверены, что хотите начать заново?") },
                confirmButton = {
                    TextButton(onClick = {
                        showResetDialog = false
                        viewModel.resetGame()
                        navController.navigate("home") { popUpTo("home") { inclusive = true } }
                    }) { Text("Да") }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) { Text("Отмена") }
                }
            )
        }
    }
}

@Composable
fun LargePauseButton(onClick: () -> Unit, content: @Composable BoxScope.() -> Unit) {
    Surface(
        modifier = Modifier
            .size(100.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Box(contentAlignment = Alignment.Center, content = content)
    }
}
