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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babelia.fansyn.R
import com.babelia.fansyn.network.models.AudioStationAlbum
import com.babelia.fansyn.network.models.AudioStationSong
import com.babelia.fansyn.ui.components.GenericErrorDialog
import com.babelia.fansyn.ui.components.commons.AppTopBar
import com.babelia.fansyn.ui.components.commons.ImageFromUrl
import com.babelia.fansyn.ui.components.commons.VerticalGrid
import com.babelia.fansyn.ui.theme.*
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import mini.Resource

private const val LOADING_SONGS_COUNT = 15

@Composable
@Suppress("UndocumentedPublicFunction")
fun AlbumSongsScreen(modifier: Modifier,
                     audioStationAlbumSongsViewModel: AudioStationAlbumSongsViewModel,
                     onNavigateUp: () -> Unit) {

    val albumDetailsResource = audioStationAlbumSongsViewModel.audioStationAlbumDetails.collectAsState().value

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.app_name),
                navigationIcon = rememberVectorPainter(Icons.Rounded.ArrowBack),
                navigationIconPaddingValues = PaddingValues(
                    start = Dimens.generalDimens.top_bar_navigation_icon_padding_start
                ),
                onNavigationIconClick = onNavigateUp,
                backgroundColor = MaterialTheme.colors.primaryVariant
            )
        }
    ) {
        AlbumSongs(albumDetailsResource /* TODO Add song click */)
    }
}

@Composable
@Suppress("UndocumentedPublicFunction")
fun AlbumSongs(albumDetailsResource: Resource<AudioStationAlbumDetailsViewData>,
               onSongClick: (songId: String) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            when {
                albumDetailsResource.isLoading -> {
                    AlbumSongEmptyList(itemsCount = LOADING_SONGS_COUNT)
                }
                albumDetailsResource.isSuccess -> {
                    AlbumInfo(albumDetailsResource.getOrNull()?.album)
                    AlbumSongsList(
                        songs = albumDetailsResource.getOrNull()?.songs ?: emptyList(),
                        onSongClick = onSongClick
                    )
                }
                albumDetailsResource.isFailure -> {
                    GenericErrorDialog { }
                }
            }
        }
    }
}

@Composable
private fun AlbumInfo(audioStationAlbum: AudioStationAlbum?) {
    audioStationAlbum?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.albumDimens.album_details_cover_padding_top,
                    start = Dimens.generalDimens.layout_padding,
                    end = Dimens.generalDimens.layout_padding
                )
        ) {
            ImageFromUrl(
                modifier = Modifier
                    .size(size = Dimens.albumDimens.album_details_cover_size)
                    .clip(shape = Shapes.medium)
                    .background(ImagePlaceholder),
                imageUrl = it.coverUrl,
                errorRes = R.drawable.ic_album_art_placeholder,
                enableCrossfade = true
            )
            Text(
                text = it.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = Dimens.themeDimens.font_size_18,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    top = Dimens.albumDimens.album_details_title_padding_top,
                    start = Dimens.albumDimens.album_card_text_padding_horizontal,
                    end = Dimens.albumDimens.album_card_text_padding_horizontal,
                    bottom = Dimens.albumDimens.album_grid_card_name_text_padding_vertical
                )
            )
            Text(
                text = it.displayArtist,
                fontSize = Dimens.themeDimens.font_size_12,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    start = Dimens.albumDimens.album_card_text_padding_horizontal,
                    end = Dimens.albumDimens.album_card_text_padding_horizontal,
                    bottom = Dimens.albumDimens.album_card_text_padding_vertical
                )
            )
        }
    }
}

@Composable
private fun AlbumSongEmptyList(itemsCount: Int) {
    VerticalGrid(
        numberOfColumns = ResValues.general_list_columns,
        items = (0 until itemsCount).toList(),
        contentPadding = PaddingValues(
            horizontal = Dimens.generalDimens.layout_padding,
            vertical = Dimens.generalDimens.layout_padding),
        spaceBetweenRows = Dimens.albumDimens.album_card_items_space_in_grid,
        spaceBetweenColumns = Dimens.albumDimens.album_card_items_space_in_grid,
        modifier = Modifier.fillMaxSize()
    ) {
        SongContent(contentIsLoading = true)
    }
}

@Composable
private fun AlbumSongsList(songs: List<AudioStationSong>,
                           onSongClick: (songId: String) -> Unit = {}) {
    VerticalGrid(
        numberOfColumns = ResValues.general_list_columns,
        items = songs,
        contentPadding = PaddingValues(
            horizontal = Dimens.generalDimens.layout_padding,
            vertical = Dimens.generalDimens.layout_padding),
        spaceBetweenRows = Dimens.albumDimens.album_card_items_space_in_grid,
        spaceBetweenColumns = Dimens.albumDimens.album_card_items_space_in_grid,
        modifier = Modifier.fillMaxSize(),
        divider = { Divider(color = LightPurple, thickness = 1.dp) }
    ) {
        SongContent(song = it, onSongClick = onSongClick)
    }
}

@Composable
@Suppress("UndocumentedPublicFunction")
fun SongContent(song: AudioStationSong? = null,
                contentIsLoading: Boolean = false,
                onSongClick: (songId: String) -> Unit = {}) {
    song?.let {
        Column(
            modifier = Modifier
                .let { if (contentIsLoading) it.height(Dimens.albumDimens.album_card_loading_height) else it }
                .clickable { onSongClick(it.id) }
                .padding(Dimens.albumDimens.album_card_internal_padding)
                .placeholder(
                    visible = contentIsLoading,
                    highlight = PlaceholderHighlight.shimmer()
                )
        ) {
            Text(
                text = it.fileName,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(
                    bottom = Dimens.albumDimens.album_card_text_padding_vertical
                )
            )
        }
    }
}