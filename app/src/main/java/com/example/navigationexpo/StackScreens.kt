package com.example.navigationexpo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext

interface SecondComponent {
    fun goToThirdScreen()
}

class DefaultSecondComponent(
    componentContext: ComponentContext,
    private val onThirdButtonSelected: () -> Unit,
) : SecondComponent {
    override fun goToThirdScreen() {
        onThirdButtonSelected()
    }
}

@Composable
fun SecondContent(
    component: SecondComponent,
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
                text = "Segunda pantalla",
                fontSize = 28.sp
            )
            Button(onClick = { component.goToThirdScreen() }) {
                Text(text = "Ir a la tercera pantalla")
            }
        }
    }
}

interface ThirdComponent {
    fun returnToMain()
}

class DefaultThirdComponent(
    componentContext: ComponentContext,
    private val popToMain: () -> Unit
) : ThirdComponent {
    override fun returnToMain() {
        popToMain()
    }
}

@Composable
fun ThirdContent(
    component: ThirdComponent,
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
                text = "Tercera pantalla",
                fontSize = 28.sp
            )
            Button(onClick = { component.returnToMain() }) {
                Text(text = "Regresar al Inicio")
            }
        }
    }
}
