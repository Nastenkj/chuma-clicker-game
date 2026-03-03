package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plague.GameViewModel
import com.example.plague.ui.theme.*

@Composable
fun CurrentStatsScreen(navController: NavController, viewModel: GameViewModel) {
    val state by viewModel.state.collectAsState()

    BackHandler { navController.popBackStack() }

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
                CircleIconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = Background)
                }
                Text("СТАТИСТИКА", color = GreenAccent, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            StatItem("Болезнь", state.diseaseName.ifEmpty { "—" })
            StatItem("Сложность", state.difficulty)
            StatItem("ДНК очков", state.dnaPoints.toString())
            StatItem("Заражено", state.infectedCount.toString())
            StatItem("Прогресс лекарства", "${(state.cureProgress * 100).toInt()}%")
            StatItem("Клик улучшений", state.clickUpgradeCount.toString())
            StatItem("Сек. улучшений", state.secondUpgradeCount.toString())
            StatItem("Сопр. лекарству", state.cureResistanceCount.toString())
            val h = state.elapsedSeconds / 3600
            val m = (state.elapsedSeconds % 3600) / 60
            val s = state.elapsedSeconds % 60
            StatItem("Время игры", "%02d:%02d:%02d".format(h, m, s))
        }
    }
}
