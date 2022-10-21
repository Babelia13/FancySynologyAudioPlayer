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

package com.babelia.fansyn.audiostation.ui

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babelia.fansyn.BaseTypedViewModel
import com.babelia.fansyn.audiostation.GetAudioStationAlbumSongsAction
import com.babelia.fansyn.audiostation.SynologyAudioStationState
import com.babelia.fansyn.audiostation.SynologyAudioStationStore
import com.babelia.fansyn.network.models.AudioStationAlbum
import com.babelia.fansyn.network.models.AudioStationSong
import com.babelia.fansyn.utils.AlbumNotFoundException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import mini.*
import org.kodein.di.instance

/**
 * [ViewModel] used to represent a given AudioStation album.
 */
class AudioStationAlbumSongsViewModel(app: Application,
                                      private val albumBasicData: AlbumBasicData)
    : BaseTypedViewModel<AlbumBasicData>(app, albumBasicData) {

    private val dispatcher: Dispatcher by instance()
    private val synologyAudioStationStore: SynologyAudioStationStore by instance()

    private val _audioStationAlbumDetails =
        MutableStateFlow<Resource<AudioStationAlbumDetailsViewData>>(Resource.idle())
    val audioStationAlbumDetails:
        StateFlow<Resource<AudioStationAlbumDetailsViewData>> get() = _audioStationAlbumDetails

    init {
        getAudioStationAlbumSongs()
    }

    /**
     * Ask for a Synology AudioStation album songs list and saved it in a [StateFlow] to be shown in the UI.
     */
    private fun getAudioStationAlbumSongs() {
        viewModelScope.launch {
            dispatcher.dispatch(GetAudioStationAlbumSongsAction(
                albumBasicData.albumName, albumBasicData.albumArtistName))
        }

        synologyAudioStationStore.flow()
            .select { AudioStationAlbumDetailsViewData.from(it, albumBasicData) }
            .takeUntil { it.isTerminal }
            .onEach { albumsResource ->
                _audioStationAlbumDetails.value = albumsResource
            }
            .launchIn(viewModelScope)
    }
}

/**
 * Basic data used to identify an album.
 */
@Parcelize
data class AlbumBasicData(val albumName: String, val albumArtistName: String) : Parcelable {
    companion object {
        @Suppress("UndocumentedPublicFunction")
        fun from(audioStationAlbum: AudioStationAlbum) =
            AlbumBasicData(
                albumName = audioStationAlbum.name,
                albumArtistName = audioStationAlbum.albumArtist
            )
    }
}

@Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")
data class AudioStationAlbumDetailsViewData(val album: AudioStationAlbum, val songs: List<AudioStationSong>) {
    companion object {
        fun from(state: SynologyAudioStationState,
                 albumBasicData: AlbumBasicData): Resource<AudioStationAlbumDetailsViewData> {
            val audioStationAlbum = state.audioStationAlbums
                                        ?.first {
                                            it.name == albumBasicData.albumName
                                            && it.albumArtist == albumBasicData.albumArtistName
                                        }
                                    ?: return Resource.failure(AlbumNotFoundException(albumBasicData.albumName))

            with(state.getAudioStationAlbumSongsTasks) {
                return when {
                    isSuccess ->
                        state.audioStationAlbumSongs?.let {
                            Resource.success(AudioStationAlbumDetailsViewData(audioStationAlbum, it))
                        } ?: Resource.empty()
                    isFailure -> Resource.failure(exceptionOrNull())
                    isLoading -> Resource.loading()
                    else -> Resource.empty()
                }
            }
        }
    }
}