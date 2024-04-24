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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.IntScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ObjectScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SecondScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StringScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination<RootGraph>(start = true)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
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
                Button(onClick = { navigator.navigate(StringScreenDestination("Mango")) }) {
                    Text(text = "Pasar un String")
                }
                Button(onClick = { navigator.navigate(IntScreenDestination(2024)) }) {
                    Text(text = "Pasar un Int")
                }
            }
            Button(onClick = {
                navigator.navigate(
                    ObjectScreenDestination(
                        CustomObject(
                            2024,
                            "Abril"
                        )
                    )
                )
            }) {
                Text(text = "Pasar un Objeto")
            }
            Button(onClick = { navigator.navigate(SecondScreenDestination) }) {
                Text(text = "Ir a una siguiente pantalla")
            }
        }
    }
}
