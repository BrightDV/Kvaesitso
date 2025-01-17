package de.mm20.launcher2.badges.providers

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import de.mm20.launcher2.badges.Badge
import de.mm20.launcher2.graphics.BadgeDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class AppShortcutBadgeProvider(
    private val context: Context
) : BadgeProvider {
    override fun getBadge(badgeKey: String): Flow<Badge?> = channelFlow {
        if (badgeKey.startsWith("shortcut://")) {
            val componentName = ComponentName.unflattenFromString(badgeKey.substring(11))
            if (componentName == null) {
                send(null)
                return@channelFlow
            }
            withContext(Dispatchers.IO) {
                val icon = try {
                    context.packageManager.getActivityIcon(
                        componentName
                    )
                } catch (e: PackageManager.NameNotFoundException) {
                    return@withContext
                }
                val badge = Badge(icon = BadgeDrawable(context, icon))
                send(badge)
            }
        } else {
            send(null)
        }
    }
}