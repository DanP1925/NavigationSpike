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