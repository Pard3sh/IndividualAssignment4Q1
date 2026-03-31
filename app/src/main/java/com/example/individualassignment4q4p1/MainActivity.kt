package com.example.individualassignment4q4p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.individualassignment4q4p1.ui.theme.IndividualAssignment4Q4P1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndividualAssignment4Q4P1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        var ballX by remember { mutableStateOf(0f) }
        var ballY by remember { mutableStateOf(0f) }
        val ballRadius = 40f
        Text(
            text = "X: $ballX Y: $ballY",
            color = Color.White,
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            // If ballX/Y are 0, start in the center
            val centerX = if (ballX == 0f) size.width / 2f else ballX
            val centerY = if (ballY == 0f) size.height / 2f else ballY

            drawCircle(
                color = Color.Red,
                radius = ballRadius,
                center = Offset(centerX, centerY)
            )
        }
    }
}