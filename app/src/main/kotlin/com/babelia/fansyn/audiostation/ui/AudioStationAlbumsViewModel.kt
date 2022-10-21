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
import androidx.lifecycle.viewModelScope
import com.babelia.fansyn.BaseViewModel
import com.babelia.fansyn.audiostation.GetAudioStationAlbumsAction
import com.babelia.fansyn.audiostation.SynologyAudioStationState
import com.babelia.fansyn.audiostation.SynologyAudioStationStore
import com.babelia.fansyn.network.models.AudioStationAlbum
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mini.*
import org.kodein.di.instance

/**
 * [ViewModel] used to represent a given Synology Audio Station albums data.
 */
class AudioStationAlbumsViewModel(app: Application) : BaseViewModel(app) {

    private val dispatcher: Dispatcher by instance()
    private val synologyAudioStationStore: SynologyAudioStationStore by instance()

    private val _audioStationAlbums = MutableStateFlow<Resource<AudioStationAlbumViewData>>(Resource.idle())
    val audioStationAlbums: StateFlow<Resource<AudioStationAlbumViewData>> get() = _audioStationAlbums

    private val _isGridViewEnabled = MutableStateFlow(true)
    val isGridViewEnabled: StateFlow<Boolean> get() = _isGridViewEnabled

    init {
        getAudioStationAlbums()
    }

    /**
     * Ask for a Synology AudioStation albums list and saved it in a [StateFlow] to be shown in the UI.
     */
    private fun getAudioStationAlbums() {
        viewModelScope.launch {
            dispatcher.dispatch(GetAudioStationAlbumsAction)
        }

        synologyAudioStationStore.flow()
            .select { AudioStationAlbumViewData.from(it) }
            .takeUntil { it.isTerminal }
            .onEach { albumsResource ->
                _audioStationAlbums.value = albumsResource
            }
            .launchIn(viewModelScope)
    }

    /**
     * Change between grid and list view.
     */
    fun toggleGridViewEnabled() {
        _isGridViewEnabled.value = !_isGridViewEnabled.value
    }
}

@Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")
data class AudioStationAlbumViewData(val albums: List<AudioStationAlbum>) {
    companion object {
        fun from(state: SynologyAudioStationState): Resource<AudioStationAlbumViewData> =
            with(state.getAudioStationAlbumsTask) {
                when {
                    isSuccess ->
                        state.audioStationAlbums?.let {
                            Resource.success(AudioStationAlbumViewData(it))
                        } ?: Resource.empty()
                    isFailure -> Resource.failure(exceptionOrNull())
                    isLoading -> Resource.loading()
                    else -> Resource.empty()
                }
            }
    }
}