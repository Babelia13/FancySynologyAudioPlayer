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

package com.babelia.fansyn.network.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Model that represents the information related to an album in the Synology API.
 */
@Parcelize
data class AudioStationAlbum(
    @Json(name = "album_artist") val albumArtist: String,
    val artist: String,
    @Json(name = "display_artist") val displayArtist: String,
    val name: String,
    val year: Int,
    val coverUrl: String
) : Parcelable

@Suppress("UndocumentedPublicFunction")
fun AudioStationAlbum.toNetworkAudioStationAlbum() =
    NetworkAudioStationAlbum(
        albumArtist = albumArtist,
        artist = artist,
        displayArtist = displayArtist,
        name = name,
        year = year
    )