package de.mm20.launcher2.ui.launcher.search.website

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import de.mm20.launcher2.search.data.Website
import de.mm20.launcher2.ui.component.DefaultToolbarAction
import de.mm20.launcher2.ui.component.Toolbar
import de.mm20.launcher2.ui.component.ToolbarAction
import de.mm20.launcher2.ui.R
import de.mm20.launcher2.ui.ktx.toDp
import de.mm20.launcher2.ui.locals.LocalFavoritesEnabled

@Composable
fun WebsiteItem(
    modifier: Modifier = Modifier,
    website: Website,
    onBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val viewModel = remember(website) { WebsiteItemVM(website) }

    Column(
        modifier = modifier.clickable {
            viewModel.launch(context)
        }
    ) {
        if (website.image.isNotBlank()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                painter = rememberImagePainter(website.image),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = website.label,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = website.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
        val toolbarActions = mutableListOf<ToolbarAction>()

        if (LocalFavoritesEnabled.current) {
            val isPinned by viewModel.isPinned.collectAsState(false)
            val favAction = if (isPinned) {
                DefaultToolbarAction(
                    label = stringResource(R.string.menu_favorites_unpin),
                    icon = Icons.Rounded.Star,
                    action = {
                        viewModel.unpin()
                        onBack?.invoke()
                    }
                )
            } else {
                DefaultToolbarAction(
                    label = stringResource(R.string.menu_favorites_pin),
                    icon = Icons.Rounded.StarOutline,
                    action = {
                        viewModel.pin()
                        onBack?.invoke()
                    })
            }
            toolbarActions.add(favAction)
        }

        toolbarActions.add(
            DefaultToolbarAction(
                label = stringResource(R.string.menu_share),
                icon= Icons.Rounded.Share,
                action = {
                    viewModel.share(context)
                }
            )
        )

        Toolbar(
            leftActions = if (onBack != null) listOf(
                DefaultToolbarAction(
                    stringResource(id = R.string.menu_back),
                    icon = Icons.Rounded.ArrowBack,
                    action = onBack
                )
            ) else emptyList(),
            rightActions = toolbarActions
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WebsiteItemGridPopup(
    website: Website,
    show: Boolean,
    animationProgress: Float,
    origin: Rect,
    onDismiss: () -> Unit
) {
    AnimatedContent(
        targetState = show,
        transitionSpec = {
            fadeIn(tween(200)) with
                    fadeOut(tween(200, 200)) using
                    SizeTransform { _, _ ->
                        tween(300)
                    }
        }
    ) { targetState ->
        if (targetState) {
            WebsiteItem(
                modifier = Modifier
                    .fillMaxWidth(),
                website = website,
                onBack = onDismiss
            )
        } else {
            Box(
                modifier = Modifier
                    .requiredWidth(origin.width.toDp())
                    .requiredHeight(origin.height.toDp())
            )
        }
    }
}