package com.example.navigationexpo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

data class IntScreen(
    val number: Int,
    val modifier: Modifier = Modifier
) : Screen {

    @Composable
    override fun Content() {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Pantalla con Int",
                    fontSize = 28.sp
                )
                Text(
                    text = number.toString(),
                    fontSize = 24.sp
                )
            }
        }
    }
}

