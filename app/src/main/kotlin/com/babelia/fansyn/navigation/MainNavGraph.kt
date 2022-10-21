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

package com.babelia.fansyn.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.babelia.fansyn.audiostation.ui.*
import com.squareup.moshi.Moshi
import mini.kodein.android.compose.viewModel
import org.kodein.di.compose.androidContextDI
import org.kodein.di.compose.rememberInstance

@Suppress("UndocumentedPublicFunction")
@Composable
fun MainNavGraph(modifier: Modifier,
                 navController: NavHostController = rememberNavController(),
                 startDestinationScreenType: ScreenType = AlbumsScreenType.Albums) {

    val moshi: Moshi by rememberInstance()

    NavHost(navController, startDestination = startDestinationScreenType.routeDefinition) {
        addAlbumScreens(modifier, navController, moshi)
    }
}

private fun NavGraphBuilder.addAlbumScreens(modifier: Modifier,
                                            navController: NavController,
                                            moshi: Moshi) {

    composable(route = AlbumsScreenType.Albums.routeDefinition) {
        val audioStationAlbumsViewModel: AudioStationAlbumsViewModel by it.viewModel(androidContextDI())
        AlbumsScreen(modifier, audioStationAlbumsViewModel) { albumData ->
            navController.navigate(AlbumsScreenType.AlbumDetails.getRouteFor(moshi, albumData))
        }
    }

    composable(
        route = AlbumsScreenType.AlbumDetails.routeDefinition,
        arguments = listOf(navArgument(AlbumsScreenType.AlbumDetails.ALBUM_DATA_ARG) {
            type = AlbumDataNavType()
        })
    ) { backStackEntry ->
        val albumData = backStackEntry.arguments
            ?.getParcelable<AlbumBasicData>(AlbumsScreenType.AlbumDetails.ALBUM_DATA_ARG)
        // TODO: Don't force the not null and show a proper error
        val audioStationAlbumSongsViewModel: AudioStationAlbumSongsViewModel
            by backStackEntry.viewModel(androidContextDI(), albumData!!)
        AlbumSongsScreen(modifier, audioStationAlbumSongsViewModel) {
            navController.popBackStack()
        }
    }
}