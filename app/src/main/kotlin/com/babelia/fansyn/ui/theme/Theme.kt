/*
 *   Copyright 2022 Babelia
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.babelia.fansyn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import com.babelia.fansyn.utils.extensions.isLargeTablet
import com.babelia.fansyn.utils.extensions.isSmallTablet

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Amber200,
//    secondaryVariant = MidPurple,
//    background = DeepPurple,
//    surface = DeepPurple,
//    error = Red300,
//    onPrimary = DeepPurple,
//    onSecondary = DynamicLight,
//    onBackground = DynamicLight,
//    onSurface = DynamicLight,
//    onError = DynamicLight
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Amber200,
//    secondaryVariant = MidPurple,
//    background = DynamicLight,
//    surface = DynamicLight,
//    error = Red300,
//    onPrimary = DeepPurple,
//    onSecondary = DeepPurple,
//    onBackground = DeepPurple,
//    onSurface = DeepPurple,
//    onError = DynamicLight
)

/**
 * Object to get theme values from a [Composable].
 */
object AppTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current

    val resValues: ResourceValues
        @Composable
        get() = LocalAppResValues.current
}

val Dimens: Dimensions
    @Composable
    get() = AppTheme.dimens

private val LocalAppDimens = staticCompositionLocalOf { phoneDimensions }

/**
 * Composable to provide the proper dimens depending on the device screen dimensions.
 */
@Composable
fun ProvideDimens(dimensions: Dimensions,
                  content: @Composable () -> Unit) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

val ResValues: ResourceValues
    @Composable
    get() = AppTheme.resValues

private val LocalAppResValues = staticCompositionLocalOf { phoneResourceValues }

/**
 * Composable to provide the proper dimens depending on the device screen dimensions.
 */
@Composable
fun ProvideResValues(resourceValues: ResourceValues,
                     content: @Composable () -> Unit) {
    val resourceValuesSet = remember { resourceValues }
    CompositionLocalProvider(LocalAppResValues provides resourceValuesSet, content = content)
}

@Composable
@Suppress("UndocumentedPublicFunction")
fun FanSynTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val configuration = LocalConfiguration.current
    val dimensions = when {
        configuration.isLargeTablet() -> largeTabletDimensions
        configuration.isSmallTablet() -> smallTabletDimensions
        else -> phoneDimensions
    }
    val resourceValues = when {
        configuration.isLargeTablet() -> largeTabletResourceValues
        configuration.isSmallTablet() -> smallTabletResourceValues
        else -> phoneResourceValues
    }

    ProvideDimens(dimensions = dimensions) {
        ProvideResValues(resourceValues = resourceValues) {
            MaterialTheme(
                colors = colors,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }
    }
}