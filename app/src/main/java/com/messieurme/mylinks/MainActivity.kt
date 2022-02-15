package com.messieurme.mylinks

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import com.messieurme.mylinks.ui.screens.mainScreen.MainScreen
import com.messieurme.mylinks.ui.theme.MyLinksTheme
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity @Inject constructor() : DaggerAppCompatActivity() {

    @Inject
    lateinit var mainScreen: MainScreen

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyLinksTheme {
                Surface(color = MaterialTheme.colors.background) {
                    mainScreen.Screen()
                }
            }
        }
    }

    override fun onBackPressed() {
        CoroutineScope(Dispatchers.Main).launch {
            if (mainScreen.backPressed()) super.onBackPressed()
        }
    }
}
