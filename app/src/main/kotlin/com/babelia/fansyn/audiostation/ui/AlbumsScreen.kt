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
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import com.babelia.fansyn.R
import com.babelia.fansyn.network.models.AudioStationAlbum
import com.babelia.fansyn.ui.components.GenericErrorDialog
import com.babelia.fansyn.ui.components.commons.AppTopBar
import com.babelia.fansyn.ui.components.commons.ImageFromUrl
import com.babelia.fansyn.ui.components.commons.VerticalGrid
import com.babelia.fansyn.ui.theme.Dimens
import com.babelia.fansyn.ui.theme.ImagePlaceholder
import com.babelia.fansyn.ui.theme.ResValues
import com.babelia.fansyn.ui.theme.Shapes
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import mini.Resource

private const val LOADING_ALBUMS_COUNT = 9

@Composable
@Suppress("UndocumentedPublicFunction")
fun AlbumsScreen(modifier: Modifier,
                 audioStationAlbumsViewModel: AudioStationAlbumsViewModel,
                 navigateToAlbumDetails: (albumData: AlbumBasicData) -> Unit) {

    val albumsResource = audioStationAlbumsViewModel.audioStationAlbums.collectAsState().value
    val isGridViewEnabled = audioStationAlbumsViewModel.isGridViewEnabled.collectAsState().value

    Scaffold(
        modifier = modifier.statusBarsPadding(),
        topBar = {
            AppTopBar(
                titleRes = R.string.albums_screen_title,
                backgroundColor = MaterialTheme.colors.primaryVariant,
                actions = {
                    IconButton(
                        onClick = {
                            audioStationAlbumsViewModel.toggleGridViewEnabled()
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isGridViewEnabled) R.drawable.ic_view_list else R.drawable.ic_view_grid),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        Albums(isGridViewEnabled, albumsResource, navigateToAlbumDetails)
    }
}

@Composable
@Suppress("UndocumentedPublicFunction")
fun Albums(showAsGrid: Boolean,
           albumsResource: Resource<AudioStationAlbumViewData>,
           onAlbumClick: (albumData: AlbumBasicData) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            when {
                albumsResource.isLoading -> {
                    AlbumsEmptyGrid(itemsCount = LOADING_ALBUMS_COUNT, showAsGrid)
                }
                albumsResource.isSuccess -> {
                    AlbumsVerticalGrid(
                        showAsGrid = showAsGrid,
                        albums = albumsResource.getOrNull()?.albums ?: emptyList(),
                        onAlbumClick = onAlbumClick
                    )
                }
                albumsResource.isFailure -> {
                    GenericErrorDialog { }
                }
            }
        }
    }
}

@Composable
private fun AlbumsEmptyGrid(itemsCount: Int, showAsGrid: Boolean) {
    VerticalGrid(
        numberOfColumns = if (showAsGrid) ResValues.general_grid_columns else ResValues.general_list_columns,
        items = (0 until itemsCount).toList(),
        contentPadding = PaddingValues(
            horizontal = Dimens.generalDimens.layout_padding,
            vertical = Dimens.generalDimens.layout_padding),
        spaceBetweenRows = Dimens.albumDimens.album_card_items_space_in_grid,
        spaceBetweenColumns = Dimens.albumDimens.album_card_items_space_in_grid,
        modifier = Modifier.fillMaxSize()
    ) {
        AlbumGridCard(contentIsLoading = true)
    }
}

@Composable
private fun AlbumsVerticalGrid(showAsGrid: Boolean,
                               albums: List<AudioStationAlbum>,
                               onAlbumClick: (albumData: AlbumBasicData) -> Unit = {}) {
    VerticalGrid(
        numberOfColumns = if (showAsGrid) ResValues.general_grid_columns else ResValues.general_list_columns,
        items = albums,
        contentPadding = PaddingValues(
            horizontal = Dimens.generalDimens.layout_padding,
            vertical = Dimens.generalDimens.layout_padding),
        spaceBetweenRows = Dimens.albumDimens.album_card_items_space_in_grid,
        spaceBetweenColumns = Dimens.albumDimens.album_card_items_space_in_grid,
        modifier = Modifier.fillMaxSize()
    ) {
        if (showAsGrid) {
            AlbumGridCard(
                album = it,
                onAlbumClick = onAlbumClick
            )
        } else {
            AlbumListCard(
                album = it,
                onAlbumClick = onAlbumClick
            )
        }
    }
}

@Composable
@Suppress("UndocumentedPublicFunction", "MagicNumber")
fun AlbumGridCard(album: AudioStationAlbum? = null,
                  contentIsLoading: Boolean = false,
                  onAlbumClick: (albumData: AlbumBasicData) -> Unit = {}) {

    val cardSize = remember { mutableStateOf(IntSize.Zero) }

    Card(
        modifier = Modifier
            .onGloballyPositioned { cardSize.value = it.size }
            .let { if (contentIsLoading) it.height(Dimens.albumDimens.album_card_loading_height) else it }
            .placeholder(
                visible = contentIsLoading,
                highlight = PlaceholderHighlight.shimmer()
            ),
        elevation = Dimens.albumDimens.album_card_elevation
    ) {
        album?.let {
            Column(
                modifier = Modifier
                    .clickable { onAlbumClick(AlbumBasicData.from(it)) }
            ) {
                ImageFromUrl(
                    // In order to have an image as a square we need to wait for onGloballyPositioned
                    // to be call. To avoid a unexpected UI behavior, minHeight is defined
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.albumDimens.album_card_text_padding_vertical)
                        .background(ImagePlaceholder)
                        .defaultMinSize(minHeight = Dimens.albumDimens.album_grid_card_album_cover_min_height)
                        .height(with(LocalDensity.current) { cardSize.value.width.toDp() }),
                    imageUrl = it.coverUrl,
                    errorRes = R.drawable.ic_album_art_placeholder,
                    enableCrossfade = true
                )
                Text(
                    text = it.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = Dimens.themeDimens.font_size_13,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        start = Dimens.albumDimens.album_card_text_padding_horizontal,
                        end = Dimens.albumDimens.album_card_text_padding_horizontal,
                        bottom = Dimens.albumDimens.album_grid_card_name_text_padding_vertical
                    )
                )
                Text(
                    text = it.displayArtist,
                    fontSize = Dimens.themeDimens.font_size_10,
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
}

@Composable
@Suppress("UndocumentedPublicFunction", "MagicNumber")
fun AlbumListCard(album: AudioStationAlbum? = null,
                  contentIsLoading: Boolean = false,
                  onAlbumClick: (albumData: AlbumBasicData) -> Unit = {}) {
    Card(
        modifier = Modifier
            .height(Dimens.albumDimens.album_list_card_height)
            .placeholder(
                visible = contentIsLoading,
                highlight = PlaceholderHighlight.shimmer()
            ),
        elevation = Dimens.albumDimens.album_card_elevation
    ) {
        album?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onAlbumClick(AlbumBasicData.from(it)) }
                    .padding(Dimens.albumDimens.album_list_card_internal_padding)
            ) {
                ImageFromUrl(
                    modifier = Modifier
                        .padding(end = Dimens.albumDimens.album_list_card_cover_image_padding_end)
                        .size(size = Dimens.albumDimens.album_list_card_album_cover_size)
                        .clip(shape = Shapes.small)
                        .background(ImagePlaceholder),
                    imageUrl = it.coverUrl,
                    errorRes = R.drawable.ic_album_art_placeholder,
                    enableCrossfade = true
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(
                            bottom = Dimens.albumDimens.album_card_text_padding_vertical
                        )
                    )
                    Text(
                        text = it.displayArtist,
                        fontSize = Dimens.themeDimens.font_size_14,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}