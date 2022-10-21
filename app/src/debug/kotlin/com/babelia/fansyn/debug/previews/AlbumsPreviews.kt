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
import com.babelia.fansyn.R
import com.babelia.fansyn.audiostation.ui.*
import com.babelia.fansyn.ui.components.ErrorScreen
import com.babelia.fansyn.ui.theme.FanSynTheme
import mini.Resource

@Preview(name = "Albums list", showBackground = true)
@Composable
private fun AlbumsListPreview() {
    val albumsResource = Resource.success(AudioStationAlbumViewData(AlbumsMocks.getAlbumsMock()))
    FanSynTheme {
        Albums(false, albumsResource) {}
    }
}

@Preview(name = "Albums grid", showBackground = true)
@Composable
private fun AlbumsGridPreview() {
    val albumsResource = Resource.success(AudioStationAlbumViewData(AlbumsMocks.getAlbumsMock()))
    FanSynTheme {
        Albums(true, albumsResource) {}
    }
}

@Preview(name = "Album list card", showBackground = true)
@Composable
private fun AlbumListCardPreview() {
    FanSynTheme {
        AlbumListCard(AlbumsMocks.getAlbumsMock().first()) {}
    }
}

@Preview(name = "Album grid card", showBackground = true)
@Composable
private fun AlbumGridCardPreview() {
    FanSynTheme {
        AlbumGridCard(AlbumsMocks.getAlbumsMock().first()) {}
    }
}

@Preview(name = "Full screen error", showBackground = true)
@Composable
private fun ErrorPreview() {
    FanSynTheme {
        ErrorScreen(R.string.get_albums_list_error_message)
    }
}

@Preview(name = "Album song row details", showBackground = true)
@Composable
private fun AlbumSongRowPreview() {
    FanSynTheme {
        SongContent(AlbumsMocks.getAlbumSongsMock().first()) {}
    }
}
