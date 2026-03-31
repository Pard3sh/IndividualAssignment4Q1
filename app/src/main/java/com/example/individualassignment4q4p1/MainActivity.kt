package com.example.individualassignment4q4p1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import com.example.individualassignment4q4p1.ui.theme.IndividualAssignment4Q4P1Theme

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var gyroscope: Sensor? = null

    private val tiltXState = mutableStateOf(0f)
    private val tiltYState = mutableStateOf(0f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        setContent {
            IndividualAssignment4Q4P1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameScreen(
                        tiltX = tiltXState.value,
                        tiltY = tiltYState.value
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        gyroscope?.also { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            tiltXState.value = event.values[0]
            tiltYState.value = event.values[1]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}

@Composable
fun GameScreen(
    tiltX: Float,
    tiltY: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF008000)), // green board
        contentAlignment = Alignment.Center
    ) {
        var ballX by rememberSaveable { mutableStateOf(0f) }
        var ballY by rememberSaveable { mutableStateOf(0f) }
        val ballRadius = 25f
        val sensitivity = 15f

        Text(
            text = "tiltX: %.3f  tiltY: %.3f".format(tiltX, tiltY),
            color = Color.White,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            if (ballX == 0f && ballY == 0f) {
                ballX = width / 2f
                ballY = height / 2f
            }

            val wallThickness = 25f

            val walls = listOf(
                // outer frame
                Rect(0f, 0f, width, wallThickness),                    // top
                Rect(0f, height - wallThickness, width, height),      // bottom
                Rect(0f, 0f, wallThickness, height),                  // left
                Rect(width - wallThickness, 0f, width, height),       // right

                // inner maze (roughly like the photo: a few random lines)
                Rect(width * 0.25f, height * 0.2f, width * 0.75f, height * 0.2f + wallThickness),
                Rect(width * 0.25f, height * 0.4f, width * 0.6f, height * 0.4f + wallThickness),
                Rect(width * 0.4f, height * 0.6f, width * 0.8f, height * 0.6f + wallThickness),
                Rect(width * 0.25f, height * 0.2f, width * 0.25f + wallThickness, height * 0.6f),
                Rect(width * 0.55f, height * 0.3f, width * 0.55f + wallThickness, height * 0.8f)
            )

            // Proposed movement
            val proposedX = ballX + (-tiltY * sensitivity)
            val proposedY = ballY + (tiltX * sensitivity)

            // Clamp to screen bounds
            var newX = proposedX.coerceIn(ballRadius + wallThickness, width - ballRadius - wallThickness)
            var newY = proposedY.coerceIn(ballRadius + wallThickness, height - ballRadius - wallThickness)

            // Simple collision
            fun collides(x: Float, y: Float): Boolean {
                val ballRect = Rect(
                    x - ballRadius,
                    y - ballRadius,
                    x + ballRadius,
                    y + ballRadius
                )
                return walls.any { wall -> wall.overlaps(ballRect) }
            }

            // Check X movement
            if (!collides(newX, ballY)) {
                ballX = newX
            }
            // Check Y movement
            if (!collides(ballX, newY)) {
                ballY = newY
            }

            // Draw walls
            walls.forEach { wall ->
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(wall.left, wall.top),
                    size = wall.size
                )
            }

            // Draw ball
            drawCircle(
                color = Color.White,
                radius = ballRadius,
                center = Offset(ballX, ballY)
            )
        }
    }
}