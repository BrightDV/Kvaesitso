package de.mm20.launcher2.ui.launcher.search.common

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.geometry.Rect
import androidx.core.app.ActivityOptionsCompat
import de.mm20.launcher2.badges.BadgeRepository
import de.mm20.launcher2.favorites.FavoritesRepository
import de.mm20.launcher2.icons.IconRepository
import de.mm20.launcher2.icons.LauncherIcon
import de.mm20.launcher2.search.data.Searchable
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class SearchableItemVM(
    private val searchable: Searchable
) : KoinComponent {
    protected val favoritesRepository: FavoritesRepository by inject()
    protected val badgeRepository: BadgeRepository by inject()
    protected val iconRepository: IconRepository by inject()

    val isPinned = favoritesRepository.isPinned(searchable)
    fun pin() {
        favoritesRepository.pinItem(searchable)
    }

    fun unpin() {
        favoritesRepository.unpinItem(searchable)
    }

    val isHidden = favoritesRepository.isHidden(searchable)
    fun hide() {
        favoritesRepository.hideItem(searchable)
    }

    fun unhide() {
        favoritesRepository.unhideItem(searchable)
    }

    val badge = badgeRepository.getBadge(searchable.badgeKey)

    fun getIcon(size: Int): Flow<LauncherIcon> {
        return iconRepository.getIcon(searchable, size)
    }


    fun launch(context: Context, bounds: Rect? = null): Boolean {
        val view = (context as? AppCompatActivity)?.window?.decorView
        val options = if (bounds != null && view != null) {
            ActivityOptionsCompat.makeClipRevealAnimation(
                view,
                bounds.left.toInt(),
                bounds.top.toInt(),
                bounds.width.toInt(),
                bounds.height.toInt()
            )
        } else {
            ActivityOptionsCompat.makeBasic()
        }
        if (searchable.launch(context, options.toBundle())) {
            favoritesRepository.incrementLaunchCounter(searchable)
            return true
        }
        return false
    }
}