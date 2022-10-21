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

import android.os.Bundle
import androidx.navigation.NavType
import com.babelia.fansyn.audiostation.ui.AlbumBasicData
import com.babelia.fansyn.utils.extensions.fromJson
import com.babelia.fansyn.utils.extensions.toJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Suppress("UndocumentedPublicClass")
sealed class AlbumsScreenType : ScreenType {

    object Albums : AlbumsScreenType() {
        override val route = "albumsList"
    }

    object AlbumDetails : AlbumsScreenType() {
        const val ALBUM_DATA_ARG = "albumData"
        override val route = "${Albums.route}/"
        override val routeDefinition = "$route{$ALBUM_DATA_ARG}"

        @Suppress("UndocumentedPublicFunction")
        fun getRouteFor(moshi: Moshi, albumData: AlbumBasicData) = "$route${moshi.toJson(albumData)}"
    }
}

/**
 * [NavType] used to send the album basic data as an argument in the navigation graph.
 */
@Suppress("ExpressionBodySyntax")
class AlbumDataNavType : NavType<AlbumBasicData>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): AlbumBasicData? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): AlbumBasicData {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val fromJson: AlbumBasicData? = moshi.fromJson(value)
        return fromJson!!
    }

    override fun put(bundle: Bundle, key: String, value: AlbumBasicData) {
        bundle.putParcelable(key, value)
    }
}