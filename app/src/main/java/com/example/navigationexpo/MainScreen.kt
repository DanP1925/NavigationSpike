package com.example.navigationexpo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    goToStringScreen: () -> Unit,
    goToIntScreen: () -> Unit,
    goToObjectScreen: () -> Unit,
    goToSecondScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Pantalla Principal",
                fontSize = 28.sp
            )
            Row {
                Button(onClick = { goToStringScreen() }) {
                    Text(text = "Pasar un String")
                }
                Button(onClick = { goToIntScreen() }) {
                    Text(text = "Pasar un Int")
                }
            }
            Button(onClick = { goToObjectScreen() }) {
                Text(text = "Pasar un Objeto")
            }
            Button(onClick = { goToSecondScreen() }) {
                Text(text = "Ir a una siguiente pantalla")
            }
        }
    }
}

