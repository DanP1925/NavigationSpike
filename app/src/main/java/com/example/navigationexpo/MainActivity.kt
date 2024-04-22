package com.example.navigationexpo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.navigationexpo.ui.theme.NavigationExpoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationExpoTheme {
            }
        }
    }
}

@Composable
fun MainScreen(
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
                Button(onClick = { }) {
                    Text(text = "Pasar un String")
                }
                Button(onClick = { }) {
                    Text(text = "Pasar un Int")
                }
            }
            Button(onClick = { }) {
                Text(text = "Pasar un Objeto")
            }
            Button(onClick = { }) {
                Text(text = "Ir a una siguiente pantalla")
            }
        }
    }
}

@Composable
fun StringScreen(
    name: String,
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
                text = name,
                fontSize = 24.sp
            )
        }
    }
}


@Composable
fun IntScreen(
    number: Int,
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
                text = number.toString(),
                fontSize = 24.sp
            )
        }
    }
}

data class CustomObject(
    val number: Int,
    val name: String
)

@Composable
fun ObjectScreen(
    customObject: CustomObject,
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
                text = "Pantalla con Object",
                fontSize = 28.sp
            )
            Text(
                text = customObject.name,
                fontSize = 24.sp
            )
            Text(
                text = customObject.number.toString(),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun SecondScreen(
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
            Button(onClick = { }) {
                Text(text = "Ir a la tercera pantalla")
            }
        }
    }
}

@Composable
fun ThirdScreen(
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
            Button(onClick = { }) {
                Text(text = "Regresar al Inicio")
            }
        }
    }
}
