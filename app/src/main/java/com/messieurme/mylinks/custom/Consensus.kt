package com.messieurme.mylinks.custom

import androidx.compose.runtime.mutableStateOf

class Consensus(size: Int) {
    val consensus = mutableStateOf(false)

    var checker = BooleanArray(size)

    private fun checkArray(){
        consensus.value = checker.all { it }
    }

    fun ok(i: Int){
        checker[i] = true
        checkArray()
    }

    fun notOk(i: Int){
        checker[i] = false
        checkArray()
    }

    fun changeSize(n: Int){
        checker = BooleanArray(n) { false }
        checkArray()
    }

}