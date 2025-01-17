package de.mm20.launcher2.ui.launcher.search.common

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import de.mm20.launcher2.search.data.*
import de.mm20.launcher2.ui.component.LauncherCard
import de.mm20.launcher2.ui.component.ShapedLauncherIcon
import de.mm20.launcher2.ui.ktx.toDp
import de.mm20.launcher2.ui.ktx.toPixels
import de.mm20.launcher2.ui.launcher.search.apps.AppItemGridPopup
import de.mm20.launcher2.ui.launcher.search.calendar.CalendarItemGridPopup
import de.mm20.launcher2.ui.launcher.search.common.grid.GridItemVM
import de.mm20.launcher2.ui.launcher.search.contacts.ContactItemGridPopup
import de.mm20.launcher2.ui.launcher.search.files.FileItemGridPopup
import de.mm20.launcher2.ui.launcher.search.shortcut.ShortcutItemGridPopup
import de.mm20.launcher2.ui.launcher.search.website.WebsiteItemGridPopup
import de.mm20.launcher2.ui.launcher.search.wikipedia.WikipediaItemGridPopup
import de.mm20.launcher2.ui.locals.LocalWindowPosition
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridItem(modifier: Modifier = Modifier, item: Searchable) {
    val viewModel = remember(item.key) { GridItemVM(item) }
    val context = LocalContext.current

    var showPopup by remember { mutableStateOf(false) }
    var bounds by remember { mutableStateOf(Rect.Zero) }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val badge by viewModel.badge.collectAsState(null)
        val iconSize = 48.dp.toPixels()
        val icon by remember(item.key) { viewModel.getIcon(iconSize.toInt()) }.collectAsState(null)

        // If item is one of these types, try to launch them on click; show details otherwise
        val launchOnPress =
            item is File || item is Application || item is AppShortcut || item is Website || item is Wikipedia

        ShapedLauncherIcon(
            modifier = Modifier
                .onGloballyPositioned {
                    bounds = it.boundsInWindow()
                },
            size = 48.dp,
            badge = badge,
            icon = icon,
            onClick = {
                if (!launchOnPress || !viewModel.launch(context, bounds)) {
                    showPopup = true
                }
            },
            onLongClick = {
                showPopup = true
            }
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = item.label,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (showPopup) {
            ItemPopup(origin = bounds, searchable = item, onDismissRequest = { showPopup = false })
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun ItemPopup(origin: Rect, searchable: Searchable, onDismissRequest: () -> Unit) {
    var show by remember { mutableStateOf(false) }
    LaunchedEffect(null) {
        show = true
    }
    LaunchedEffect(show) {
        if (!show) {
            delay(300L)
            onDismissRequest()
        }
    }
    BackHandler {
        show = false
    }

    val animationProgress by animateFloatAsState(if (show) 1f else 0f, tween(300))
    Popup(
        properties = PopupProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true
        ),
        alignment = Alignment.TopCenter,
        onDismissRequest = {
            show = false
        },
        offset = IntOffset(-origin.left.toInt(), 0)
    ) {
        CompositionLocalProvider(LocalWindowPosition provides origin.top) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LauncherCard(
                    elevation = 8.dp * animationProgress,
                    backgroundOpacity = 1f,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .absoluteOffset(
                            x = ((1 - animationProgress) * origin.left).toDp() - 16.dp * (1 - animationProgress),
                        )
                        .wrapContentSize()
                ) {
                    when (searchable) {
                        is Application -> {
                            AppItemGridPopup(
                                app = searchable,
                                show = show,
                                animationProgress = animationProgress,
                                origin = origin,
                                onDismiss = {
                                    show = false
                                }
                            )
                        }
                        is Website -> {
                            WebsiteItemGridPopup(
                                website = searchable,
                                show = show,
                                animationProgress = animationProgress,
                                origin = origin,
                                onDismiss = {
                                    show = false
                                }
                            )
                        }
                        is Wikipedia -> {
                            WikipediaItemGridPopup(
                                wikipedia = searchable,
                                show = show,
                                animationProgress = animationProgress,
                                origin = origin,
                                onDismiss = {
                                    show = false
                                }
                            )
                        }
                        is Contact -> {
                            ContactItemGridPopup(
                                contact = searchable,
                                show = show,
                                animationProgress = animationProgress,
                                origin = origin,
                                onDismiss = {
                                    show = false
                                }
                            )
                        }
                        is File -> {
                            FileItemGridPopup(
                                file = searchable,
                                show = show,
                                animationProgress = animationProgress,
                                origin = origin,
                                onDismiss = {
                                    show = false
                                }
                            )
                        }
                        is CalendarEvent -> {
                            CalendarItemGridPopup(
                                calendar = searchable,
                                show = show,
                                animationProgress = animationProgress,
                                origin = origin,
                                onDismiss = {
                                    show = false
                                }
                            )
                        }
                        is AppShortcut -> {
                            ShortcutItemGridPopup(
                                shortcut = searchable,
                                show = show,
                                animationProgress = animationProgress,
                                origin = origin,
                                onDismiss = {
                                    show = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}