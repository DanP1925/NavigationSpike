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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

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

