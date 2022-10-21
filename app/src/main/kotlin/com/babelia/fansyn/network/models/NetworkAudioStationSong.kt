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

/**
 * Network representation of a [AudioStationSong] object retrieved in an API request.
 */
data class NetworkAudioStationSong(
    val id: String,
    val path: String,
    val title: String,
    val type: String
)

@Suppress("UndocumentedPublicFunction")
fun NetworkAudioStationSong.toAudioStationSong() =
    AudioStationSong(
        id = id,
        path = path,
        title = title,
        fileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")),
        type = type
    )