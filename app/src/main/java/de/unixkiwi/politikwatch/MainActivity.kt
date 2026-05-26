package de.unixkiwi.politikwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import de.unixkiwi.politikwatch.presentation.home.view.HomePage
import de.unixkiwi.politikwatch.ui.theme.PolitikWatchTheme
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


        enableEdgeToEdge()
        setContent {
            PolitikWatchTheme {
                HomePage()
            }
        }
    }
}
