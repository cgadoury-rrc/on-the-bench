package com.cgadoury.onthebench.utility

import android.content.Context
import coil3.ImageLoader
import coil3.svg.SvgDecoder

/**
 * Purpose - svg decoder util - a class used to decode svg type images
 */
class SvgDecoderUtil {
    fun decodeSvgImage(context: Context): ImageLoader {
        return ImageLoader.Builder(
            context
        ).components {
            add(SvgDecoder.Factory())
        }
            .build()
    }
}
