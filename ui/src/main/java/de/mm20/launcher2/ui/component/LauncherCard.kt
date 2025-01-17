package de.mm20.launcher2.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.mm20.launcher2.ui.locals.LocalCardStyle

@Composable
fun LauncherCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    backgroundOpacity: Float = LocalCardStyle.current.opacity,
    content: @Composable () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(LocalCardStyle.current.radius.dp),
        border = LocalCardStyle.current.borderWidth.takeIf { it > 0 }
            ?.let { BorderStroke(it.dp, MaterialTheme.colorScheme.surface) },
        content = content,
        color = MaterialTheme.colorScheme.surface.copy(alpha = backgroundOpacity.coerceIn(0f, 1f)),
        shadowElevation = if (backgroundOpacity == 1f) elevation else 0.dp,
        tonalElevation = elevation
    )
}