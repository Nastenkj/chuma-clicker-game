package com.example.plague.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plague.ui.theme.*

@Composable
fun CircleIconButton(
    onClick: () -> Unit,
    size: Dp = 56.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Composable
fun BiohazardBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val biohazardPositions = listOf(
            Offset(100f, 200f),
            Offset(800f, 400f),
            Offset(300f, 700f),
            Offset(900f, 1200f),
            Offset(150f, 1500f),
            Offset(700f, 1800f),
        )
        biohazardPositions.forEach { pos ->
            drawCircle(
                color = Color(0xFF39FF14).copy(alpha = 0.08f),
                radius = 60f,
                center = pos,
                style = Stroke(width = 2f)
            )
            drawCircle(
                color = Color(0xFF39FF14).copy(alpha = 0.05f),
                radius = 40f,
                center = pos
            )
        }
    }
}

@Composable
fun WorldMapBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        val continentColor = Color(0xFF39FF14).copy(alpha = 0.12f)
        val strokeWidth = 1.5f

        // North America
        val naPath = Path().apply {
            moveTo(w * 0.05f, h * 0.15f)
            lineTo(w * 0.25f, h * 0.10f)
            lineTo(w * 0.32f, h * 0.18f)
            lineTo(w * 0.28f, h * 0.35f)
            lineTo(w * 0.20f, h * 0.40f)
            lineTo(w * 0.10f, h * 0.38f)
            lineTo(w * 0.05f, h * 0.25f)
            close()
        }
        drawPath(naPath, continentColor, style = Stroke(strokeWidth))

        // South America
        val saPath = Path().apply {
            moveTo(w * 0.22f, h * 0.45f)
            lineTo(w * 0.30f, h * 0.43f)
            lineTo(w * 0.32f, h * 0.60f)
            lineTo(w * 0.25f, h * 0.70f)
            lineTo(w * 0.18f, h * 0.65f)
            lineTo(w * 0.17f, h * 0.50f)
            close()
        }
        drawPath(saPath, continentColor, style = Stroke(strokeWidth))

        // Europe
        val euPath = Path().apply {
            moveTo(w * 0.43f, h * 0.12f)
            lineTo(w * 0.55f, h * 0.10f)
            lineTo(w * 0.58f, h * 0.22f)
            lineTo(w * 0.50f, h * 0.28f)
            lineTo(w * 0.43f, h * 0.22f)
            close()
        }
        drawPath(euPath, continentColor, style = Stroke(strokeWidth))

        // Africa
        val afPath = Path().apply {
            moveTo(w * 0.45f, h * 0.32f)
            lineTo(w * 0.58f, h * 0.30f)
            lineTo(w * 0.62f, h * 0.50f)
            lineTo(w * 0.55f, h * 0.62f)
            lineTo(w * 0.45f, h * 0.55f)
            lineTo(w * 0.42f, h * 0.42f)
            close()
        }
        drawPath(afPath, continentColor, style = Stroke(strokeWidth))

        // Asia
        val asPath = Path().apply {
            moveTo(w * 0.58f, h * 0.10f)
            lineTo(w * 0.90f, h * 0.08f)
            lineTo(w * 0.95f, h * 0.25f)
            lineTo(w * 0.88f, h * 0.38f)
            lineTo(w * 0.70f, h * 0.40f)
            lineTo(w * 0.60f, h * 0.32f)
            lineTo(w * 0.58f, h * 0.20f)
            close()
        }
        drawPath(asPath, continentColor, style = Stroke(strokeWidth))

        // Australia
        val auPath = Path().apply {
            moveTo(w * 0.75f, h * 0.55f)
            lineTo(w * 0.90f, h * 0.52f)
            lineTo(w * 0.92f, h * 0.65f)
            lineTo(w * 0.80f, h * 0.68f)
            lineTo(w * 0.73f, h * 0.62f)
            close()
        }
        drawPath(auPath, continentColor, style = Stroke(strokeWidth))
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color.White.copy(alpha = 0.8f), fontSize = 15.sp)
        Text(value, color = GreenAccent, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
    HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
}
