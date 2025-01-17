package de.mm20.launcher2.ktx

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.Px
import androidx.core.graphics.drawable.toBitmap

fun Drawable.toBitmapOrNull(
        @Px width: Int = intrinsicWidth,
        @Px height: Int = intrinsicHeight,
        config: Bitmap.Config? = null
): Bitmap? {
    if (this is BitmapDrawable && bitmap == null) return null
    return toBitmap(width, height, config)
}