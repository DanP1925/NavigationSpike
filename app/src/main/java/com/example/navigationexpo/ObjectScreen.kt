package com.example.navigationexpo

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import kotlinx.parcelize.Parcelize

@Parcelize
class CustomObject(
    val number: Int,
    val name: String
) : Parcelable

interface ObjectComponent {
    val customObject: CustomObject
}

class DefaultObjectComponent(
    componentContext: ComponentContext,
    override val customObject: CustomObject
) : ObjectComponent

@Composable
fun ObjectContent(
    component: ObjectComponent,
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
                text = component.customObject.name,
                fontSize = 24.sp
            )
            Text(
                text = component.customObject.number.toString(),
                fontSize = 24.sp
            )
        }
    }
}

