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

import com.babelia.fansyn.BuildConfig
import com.babelia.fansyn.network.models.AudioStationAlbumSongsResponse
import com.babelia.fansyn.network.models.AudioStationAlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Class in charge of the communication with the Synology API using Retrofit.
 * All the request but the login one needs _sid as query param. It is added automatically using
 * [RequestInterceptor].
 */
@Suppress("UndocumentedPublicFunction")
interface SynologyApi {

    companion object {
        const val SYNOLOGY_AUDIO_STATION_ALBUM_API = "SYNO.AudioStation.Album"
        const val SYNOLOGY_AUDIO_STATION_SONG_API = "SYNO.AudioStation.Song"
        const val SYNOLOGY_AUDIO_STATION_COVER_API = "SYNO.AudioStation.Cover"

        fun getAlbumCoverUrl(sid: String,
                             albumName: String,
                             albumArtistName: String): String {
            return "${BuildConfig.SYNOLOGY_API_BASE_URL}webapi/AudioStation/cover.cgi" +
                   "?api=$SYNOLOGY_AUDIO_STATION_COVER_API" +
                   "&_sid=$sid" +
                   "&version=3" +
                   "&method=getcover" +
                   "&album_name=$albumName" +
                   "&album_artist_name=$albumArtistName"
        }
    }

    @GET("/webapi/AudioStation/album.cgi")
    suspend fun getAudioStationAlbums(@Query("api") api: String = SYNOLOGY_AUDIO_STATION_ALBUM_API,
                                      @Query("version") version: Int = 3,
                                      @Query("method") method: String = "list",
                                      @Query("limit") limit: Int = 10000,
                                      @Query("library") library: String = "all"): AudioStationAlbumsResponse

    @GET("/webapi/AudioStation/song.cgi")
    suspend fun getAudioStationAlbumSongs(@Query("api") api: String = SYNOLOGY_AUDIO_STATION_SONG_API,
                                          @Query("version") version: Int = 3,
                                          @Query("method") method: String = "list",
                                          @Query("limit") limit: Int = 10000,
                                          @Query("library") library: String = "shared",
                                          @Query("album") albumName: String,
                                          @Query("album_artist") albumArtist: String): AudioStationAlbumSongsResponse
}
