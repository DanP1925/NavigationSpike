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

interface StringComponent {
    val name: String
}

class DefaultStringComponent(
    componentContext: ComponentContext,
    override val name: String
) : StringComponent

@Composable
fun StringContent(
    component: StringComponent,
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
                text = "Pantalla con String",
                fontSize = 28.sp
            )
            Text(
                text = component.name,
                fontSize = 24.sp
            )
        }
    }
}

