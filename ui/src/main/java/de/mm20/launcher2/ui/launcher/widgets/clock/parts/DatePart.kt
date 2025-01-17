package de.mm20.launcher2.ui.launcher.widgets.clock.parts

import android.content.ContentUris
import android.content.Intent
import android.provider.CalendarContract
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.em
import de.mm20.launcher2.preferences.Settings.ClockWidgetSettings.ClockWidgetLayout
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DatePart(
    time: Long,
    layout: ClockWidgetLayout
) {
    val verticalLayout = layout == ClockWidgetLayout.Vertical
    val context = LocalContext.current
    TextButton(onClick = {
        val startMillis = System.currentTimeMillis()
        val builder = CalendarContract.CONTENT_URI.buildUpon()
        builder.appendPath("time")
        ContentUris.appendId(builder, startMillis)
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(builder.build())
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(intent)
    }) {
        if (verticalLayout) {
            Text(
                text = DateUtils.formatDateTime(
                    context,
                    time,
                    DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                ),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        } else {
            val line1Format = DateFormat.getBestDateTimePattern(Locale.getDefault(), "EEEE")
            val line2Format = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMMM dd yyyy")
            val format = SimpleDateFormat("$line1Format\n$line2Format")
            Text(
                text = format.format(time),
                lineHeight = 1.2.em,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
}