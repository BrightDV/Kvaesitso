package de.mm20.launcher2.badges.providers

import de.mm20.launcher2.applications.AppRepository
import de.mm20.launcher2.badges.Badge
import de.mm20.launcher2.badges.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SuspendedAppsBadgeProvider : BadgeProvider, KoinComponent {
    private val appRepository: AppRepository by inject()

    override fun getBadge(badgeKey: String): Flow<Badge?> = channelFlow {
        if (badgeKey.startsWith("app://")) {
            val packageName = badgeKey.substring(6)
            appRepository.getSuspendedPackages().collectLatest {
                if (it.contains(packageName)) {
                    send(
                        Badge(
                            iconRes = R.drawable.ic_badge_suspended
                        )
                    )
                } else {
                    send(null)
                }
            }
        } else {
            send(null)
        }
    }
}