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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationexpo.ui.theme.NavigationExpoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationExpoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen") {
                        MainScreen(
                            goToStringScreen = { navController.navigate("string_screen/Mango") },
                            goToIntScreen = { navController.navigate("int_screen/2024") },
                            goToObjectScreen = { navController.navigate("object_screen/5") },
                            goToSecondScreen = { navController.navigate("second_screen") }
                        )
                    }
                    composable(
                        "string_screen/{name}",
                        arguments = listOf(navArgument("name") {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        StringScreen(name = backStackEntry.arguments?.getString("name") ?: "")
                    }
                    composable(
                        "int_screen/{number}",
                        arguments = listOf(navArgument("number") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        IntScreen(number = backStackEntry.arguments?.getInt("number") ?: -1)
                    }
                    composable(
                        "object_screen/{objectId}",
                        arguments = listOf(navArgument("objectId") {
                            type = NavType.IntType
                        })
                    ) {
                        ObjectScreen()
                    }
                    composable("second_screen") {
                        SecondScreen({ navController.navigate("third_screen") })
                    }
                    composable("third_screen") {
                        ThirdScreen({ navController.popBackStack("main_screen", false) })
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    goToStringScreen: () -> Unit,
    goToIntScreen: () -> Unit,
    goToObjectScreen: () -> Unit,
    goToSecondScreen: () -> Unit,
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
                Button(onClick = { goToStringScreen() }) {
                    Text(text = "Pasar un String")
                }
                Button(onClick = { goToIntScreen() }) {
                    Text(text = "Pasar un Int")
                }
            }
            Button(onClick = { goToObjectScreen() }) {
                Text(text = "Pasar un Objeto")
            }
            Button(onClick = { goToSecondScreen() }) {
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

class ObjectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val objectId = checkNotNull(savedStateHandle["objectId"])

    private val _customObject: MutableStateFlow<CustomObject> =
        if (objectId == 5) {
            MutableStateFlow(CustomObject(2024, "Abril"))
        } else {
            MutableStateFlow(CustomObject(-1, ""))
        }
    val customObject = _customObject.asStateFlow()

}

@Composable
fun ObjectScreen(
    viewModel: ObjectViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val customObject = viewModel.customObject.collectAsState()

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
                text = customObject.value.name,
                fontSize = 24.sp
            )
            Text(
                text = customObject.value.number.toString(),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun SecondScreen(
    goToThirdScreen: () -> Unit,
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
            Button(onClick = { goToThirdScreen() }) {
                Text(text = "Ir a la tercera pantalla")
            }
        }
    }
}

@Composable
fun ThirdScreen(
    goToMainScreen: () -> Unit,
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
            Button(onClick = { goToMainScreen() }) {
                Text(text = "Regresar al Inicio")
            }
        }
    }
}
