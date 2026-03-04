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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.plague.ui.theme.Background

@Composable
fun HomeScreen(navController: NavController, viewModel: GameViewModel) {
    val state by viewModel.state.collectAsState()
    var showContinueDialog by remember { mutableStateOf(false) }

    BackHandler {}

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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                CircleIconButton(
                    onClick = { navController.navigate("globalStats") }
                ) {
                    Icon(Icons.Default.BarChart, contentDescription = "Статистика", tint = Background)
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ЧУМААААА",
                    color = Color(0xFF39FF14),
                    fontSize = 54.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "РАСПРОСТРАНИТЕ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center
                )
            }

            Box(contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            if (state.gameStarted) {
                                showContinueDialog = true
                            } else {
                                navController.navigate("newGame")
                            }
                        },
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Играть",
                            modifier = Modifier.size(60.dp),
                            tint = Color(0xFF5D7B9C)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircleIconButton(
                    onClick = { navController.navigate("about") },
                    size = 44.dp
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "О приложении",
                        tint = Background,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        if (showContinueDialog) {
            ContinueGameDialog(
                onContinue = {
                    showContinueDialog = false
                    viewModel.resumeGame()
                    navController.navigate("game")
                },
                onNewGame = {
                    showContinueDialog = false
                    viewModel.resetGame()
                    navController.navigate("newGame")
                },
                onDismiss = { showContinueDialog = false },
                elapsedSeconds = state.elapsedSeconds
            )
        }
    }
}

@Composable
fun ContinueGameDialog(
    onContinue: () -> Unit,
    onNewGame: () -> Unit,
    onDismiss: () -> Unit,
    elapsedSeconds: Long
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
                    text = "Продолжить последнюю сохраненную игру?",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Дата сохранения: 25.02.2026",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    val hours = elapsedSeconds / 3600
                    val minutes = (elapsedSeconds % 3600) / 60
                    val seconds = elapsedSeconds % 60
                    Text(
                        text = "Время игры: %02d:%02d:%02d".format(hours, minutes, seconds),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        onClick = onNewGame,
                        modifier = Modifier
                            .weight(1.1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color(0xFF7B2424)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Новая игра",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Surface(
                        onClick = onContinue,
                        modifier = Modifier
                            .weight(0.9f)
                            .height(48.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color(0xFFA5A5A5)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Да",
                                color = Color(0xFF2196F3),
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
