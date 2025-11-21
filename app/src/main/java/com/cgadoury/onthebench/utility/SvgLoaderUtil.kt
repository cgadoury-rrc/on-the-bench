package com.cgadoury.onthebench.utility

import android.content.Context
import coil3.ImageLoader
import coil3.svg.SvgDecoder

/**
 * Purpose - decode svg image - builds and returns the svg image loader
 * @param context: The application context
 * @return ImageLoader
 */

fun loadSvgImage(context: Context): ImageLoader {
    return ImageLoader.Builder(
        context
    ).components {
        add(SvgDecoder.Factory())
    }
        .build()
}