package com.messieurme.mylinks.ui.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.messieurme.mylinks.custom.Link
import com.messieurme.mylinks.custom.LinkFolderFolded

class AlertDialogAddEditParameters {
    val alertDialogOpen = mutableStateOf(-1)
    var linkToChange: Pair<Int, Link>? = null
    var linkFToChange: Pair<Int, LinkFolderFolded>? = null

    fun endEditing() {
        linkToChange = null
        linkFToChange = null
        alertDialogOpen.value = -1
    }

    fun startEditing(i: Int, link: Link) {
        linkToChange = Pair(i, link)
        alertDialogOpen.value = 0
    }

    fun startEditing(i: Int, lf: LinkFolderFolded) {
        linkFToChange = Pair(i, lf)
        alertDialogOpen.value = 1
    }

    fun isEdit() = alertDialogOpen.value != -1
    fun editingLink() = alertDialogOpen.value == 0
    fun editingLF() = alertDialogOpen.value == 1

    /**
     * Available commands:
     * - linkName -> returns name from link which is editing now. Otherwise returns empty string
     * - linkLink -> returns link from link which is editing now. Otherwise returns empty string
     * - lfName   -> returns name from lf which is editing now. Otherwise returns empty string
     * otherwise throws IllegalArgumentException
     */
    fun emptyOrVar(command: String) =
        when (command) {
            "linkName" -> if (editingLink()) linkToChange!!.second.name else ""
            "linkLink" -> if (editingLink()) linkToChange!!.second.link else ""
            "lfName" -> if (editingLF()) linkFToChange!!.second.name else ""
            else -> throw IllegalArgumentException("Command not supported")
        }

    fun emptyOrTagArray(): Array<MutableState<String>> {
        return if (editingLF())
            with(linkFToChange!!.second.tags) {
                map { mutableStateOf(it) }
                    .toTypedArray()
                    .plus(Array(3 - size) { mutableStateOf("") })
            }
        else {
            Array(3) { mutableStateOf("") }
        }
    }

    fun getIndex() =
        when (alertDialogOpen.value) {
            0 -> linkToChange!!.first
            1 -> linkFToChange!!.first
            else -> null
        }


//    fun enableEditingLink(toEdit: Pair<Int, Link>) {
//        alertDialogOpen.value = 0
//        linkToChange = toEdit
//        linkFToChange = null
//    }

//    fun enableEditingLF(toEdit: Pair<Int, LinkFolderFolded>) {
//        alertDialogOpen.value = 1
//        linkToChange = null
//        linkFToChange = toEdit
//    }
}