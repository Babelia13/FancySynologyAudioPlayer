package com.babelia.fansyn.ui.components.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.babelia.fansyn.R

/**
 * A progress bar in the center of a full screen.
 */
@Composable
fun FullScreenProgressIndicator(modifier: Modifier, color: Color = MaterialTheme.colors.primary) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
        LottieAnimation(
            modifier = Modifier.size(200.dp),
            iterations = LottieConstants.IterateForever,
            composition = composition.value
        )
    }
}