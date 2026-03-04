package com.example.plague.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.plague.ui.theme.*

@Composable
fun ExitToMenuDialog(onExit: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Выйти в главное меню?", color = Color.White, fontWeight = FontWeight.Bold) },
        text = { Text("Прогресс текущей игры будет утерян.", color = Color.White.copy(alpha = 0.7f)) },
        confirmButton = {
            Button(
                onClick = onExit,
                colors = ButtonDefaults.buttonColors(containerColor = GreenAccent)
            ) {
                Text("Выйти", color = Background, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Отмена", color = Color.White)
            }
        },
        containerColor = Surface
    )
}
