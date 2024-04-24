package com.example.navigationexpo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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

