package com.example.plague.ui.screens

import com.example.plague.R
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.GameViewModel
import com.example.plague.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.scale

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val state by viewModel.state.collectAsState()

    BackHandler {
        viewModel.pauseGame()
        navController.navigate("pause")
    }

    LaunchedEffect(Unit) {
        if (!state.isTimerRunning && state.gameStarted) {
            viewModel.resumeGame()
        }
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
            alpha = 0.5f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    VerticalStatItem(
                        painter = painterResource(id = R.drawable.dnk),
                        value = state.dnaPoints.toString(),
                        labelColor = Color(0xFF64B5F6)
                    )
                    VerticalStatItem(
                        painter = painterResource(id = R.drawable.zar),
                        value = state.infectedCount.toString(),
                        labelColor = Color(0xFF64B5F6),
                        isCircleIcon = true
                    )
                    VerticalStatItem(
                        painter = painterResource(id = R.drawable.kolba),
                        value = "${(state.cureProgress * 100).toInt()}%",
                        labelColor = Color(0xFF64B5F6)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.Gray.copy(alpha = 0.8f),
                ) {
                    val hours = state.elapsedSeconds / 3600
                    val minutes = (state.elapsedSeconds % 3600) / 60
                    val seconds = state.elapsedSeconds % 60
                    Text(
                        text = "%02d:%02d:%02d".format(hours, minutes, seconds),
                        color = Color(0xFF2D2D2D),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 40.dp, vertical = 12.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(240.dp)
                    .border(4.dp, Color.White, CircleShape)
                    .clip(CircleShape)
                    .clickable { viewModel.addClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.knopka),
                    contentDescription = "Вирус",
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(1.5f),
                    contentScale = ContentScale.Crop
                )
            }

            // Bottom Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LargeCircleButton(onClick = { navController.navigate("currentStats") }) {
                    Icon(
                        Icons.Default.BarChart,
                        contentDescription = "Stats",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(48.dp)
                    )
                }
                LargeCircleButton(onClick = {
                    viewModel.pauseGame()
                    navController.navigate("pause")
                }) {
                    Icon(
                        Icons.Default.Pause,
                        contentDescription = "Pause",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(48.dp)
                    )
                }
                LargeCircleButton(onClick = { navController.navigate("shop") }) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Shop",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VerticalStatItem(
    icon: String? = null,
    painter: Painter? = null,
    value: String,
    labelColor: Color,
    isCircleIcon: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = if (isCircleIcon) {
                Modifier
                    .size(90.dp)
                    .background(Color.Black, CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            } else {
                Modifier.size(90.dp)
            },
            contentAlignment = Alignment.Center
        ) {
            if (painter != null) {
                Image(painter = painter, contentDescription = null, modifier = Modifier.size(80.dp))
            } else if (icon != null) {
                Text(text = icon, fontSize = 60.sp)
            }
        }
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = labelColor,
            modifier = Modifier.width(100.dp)
        ) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun LargeCircleButton(
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .size(80.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center, content = content)
    }
}
