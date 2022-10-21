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

package com.babelia.fansyn.audiostation

import android.content.Context
import com.babelia.fansyn.BuildConfig
import com.babelia.fansyn.network.SynologyApi
import com.babelia.fansyn.network.models.AudioStationAlbum
import com.babelia.fansyn.network.models.AudioStationSong
import com.babelia.fansyn.network.models.toAudioStationAlbum
import com.babelia.fansyn.network.models.toAudioStationSong
import com.babelia.fansyn.utils.AlbumNotFoundException
import com.babelia.fansyn.utils.AlbumsListNotFoundException
import mini.Resource

/**
 * Implementation for [SynologyAudioStationController] using a REST API as backend.
 */
class SynologyAudioStationControllerImpl(
    private val synologyApi: SynologyApi,
    private val fakeSynologyApi: SynologyApi,
    private val context: Context
) : SynologyAudioStationController {

    override suspend fun getAudioStationAlbums(): Resource<List<AudioStationAlbum>> =
        try {
            val audioStationAlbumsResponse = synologyApi.getAudioStationAlbums()
            if (audioStationAlbumsResponse.success) {
                Resource.success(audioStationAlbumsResponse.data?.albums?.map {
                    it.toAudioStationAlbum(BuildConfig.SYNOLOGY_LOGIN_API_SID)
                })
            } else {
                val errorCode = audioStationAlbumsResponse.error?.code.toString()
                Resource.failure(AlbumsListNotFoundException(errorCode))
            }
        } catch (e: Exception) {
            Resource.failure(e)
        }

    override suspend fun getAudioStationAlbumSongs(
        albumName: String,
        albumArtist: String
    ): Resource<List<AudioStationSong>> =
        try {
            val audioStationAlbumSongsResponse = synologyApi.getAudioStationAlbumSongs(
                albumName = albumName, albumArtist = albumArtist
            )
            if (audioStationAlbumSongsResponse.success) {
                Resource.success(audioStationAlbumSongsResponse.data?.songs.map { it.toAudioStationSong() })
            } else {
                val errorCode = audioStationAlbumSongsResponse.error?.code.toString()
                Resource.failure(AlbumNotFoundException(albumName, errorCode))
            }
        } catch (e: Exception) {
            Resource.failure(e)
        }
}