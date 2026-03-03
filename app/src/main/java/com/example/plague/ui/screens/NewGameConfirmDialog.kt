package com.example.plague.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.plague.ui.theme.*

@Composable
fun NewGameConfirmDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Начать новую игру?", color = Color.White, fontWeight = FontWeight.Bold) },
        text = { Text("Текущий прогресс будет сброшен.", color = Color.White.copy(alpha = 0.7f)) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = GreenAccent)
            ) {
                Text("Сброс", color = Background, fontWeight = FontWeight.Bold)
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
