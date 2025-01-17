package de.mm20.launcher2.ui.component.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListPreference(
    title: String,
    icon: ImageVector? = null,
    items: List<ListPreferenceItem<T>>,
    value: T,
    summary: String? = items.firstOrNull { value == it.value }?.label,
    onValueChanged: (T) -> Unit,
    enabled: Boolean = true
) {
    var showDialog by remember { mutableStateOf(false) }
    Preference(
        title = title,
        summary = summary,
        icon = icon,
        enabled = enabled,
        onClick = {
            showDialog = true
        }
    )
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                tonalElevation = 16.dp,
                shadowElevation = 16.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp
                        )
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        items(items) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onValueChanged(it.value)
                                        showDialog = false
                                    }
                                    .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 24.dp)
                            ) {
                                RadioButton(selected = it.value == value, onClick = {
                                    onValueChanged(it.value)
                                    showDialog = false
                                })
                                Text(
                                    text = it.label,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

typealias ListPreferenceItem<T> = Pair<String, T>

inline val <T>ListPreferenceItem<T>.label: String
    get() = this.first

inline val <T>ListPreferenceItem<T>.value: T
    get() = this.second
