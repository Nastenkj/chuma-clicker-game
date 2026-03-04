package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
            PlagueConfirmDialog(
                title = "Выйти в главное меню?",
                description = "Текущая игра будет сохранена",
                confirmText = "Выйти",
                onConfirm = {
                    showExitDialog = false
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                },
                onDismiss = { showExitDialog = false }
            )
        }

        if (showResetDialog) {
            PlagueConfirmDialog(
                title = "Начать новую игру?",
                description = "Текущая игра не сохранится",
                confirmText = "Сброс",
                onConfirm = {
                    showResetDialog = false
                    viewModel.resetGame()
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                },
                onDismiss = { showResetDialog = false }
            )
        }
    }
}

@Composable
fun PlagueConfirmDialog(
    title: String,
    description: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF1A1A1A).copy(alpha = 0.95f)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = description,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color(0xFFA5A5A5)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Отмена",
                                color = Color(0xFF2196F3),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))

                    Surface(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color(0xFF7B2424)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = confirmText,
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
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
