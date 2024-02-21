package com.abdoali.mymidia3.uiCompount.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.abdoali.mymidia3.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieCompose(
    modifier: Modifier = Modifier
) {
    val animation by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.no_network
        )
    )
    val animationSata by animateLottieCompositionAsState(
        composition = animation,
        iterations = Int.MAX_VALUE
    )

    LottieAnimation(
        composition = animation,
        progress = { animationSata },
        modifier = modifier,
        contentScale = ContentScale.Inside
    )

}

@Preview
@Composable
fun LottiePreview() {
    LottieCompose()
}