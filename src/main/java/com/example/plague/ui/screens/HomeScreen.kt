package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import com.example.plague.GameViewModel
import com.example.plague.R
import com.example.plague.ui.theme.Background
import com.example.plague.ui.theme.GreenAccent

@Composable
fun HomeScreen(navController: NavController, viewModel: GameViewModel) {
    val state by viewModel.state.collectAsState()

    BackHandler { /* do nothing on home */ }

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
                                navController.navigate("game")
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
    }
}
