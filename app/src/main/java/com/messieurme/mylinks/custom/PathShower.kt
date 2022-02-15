package com.messieurme.mylinks.custom

import com.messieurme.mylinks.ui.screens.mainScreen.MainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlin.reflect.KProperty

class PathShower(current: MutableStateFlow<MainViewModel.CurrentFolder>) {
    operator fun getValue(
        mainViewModel: MainViewModel,
        property: KProperty<*>
    ): MutableStateFlow<String> {
        return path
    }

    val path = MutableStateFlow("./")

    private var job: Job? = null

    init {
        CoroutineScope(Dispatchers.Default).launch {
            current.collect {
                when (it) {
                    is MainViewModel.CurrentFolder.Loading -> {
                        job = CoroutineScope(Dispatchers.Default).launch {
                            while (true) {
                                if (path.value.length >= 3) {
                                    path.value = "."
                                } else {
                                    path.value += "."
                                }
                                delay(250)
                            }
                        }
                    }
                    is MainViewModel.CurrentFolder.Loaded -> {
                        job?.cancel()
                        path.value = it.lf.path
                    }
                }
            }
        }
    }
}