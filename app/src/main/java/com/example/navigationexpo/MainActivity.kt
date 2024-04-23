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
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.navigationexpo.ui.theme.NavigationExpoTheme
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val root = DefaultRootComponent(
                componentContext = defaultComponentContext()
            )
            NavigationExpoTheme {
                RootContent(component = root, modifier = Modifier.fillMaxSize())
            }
        }
    }
}


interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class MainChild(val component: MainComponent) : Child()
        class StringChild(val component: StringComponent) : Child()
        class IntChild(val component: IntComponent) : Child()
        class ObjectChild(val component: ObjectComponent) : Child()

        class SecondChild(val component: SecondComponent) : Child()
        class ThirdChild(val component: ThirdComponent) : Child()
    }

}


class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.MainConfig,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.MainConfig -> RootComponent.Child.MainChild(mainComponent(componentContext))
            is Config.StringConfig -> RootComponent.Child.StringChild(
                stringComponent(componentContext, config)
            )

            is Config.IntConfig -> RootComponent.Child.IntChild(
                intComponent(componentContext, config)
            )

            is Config.ObjectConfig -> RootComponent.Child.ObjectChild(
                objectComponent(componentContext, config)
            )

            is Config.SecondConfig -> RootComponent.Child.SecondChild(
                secondComponent(componentContext)
            )

            is Config.ThirdConfig -> RootComponent.Child.ThirdChild(
                thirdComponent(componentContext)
            )
        }

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            onStringButtonSelected = { name ->
                navigation.push(Config.StringConfig(name))
            },
            onIntButtonSelected = { number ->
                navigation.push(Config.IntConfig(number))
            },
            onObjectButtonSelected = { customObject ->
                navigation.push(Config.ObjectConfig(customObject))
            },
            onSecondButtonSelected = {
                navigation.push(Config.SecondConfig)
            }
        )

    private fun stringComponent(
        componentContext: ComponentContext,
        config: Config.StringConfig
    ): StringComponent = DefaultStringComponent(
        componentContext = componentContext,
        name = config.name
    )

    private fun intComponent(
        componentContext: ComponentContext,
        config: Config.IntConfig
    ): IntComponent = DefaultIntComponent(
        componentContext = componentContext,
        number = config.number
    )

    private fun objectComponent(
        componentContext: ComponentContext,
        config: Config.ObjectConfig
    ): ObjectComponent = DefaultObjectComponent(
        componentContext = componentContext,
        customObject = config.custom
    )

    private fun secondComponent(
        componentContext: ComponentContext
    ): SecondComponent = DefaultSecondComponent(
        componentContext = componentContext,
        onThirdButtonSelected = {
            navigation.push(Config.ThirdConfig)
        }
    )

    private fun thirdComponent(
        componentContext: ComponentContext
    ): ThirdComponent = DefaultThirdComponent(componentContext = componentContext){
        navigation.popTo(index = 0)
    }

    @Parcelize
    private sealed interface Config : Parcelable {
        data object MainConfig : Config
        data class StringConfig(val name: String) : Config
        data class IntConfig(val number: Int) : Config
        data class ObjectConfig(val custom: CustomObject) : Config
        data object SecondConfig : Config
        data object ThirdConfig : Config
    }
}

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.MainChild -> MainContent(component = child.component)
            is RootComponent.Child.StringChild -> StringContent(component = child.component)
            is RootComponent.Child.IntChild -> IntContent(component = child.component)
            is RootComponent.Child.ObjectChild -> ObjectContent(component = child.component)
            is RootComponent.Child.SecondChild -> SecondContent(component = child.component)
            is RootComponent.Child.ThirdChild -> ThirdContent(component = child.component)
        }
    }
}

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

interface StringComponent {
    val name: String
}

class DefaultStringComponent(
    componentContext: ComponentContext,
    override val name: String
) : StringComponent

@Composable
fun StringContent(
    component: StringComponent,
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
                text = component.name,
                fontSize = 24.sp
            )
        }
    }
}

interface IntComponent {
    val number: Int
}

class DefaultIntComponent(
    componentContext: ComponentContext,
    override val number: Int
) : IntComponent

@Composable
fun IntContent(
    component: IntComponent,
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
                text = component.number.toString(),
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

interface SecondComponent {
    fun goToThirdScreen()
}

class DefaultSecondComponent(
    componentContext: ComponentContext,
    private val onThirdButtonSelected: () -> Unit,
) : SecondComponent {
    override fun goToThirdScreen() {
        onThirdButtonSelected()
    }
}

@Composable
fun SecondContent(
    component: SecondComponent,
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
            Button(onClick = { component.goToThirdScreen() }) {
                Text(text = "Ir a la tercera pantalla")
            }
        }
    }
}

interface ThirdComponent {
    fun returnToMain()
}

class DefaultThirdComponent(
    componentContext: ComponentContext,
    private val popToMain: () -> Unit
) : ThirdComponent {
    override fun returnToMain() {
        popToMain()
    }
}

@Composable
fun ThirdContent(
    component: ThirdComponent,
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
            Button(onClick = { component.returnToMain() }) {
                Text(text = "Regresar al Inicio")
            }
        }
    }
}
