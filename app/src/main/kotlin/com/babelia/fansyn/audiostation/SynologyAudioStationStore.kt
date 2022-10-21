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

import com.babelia.fansyn.network.NetworkModule
import com.babelia.fansyn.network.models.AudioStationAlbum
import com.babelia.fansyn.network.models.AudioStationSong
import kotlinx.coroutines.CancellationException
import mini.Dispatcher
import mini.Reducer
import mini.Store
import mini.Task
import mini.kodein.bindStore
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@Suppress("UndocumentedPublicClass")
data class SynologyAudioStationState(val getAudioStationAlbumsTask: Task = Task.idle(),
                                     val getAudioStationAlbumSongsTasks: Task = Task.idle(),
                                     val audioStationAlbums: List<AudioStationAlbum>? = null,
                                     val audioStationAlbumSongs: List<AudioStationSong>? = null) {

    override fun toString(): String = "SynologyAudioStationState(" +
                                      "getAudioStationAlbumsTask=$getAudioStationAlbumsTask, " +
                                      "getAudioStationAlbumSongsTasks=$getAudioStationAlbumSongsTasks, " +
                                      "audioStationAlbums=$audioStationAlbums" +
                                      "audioStationAlbumSongs=$audioStationAlbumSongs" +
                                      ")"
}

/**
 * Store in charge of handle Synology Audio Station state during the app's lifecycle.
 */
@Suppress("UndocumentedPublicFunction")
class SynologyAudioStationStore(private val synologyAudioStationController: SynologyAudioStationController,
                                private val dispatcher: Dispatcher) : Store<SynologyAudioStationState>() {

    /*
     * Manage the [CancellationException] so when the user moves to another screen, and the view model which dispatched
     * this action is cleared and all its coroutines are cancelled, if this process has not finished yet, we reset its
     * task to [Task.idle()]. This is needed because if not it would remain as [Task.loading()] forever, and would
     * prevent to be executed again if this action is dispatched again because it would be already `isLoading` state.
     */
    @Reducer
    suspend fun getAudioStationAlbumsList(action: GetAudioStationAlbumsAction) {
        if (state.getAudioStationAlbumsTask.isLoading) return
        setState(state.copy(getAudioStationAlbumsTask = Task.loading()))
        try {
            dispatcher.dispatch(OnTaskLoadingAction("getAudioStationAlbumsTask"))

            val audioStationAlbumsResource = synologyAudioStationController.getAudioStationAlbums()
            setState(
                state.copy(
                    audioStationAlbums = audioStationAlbumsResource.getOrNull(),
                    getAudioStationAlbumsTask = Task.success()
                )
            )
        } catch (e: CancellationException) {
            setState(state.copy(getAudioStationAlbumsTask = Task.idle()))
        }
    }

    @Reducer
    suspend fun getAudioStationAlbumSongs(action: GetAudioStationAlbumSongsAction) {
        if (state.getAudioStationAlbumSongsTasks.isLoading) return
        setState(state.copy(getAudioStationAlbumSongsTasks = Task.loading()))
        try {
            dispatcher.dispatch(OnTaskLoadingAction("getAudioStationAlbumSongsTasks"))

            val audioStationAlbumSongsResource = synologyAudioStationController.getAudioStationAlbumSongs(
                albumName = action.albumName,
                albumArtist = action.albumArtist
            )
            setState(
                state.copy(
                    audioStationAlbumSongs = audioStationAlbumSongsResource.getOrNull(),
                    getAudioStationAlbumSongsTasks = Task.success()
                )
            )
        } catch (e: CancellationException) {
            setState(state.copy(getAudioStationAlbumSongsTasks = Task.idle()))
        }
    }

}

@Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")
object SynologyAudioStationModule {
    fun create() = DI.Module("SynologyAudioStationModule") {
        bindStore { SynologyAudioStationStore(instance(), instance()) }
        bind<SynologyAudioStationController>() with singleton {
            SynologyAudioStationControllerImpl(instance(), instance(NetworkModule.SYNOLOGY_FAKE_API_TAG), instance())
        }
    }
}