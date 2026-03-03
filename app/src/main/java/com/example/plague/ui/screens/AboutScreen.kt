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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.ui.theme.*

@Composable
fun AboutScreen(navController: NavController) {
    BackHandler { navController.navigate("home") { popUpTo("home") { inclusive = false } } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("О ПРИЛОЖЕНИИ", color = GreenAccent, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            HorizontalDivider(color = GreenAccent.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))

            Text("Разработчики:", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
            listOf(
                "Румянцева Анастасия",
                "Сысун Михаил",
                "Крюкова Ксения",
                "Парусов Алекс",
                "Иванова Маргарита",
                "Барков Константин"
            ).forEach { name ->
                Text(name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.weight(1f))
            Text("v.1.0.0", color = Color.White.copy(alpha = 0.5f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            CircleIconButton(
                onClick = { navController.navigate("home") { popUpTo("home") { inclusive = false } } },
                size = 64.dp
            ) {
                Icon(Icons.Default.Home, contentDescription = "Домой", tint = Background, modifier = Modifier.size(32.dp))
            }
        }
    }
}
