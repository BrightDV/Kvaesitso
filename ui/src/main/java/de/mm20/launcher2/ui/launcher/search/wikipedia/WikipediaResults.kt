package de.mm20.launcher2.ui.launcher.search.wikipedia

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mm20.launcher2.ui.component.LauncherCard
import de.mm20.launcher2.ui.launcher.search.SearchVM

@Composable
fun ColumnScope.WikipediaResults() {
    val viewModel: SearchVM = viewModel()
    val wikipedia by viewModel.wikipediaResult.observeAsState(null)

    AnimatedVisibility(wikipedia != null) {
        LauncherCard(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            wikipedia?.let { WikipediaItem(wikipedia = it) }
        }
    }

}