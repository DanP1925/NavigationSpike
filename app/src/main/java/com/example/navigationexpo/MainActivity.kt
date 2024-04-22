package com.example.navigationexpo

import android.os.Bundle
import android.os.Parcelable
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
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.IntScreenDestination
import com.ramcosta.composedestinations.generated.destinations.MainScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ObjectScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SecondScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StringScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ThirdScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationExpoTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

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

@Destination<RootGraph>
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


@Destination<RootGraph>
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

@Parcelize
class CustomObject(
    val number: Int,
    val name: String
) : Parcelable

@Destination<RootGraph>
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

@Destination<RootGraph>
@Composable
fun SecondScreen(
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
                text = "Segunda pantalla",
                fontSize = 28.sp
            )
            Button(onClick = { navigator.navigate(ThirdScreenDestination) }) {
                Text(text = "Ir a la tercera pantalla")
            }
        }
    }
}

@Destination<RootGraph>
@Composable
fun ThirdScreen(
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
                text = "Tercera pantalla",
                fontSize = 28.sp
            )
            Button(onClick = { navigator.popBackStack(MainScreenDestination.route, false) }) {
                Text(text = "Regresar al Inicio")
            }
        }
    }
}
