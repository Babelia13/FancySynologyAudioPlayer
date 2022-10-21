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

package com.babelia.fansyn.network

import com.babelia.fansyn.debug.previews.AlbumsMocks
import com.babelia.fansyn.network.models.*
import kotlinx.coroutines.delay

/**
 * Fake class for using while developing to get mocked data.
 */
class SynologyFakeApi : SynologyApi {

    override suspend fun getAudioStationAlbums(api: String,
                                               version: Int,
                                               method: String,
                                               limit: Int,
                                               library: String): AudioStationAlbumsResponse {
        mockServerWork()
        return AudioStationAlbumsResponse(
            data = AudioStationAlbumsResponseData(
                AlbumsMocks.getAlbumsMock().map { it.toNetworkAudioStationAlbum() }, 0, 0),
            error = null,
            success = true)
    }

    override suspend fun getAudioStationAlbumSongs(api: String,
                                                   version: Int,
                                                   method: String,
                                                   limit: Int,
                                                   library: String,
                                                   albumName: String,
                                                   albumArtist: String): AudioStationAlbumSongsResponse {
        mockServerWork()
        return AudioStationAlbumSongsResponse(
            data = AudioStationAlbumSongsResponseData(
                0, AlbumsMocks.getAlbumSongsMock().map { it.toNetworkAudioStationSong() }, 0),
            error = null,
            success = true)
    }

    /**
     * Random delay of 1-3 seconds to simulate server work.
     */
    @Suppress("MagicNumber")
    private suspend fun mockServerWork() = delay((1000L..3000L).random())

}
