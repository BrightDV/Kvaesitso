package de.mm20.launcher2.ui.legacy.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import de.mm20.launcher2.ui.MdcLauncherTheme
import de.mm20.launcher2.ui.R
import de.mm20.launcher2.ui.launcher.widgets.weather.WeatherWidget

class WeatherWidget : LauncherWidget {


    override val canResize: Boolean
        get() = false
    override val name: String
        get() = resources.getString(R.string.widget_name_weather)


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleRes: Int) : super(context, attrs, defStyleRes)


    init {
        val composeView = ComposeView(context)
        composeView.id = FrameLayout.generateViewId()
        composeView.setContent {
            MdcLauncherTheme {
                // TODO: Temporary solution until parent widget card is rewritten in Compose
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                    Column {
                        WeatherWidget()
                    }
                }
            }
        }
        addView(composeView)
    }

    companion object {
        const val ID = "weather"
    }
}


