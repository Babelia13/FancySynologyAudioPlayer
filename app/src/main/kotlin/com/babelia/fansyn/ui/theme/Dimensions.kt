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

@file:Suppress("LongParameterList", "ConstructorParameterNaming", "MagicNumber")

package com.babelia.fansyn.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Class in charge to store the general layouts dimensions.
 */
data class GeneralDimensions(
    val layout_padding: Dp = 16.dp,
    val button_icon_padding_horizontal: Dp = 12.dp,
    val button_icon_padding_to_text: Dp = 8.dp,
    val icon_button_padding_horizontal: Dp = 8.dp,
    val icon_button_padding_vertical: Dp = 8.dp,
    val min_size: Dp = 1.dp,
    // Top bar
    val top_bar_navigation_icon_padding_start: Dp = 4.dp,
    // Forms
    val form_request_error_padding_top: Dp = 8.dp,
    // Generic loading screen
    val loading_screen_spacer_size: Dp = 8.dp,
    val loading_screen_placeholder_width_ratio: Float = 0.6f,
    // Material chip
    val material_chip_border_width: Dp = 1.5.dp,
    val material_chip_vertical_padding: Dp = 8.dp,
    val material_chip_horizontal_padding: Dp = 12.dp,
)

val smallTabletGeneralDimensions = GeneralDimensions(
    layout_padding = 32.dp,
    icon_button_padding_horizontal = 24.dp,
    icon_button_padding_vertical = 16.dp,
    top_bar_navigation_icon_padding_start = 12.dp,
)

/**
 * Class in charge to store the exceptions of theme dimensions.
 */
data class ThemeDimensions(
    val font_size_9: TextUnit = 9.sp,
    val font_size_10: TextUnit = 10.sp,
    val font_size_11: TextUnit = 11.sp,
    val font_size_12: TextUnit = 12.sp,
    val font_size_13: TextUnit = 13.sp,
    val font_size_14: TextUnit = 14.sp,
    val font_size_18: TextUnit = 18.sp,
    val font_size_22: TextUnit = 22.sp,
    val font_letter_spacing_1: TextUnit = (-0.1).sp,
    val font_letter_spacing_2: TextUnit = (-0.2).sp,
    val font_letter_spacing_3: TextUnit = (-0.3).sp,
)

/**
 * Class in charge to store the layouts dimensions related to albums screens.
 */
data class AlbumsDimensions(
    val album_card_loading_height: Dp = 210.dp,
    val album_card_elevation: Dp = 4.dp,
    val album_card_internal_padding: Dp = 16.dp,
    val album_card_image_size: Dp = 72.dp,
    val album_card_text_padding_horizontal: Dp = 8.dp,
    val album_card_text_padding_vertical: Dp = 8.dp,
    val album_card_items_space_in_grid: Dp = 12.dp,
    val album_list_card_album_cover_size: Dp = 80.dp,
    val album_list_card_height: Dp = 92.dp,
    val album_list_card_internal_padding: Dp = 8.dp,
    val album_list_card_cover_image_padding_end: Dp = 16.dp,
    val album_grid_card_album_cover_min_height: Dp = 190.dp,
    val album_grid_card_name_text_padding_vertical: Dp = 4.dp,
    val album_details_cover_padding_top: Dp = 52.dp,
    val album_details_cover_size: Dp = 200.dp,
    val album_details_title_padding_top: Dp = 12.dp,
    val album_details_title_padding_bottom: Dp = 4.dp,
)

val smallTabletAlbumsDimensions = AlbumsDimensions(
    album_card_image_size = 120.dp,
    album_card_items_space_in_grid = 16.dp,
    album_details_title_padding_top = 16.dp,
    album_details_title_padding_bottom = 8.dp,
)

val largeTabletAlbumsDimensions = smallTabletAlbumsDimensions.copy(

)

/**
 * Class in charge to store the layouts dimensions. Split them in different data classes/classes because they can
 * only contain a limited number of fields.
 */
data class Dimensions(
    val generalDimens: GeneralDimensions = GeneralDimensions(),
    val themeDimens: ThemeDimensions = ThemeDimensions(),
    val albumDimens: AlbumsDimensions = AlbumsDimensions()
)

// Phone dimensions are the default ones
val phoneDimensions = Dimensions()

// Specific values for small tablets
val smallTabletDimensions = Dimensions(
    generalDimens = smallTabletGeneralDimensions,
    albumDimens = smallTabletAlbumsDimensions,
)

// Specific values for large tablets
val largeTabletDimensions = smallTabletDimensions.copy(
    albumDimens = largeTabletAlbumsDimensions,
)