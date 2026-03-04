package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(navController: NavController, viewModel: GameViewModel) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler {
        navController.navigate("game") {
            popUpTo("game") { inclusive = false }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircleIconButton(onClick = {
                    navController.navigate("game") {
                        popUpTo("game") { inclusive = false }
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = Background)
                }
                Text(
                    text = "ЛАБОРАТОРИЯ",
                    color = GreenAccent,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = DarkGray
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = state.dnaPoints.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = "🧬", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ShopCard(
                title = "ЗАРАЖЕНИЙ ЗА КЛИК",
                icon = "👆",
                count = state.clickUpgradeCount,
                cost = 50,
                onClick = {
                    if (!viewModel.buyClickUpgrade()) {
                        scope.launch { snackbarHostState.showSnackbar("Недостаточно ДНК!") }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ShopCard(
                title = "ЗАРАЖЕНИЙ ЗА СЕКУНДУ",
                icon = "⏱️",
                count = state.secondUpgradeCount,
                cost = 50,
                onClick = {
                    if (!viewModel.buySecondUpgrade()) {
                        scope.launch { snackbarHostState.showSnackbar("Недостаточно ДНК!") }
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ShopCard(
                title = "ПРОТИВОДЕЙСТВИЕ ЛЕКАРСТВУ",
                icon = "🧪",
                count = state.cureResistanceCount,
                cost = 50,
                onClick = {
                    if (!viewModel.buyCureResistance()) {
                        scope.launch { snackbarHostState.showSnackbar("Недостаточно ДНК!") }
                    }
                }
            )
        }
    }
}

@Composable
fun ShopCard(title: String, icon: String, count: Int, cost: Int, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Gray,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = icon, fontSize = 32.sp)
                Column {
                    Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = "Куплено: $count шт.", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
            }
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = BlueAccent)
            ) {
                Text(text = "$cost 🧬", color = Color.White)
            }
        }
    }
}
