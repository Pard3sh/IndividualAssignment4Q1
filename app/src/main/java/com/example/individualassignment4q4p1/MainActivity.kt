package com.example.individualassignment4q4p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    // TODO
}