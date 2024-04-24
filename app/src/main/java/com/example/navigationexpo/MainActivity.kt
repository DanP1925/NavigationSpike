package com.example.navigationexpo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationexpo.ui.theme.NavigationExpoTheme
import dagger.hilt.android.AndroidEntryPoint

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


