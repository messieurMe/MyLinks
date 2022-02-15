package com.messieurme.mylinks.custom

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class Counter @Inject constructor(private val sharedPref: SharedPreferences) {
    private val key = "counter"

    //suspend modifier to prevent accidental call from main thread
    suspend fun inc(): Long {
        val incremented = sharedPref.getLong(key, 1L) + 1
        sharedPref.edit(true) {
            putLong(key, incremented)
        }
        return incremented
    }

    suspend fun get(): Long = sharedPref.getLong(key, 1L)
}