package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.GameViewModel
import com.example.plague.ui.theme.*

@Composable
fun DefeatScreen(navController: NavController, viewModel: GameViewModel) {
    BackHandler {
        viewModel.resetGame()
        navController.navigate("home") { popUpTo("home") { inclusive = true } }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            Text("ПОРАЖЕНИЕ", color = GreenAccent, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(16.dp))

            StatItem("Всего ДНК собрано", "1 250")
            StatItem("Куплено улучшений", "3")
            StatItem("Заражено человек", "15 000")
            StatItem("Прогресс лекарства", "100%")
            StatItem("Время игры", "00:12:34")

            Spacer(modifier = Modifier.height(24.dp))
            CircleIconButton(
                onClick = {
                    viewModel.resetGame()
                    navController.navigate("home") { popUpTo("home") { inclusive = true } }
                },
                size = 64.dp
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Домой",
                    tint = Background,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
