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

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.babelia.fansyn.audiostation.ui.*
import com.babelia.fansyn.ui.theme.FanSynTheme
import mini.Resource

@Preview(name = "Album songs", showBackground = true)
@Composable
private fun AlbumSongsPreview() {
    val albumsResource = Resource.success(AudioStationAlbumDetailsViewData(
        AlbumsMocks.getAlbumsMock().first(),
        AlbumsMocks.getAlbumSongsMock()))
    FanSynTheme {
        AlbumSongs(albumsResource) {}
    }
}

@Preview(name = "Album song content", showBackground = true)
@Composable
private fun SongContentPreview() {
    FanSynTheme {
        SongContent(AlbumsMocks.getAlbumSongsMock().first()) {}
    }
}
