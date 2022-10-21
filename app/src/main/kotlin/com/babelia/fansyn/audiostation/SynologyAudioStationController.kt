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

import com.babelia.fansyn.network.models.AudioStationAlbum
import com.babelia.fansyn.network.models.AudioStationSong
import mini.Resource

/**
 * Interface that Synology AudioStation API must comply to in order to work.
 */
interface SynologyAudioStationController {

    /**
     * Get the list of a albums stored in the NAS from the Synology REST API.
     */
    suspend fun getAudioStationAlbums(): Resource<List<AudioStationAlbum>>

    /**
     * Get the list of songs of a specific album stored in the NAS from the Synology REST API.
     */
    suspend fun getAudioStationAlbumSongs(albumName: String, albumArtist: String): Resource<List<AudioStationSong>>
}