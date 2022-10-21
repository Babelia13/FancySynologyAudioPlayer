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

package com.babelia.fansyn

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.babelia.fansyn.navigation.AlbumsScreenType
import com.babelia.fansyn.navigation.MainNavGraph
import com.babelia.fansyn.ui.theme.FanSynTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Main content of the application.
 */
class MainActivity : BaseActivity() {

    companion object {
        /**
         * List of destinations that need to have a translucent status bar.
         */
        val translucentStatusBarDestinations = listOf(
            AlbumsScreenType.Albums.routeDefinition,
            AlbumsScreenType.AlbumDetails.routeDefinition,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FanSysApp()
        }
    }
}

@Suppress("UndocumentedPublicFunction")
@Composable
fun FanSysApp() {
    val navController = rememberNavController()
    FanSynTheme {
        // Update the system bars to be translucent
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = false
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
        }

        Scaffold { innerPaddingModifier ->
            MainNavGraph(
                modifier = Modifier.padding(innerPaddingModifier),
                navController = navController
            )
        }
    }
}