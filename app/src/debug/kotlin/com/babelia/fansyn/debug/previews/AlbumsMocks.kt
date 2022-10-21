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

package com.babelia.fansyn.debug.previews

import com.babelia.fansyn.network.models.AudioStationAlbum
import com.babelia.fansyn.network.models.AudioStationSong

@Suppress("UndocumentedPublicClass", "MaxLineLength", "StringLiteralDuplication")
object AlbumsMocks {
    @Suppress("UndocumentedPublicFunction")
    fun getAlbumsMock() =
        listOf(
            AudioStationAlbum(
                albumArtist = "Beyoncé",
                artist = "Beyoncé",
                displayArtist = "Beyoncé",
                name = "RENAISSANCE",
                coverUrl = "https://m.media-amazon.com/images/I/61yO73-xTcL._SY450_.jpg",
                year = 2022
            ),
            AudioStationAlbum(
                albumArtist = "Christina Aguilera",
                artist = "Christina Aguilera",
                displayArtist = "Christina Aguilera",
                name = "AGUILERA",
                coverUrl = "https://i.scdn.co/image/ab67616d0000b273e141d09c251ab509c93a24dd",
                year = 2022
            ),
            AudioStationAlbum(
                albumArtist = "Bely Basarte",
                artist = "Bely Basarte",
                displayArtist = "Bely Basarte",
                name = "Desde Mi Otro Cuarto",
                coverUrl = "https://www.lahiguera.net/musicalia/artistas/bely_basarte/disco/8958/bely_basarte_desde_mi_otro_cuarto-portada.jpg",
                year = 2018
            ),
            AudioStationAlbum(
                albumArtist = "Beatriz Luengo",
                artist = "Beatriz Luengo",
                displayArtist = "Beatriz Luengo",
                name = "Cuerpo y Alma",
                coverUrl = "https://i.scdn.co/image/ab67616d0000b2733826ccd06bfbe61eb3a15070",
                year = 2018
            )
        )

    @Suppress("UndocumentedPublicFunction")
    fun getAlbumSongsMock() =
        listOf(
            AudioStationSong(
                id = "1",
                title = "1",
                fileName = "1",
                path = "/music/1.mp3",
                type = "file"
            ),
            AudioStationSong(
                id = "2",
                title = "2",
                fileName = "2",
                path = "/music/2.mp3",
                type = "file"
            ),
            AudioStationSong(
                id = "3",
                title = "3",
                fileName = "3",
                path = "/music/3.mp3",
                type = "file"
            ),
            AudioStationSong(
                id = "4",
                title = "4",
                fileName = "4",
                path = "/music/4.mp3",
                type = "file"
            )
        )
}