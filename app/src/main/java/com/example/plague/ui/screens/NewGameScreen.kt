package com.example.plague.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGameScreen(navController: NavController, viewModel: GameViewModel) {
    var diseaseName by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("Средний") }
    var expanded by remember { mutableStateOf(false) }
    val difficulties = listOf("Лёгкий", "Средний", "Сложный")

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleIconButton(onClick = {
                    navController.navigate("home") { popUpTo("home") { inclusive = false } }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = Background)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Новая игра", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = diseaseName,
                onValueChange = { diseaseName = it },
                label = { Text("Имя болезни", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GreenAccent,
                    unfocusedBorderColor = Gray
                ),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedDifficulty,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Сложность", color = Color.White.copy(alpha = 0.7f)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = GreenAccent,
                        unfocusedBorderColor = Gray
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Surface)
                ) {
                    difficulties.forEach { difficulty ->
                        DropdownMenuItem(
                            text = { Text(difficulty, color = Color.White) },
                            onClick = {
                                selectedDifficulty = difficulty
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (diseaseName.isNotBlank()) {
                        viewModel.startNewGame(diseaseName.trim(), selectedDifficulty)
                        navController.navigate("game") {
                            popUpTo("home") { inclusive = false }
                        }
                    }
                },
                enabled = diseaseName.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = GreenAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("PLAY", color = Background, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}
