package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.ui.theme.*

@Composable
fun GlobalStatsScreen(navController: NavController) {
    BackHandler { navController.navigate("home") { popUpTo("home") { inclusive = false } } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircleIconButton(onClick = {
                    navController.navigate("home") { popUpTo("home") { inclusive = false } }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = Background)
                }
                Text(
                    "ОБЩАЯ СТАТИСТИКА",
                    color = GreenAccent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            StatItem("Всего ДНК собрано", "12 350")
            StatItem("Куплено улучшений", "23")
            StatItem("Заражено всего", "45 200 000")
            StatItem("Игр сыграно", "5")
            StatItem("Побед", "2")
            StatItem("Поражений", "3")
            StatItem("Лучшее время", "00:45:12")
        }
    }
}
