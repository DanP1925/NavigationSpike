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
import com.arkivanov.decompose.ComponentContext

interface IntComponent {
    val number: Int
}

class DefaultIntComponent(
    componentContext: ComponentContext,
    override val number: Int
) : IntComponent

@Composable
fun IntContent(
    component: IntComponent,
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
                text = "Pantalla con Int",
                fontSize = 28.sp
            )
            Text(
                text = component.number.toString(),
                fontSize = 24.sp
            )
        }
    }
}

