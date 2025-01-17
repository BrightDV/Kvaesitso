package de.mm20.launcher2.ui.component.preferences

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SwitchPreference(
    title: String,
    icon: ImageVector? = null,
    summary: String? = null,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Preference(
        title = title,
        icon = icon,
        summary = summary,
        enabled = enabled,
        onClick = {
            onValueChanged(!value)
        },
        controls = {
            Switch(
                enabled = enabled, checked = value, onCheckedChange = onValueChanged,
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    )
}