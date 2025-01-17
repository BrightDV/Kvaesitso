package de.mm20.launcher2.ui.settings.easteregg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import de.mm20.launcher2.preferences.LauncherDataStore
import de.mm20.launcher2.preferences.Settings
import de.mm20.launcher2.preferences.Settings.IconSettings.IconShape
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EasterEggSettingsScreenVM: ViewModel(), KoinComponent {
    private val dataStore: LauncherDataStore by inject()

    val easterEgg = dataStore.data.map { it.easterEgg }.asLiveData()
    fun setEasterEgg(easterEgg: Boolean) {
        viewModelScope.launch {
            dataStore.updateData {
                it.toBuilder().setEasterEgg(easterEgg).build()
            }
        }
    }
}