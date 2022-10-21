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

package com.babelia.fansyn.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.babelia.fansyn.R

val openSauceSansFontFamily = FontFamily(
    Font(R.font.opensaucesans_regular),
    Font(R.font.opensaucesans_italic, style = FontStyle.Italic),
    Font(R.font.opensaucesans_medium, weight = FontWeight.Medium),
    Font(R.font.opensaucesans_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.opensaucesans_bold, weight = FontWeight.Bold),
    Font(R.font.opensaucesans_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.opensaucesans_semibold, weight = FontWeight.SemiBold),
    Font(R.font.opensaucesans_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.opensaucesans_light, weight = FontWeight.Light),
    Font(R.font.opensaucesans_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.opensaucesans_black, weight = FontWeight.Black),
    Font(R.font.opensaucesans_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.opensaucesans_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.opensaucesans_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.opensaucesans_extrabolditalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = openSauceSansFontFamily,
    h1 = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 96.sp,
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 60.sp,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp,
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 1.25.sp
    ),
)