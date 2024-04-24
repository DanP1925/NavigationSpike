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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class MainScreen(
    private val modifier: Modifier = Modifier
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

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
                    Button(onClick = { navigator.push(StringScreen("Mango")) }) {
                        Text(text = "Pasar un String")
                    }
                    Button(onClick = { navigator.push(IntScreen(1234)) }) {
                        Text(text = "Pasar un Int")
                    }
                }
                Button(onClick = { navigator.push(ObjectScreen(CustomObject(2024, "Abril"))) }) {
                    Text(text = "Pasar un Objeto")
                }
                Button(onClick = { navigator.push(SecondScreen()) }) {
                    Text(text = "Ir a una siguiente pantalla")
                }
            }
        }
    }
}
