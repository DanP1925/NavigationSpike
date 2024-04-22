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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.navigationexpo.ui.theme.NavigationExpoTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationExpoTheme {
                Navigator(screen = MainScreen())
            }
        }
    }
}

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

data class StringScreen(
    val name: String,
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
}


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

data class CustomObject(
    val number: Int,
    val name: String
)

class ObjectViewModel(
    customObject: CustomObject
) : ViewModel() {

    private val _customObject: MutableStateFlow<CustomObject> =
        MutableStateFlow(customObject)
    val customObject = _customObject.asStateFlow()


}

data class ObjectScreen(
    val customObject: CustomObject,
    private val modifier: Modifier = Modifier
) : Screen {

    @Composable
    override fun Content() {

        val screenModel = viewModel<ObjectViewModel>(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ObjectViewModel(customObject) as T
            }
        })

        val vmCustomObject = screenModel.customObject.collectAsState()

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
                    text = vmCustomObject.value.name,
                    fontSize = 24.sp
                )
                Text(
                    text = vmCustomObject.value.number.toString(),
                    fontSize = 24.sp
                )
            }
        }
    }
}

class SecondScreen(
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
                    text = "Segunda pantalla",
                    fontSize = 28.sp
                )
                Button(onClick = { navigator.push(ThirdScreen()) }) {
                    Text(text = "Ir a la tercera pantalla")
                }
            }
        }
    }
}

class ThirdScreen(
    val modifier: Modifier = Modifier
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
                    text = "Tercera pantalla",
                    fontSize = 28.sp
                )
                Button(onClick = { navigator.popUntil { it == MainScreen::key } }) {
                    Text(text = "Regresar al Inicio")
                }
            }
        }
    }
}
