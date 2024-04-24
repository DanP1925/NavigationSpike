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
import com.arkivanov.decompose.ComponentContext

interface MainComponent {
    fun goToStringScreen(name: String)
    fun goToIntScreen(number: Int)
    fun goToObjectScreen(customObject: CustomObject)
    fun goToSecondScreen()
}

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val onStringButtonSelected: (String) -> Unit,
    private val onIntButtonSelected: (Int) -> Unit,
    private val onObjectButtonSelected: (CustomObject) -> Unit,
    private val onSecondButtonSelected: () -> Unit
) : MainComponent {
    override fun goToStringScreen(name: String) {
        onStringButtonSelected(name)
    }

    override fun goToIntScreen(number: Int) {
        onIntButtonSelected(number)
    }

    override fun goToObjectScreen(customObject: CustomObject) {
        onObjectButtonSelected(customObject)
    }

    override fun goToSecondScreen() {
        onSecondButtonSelected()
    }
}

@Composable
fun MainContent(
    component: MainComponent,
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
                Button(onClick = { component.goToStringScreen("Mango") }) {
                    Text(text = "Pasar un String")
                }
                Button(onClick = { component.goToIntScreen(2024) }) {
                    Text(text = "Pasar un Int")
                }
            }
            Button(onClick = { component.goToObjectScreen(CustomObject(2024, "Abril")) }) {
                Text(text = "Pasar un Objeto")
            }
            Button(onClick = { component.goToSecondScreen() }) {
                Text(text = "Ir a una siguiente pantalla")
            }
        }
    }
}

