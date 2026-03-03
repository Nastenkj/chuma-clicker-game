package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.GameViewModel
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
            .background(Background)
    ) {
        WorldMapBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("МЕНЮ", color = GreenAccent, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                PauseButton(
                    icon = {
                        Icon(Icons.Default.PlayArrow, "Продолжить", tint = Background, modifier = Modifier.size(32.dp))
                    },
                    label = "Продолжить"
                ) {
                    viewModel.resumeGame()
                    navController.navigate("game") { popUpTo("game") { inclusive = false } }
                }
                PauseButton(
                    icon = {
                        Icon(Icons.Default.BarChart, "Статистика", tint = Background, modifier = Modifier.size(32.dp))
                    },
                    label = "Статистика"
                ) {
                    navController.navigate("currentStats")
                }
                PauseButton(
                    icon = {
                        Icon(Icons.Default.Refresh, "Сброс", tint = Background, modifier = Modifier.size(32.dp))
                    },
                    label = "Сбросить игру"
                ) {
                    showResetDialog = true
                }
                PauseButton(
                    icon = {
                        Icon(Icons.Default.Home, "Домой", tint = Background, modifier = Modifier.size(32.dp))
                    },
                    label = "Главное меню"
                ) {
                    showExitDialog = true
                }
                PauseButton(
                    icon = {
                        Icon(Icons.Default.Info, "О приложении", tint = Background, modifier = Modifier.size(32.dp))
                    },
                    label = "Информация"
                ) {
                    navController.navigate("about")
                }
            }
        }

        if (showExitDialog) {
            ExitToMenuDialog(
                onExit = {
                    showExitDialog = false
                    viewModel.resetGame()
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                },
                onCancel = { showExitDialog = false }
            )
        }

        if (showResetDialog) {
            NewGameConfirmDialog(
                onConfirm = {
                    showResetDialog = false
                    viewModel.resetGame()
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                },
                onCancel = { showResetDialog = false }
            )
        }
    }
}

@Composable
fun PauseButton(icon: @Composable () -> Unit, label: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircleIconButton(onClick = onClick, size = 64.dp) {
            icon()
        }
        Text(label, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}
