package com.example.plague.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.plague.GameViewModel
import com.example.plague.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController, viewModel: GameViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("resume") { ResumeScreen(navController, viewModel) }
        composable("game") { GameScreen(navController, viewModel) }
        composable("shop") { ShopScreen(navController, viewModel) }
        composable("defeat") { DefeatScreen(navController, viewModel) }
        composable("victory") { VictoryScreen(navController, viewModel) }
        composable("globalStats") { GlobalStatsScreen(navController) }
        composable("newGame") { NewGameScreen(navController, viewModel) }
        composable("pause") { PauseScreen(navController, viewModel) }
        composable("about") { AboutScreen(navController) }
        composable("currentStats") { CurrentStatsScreen(navController, viewModel) }
    }
}
