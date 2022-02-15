package com.messieurme.mylinks.ui.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.messieurme.mylinks.custom.Consensus

class AlertDialogAddNew {

    @ExperimentalComposeUiApi
    @Composable
    fun AddNew(
        isOpen: (Boolean) -> Unit,
        addLink: (String, String, Int?) -> Unit,
        addFolder: (String, List<String>, Int?) -> Unit,
        alertDialogParams: AlertDialogAddEditParameters
    ) {
        var state by remember { mutableStateOf(if (alertDialogParams.editingLF()) 1 else 0) }

        val linkName = remember { mutableStateOf(alertDialogParams.emptyOrVar("linkName")) }
        val linkLink = remember { mutableStateOf(alertDialogParams.emptyOrVar("linkLink")) }

        val lfName = remember { mutableStateOf(alertDialogParams.emptyOrVar("lfName")) }
        val tags: Array<MutableState<String>> = remember { alertDialogParams.emptyOrTagArray() }

        val addButtonConsensus = Consensus(0)
        val addButtonEnabled by remember { addButtonConsensus.consensus }

        fun dismissDialog() {
            isOpen(false)
            alertDialogParams.endEditing()
        }

        AlertDialog(
            modifier = Modifier
                .animateContentSize()
                .padding(15.dp),
            title = { Text(text = "Add new", fontWeight = FontWeight.Bold) },
            onDismissRequest = { dismissDialog() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            text = {
                Card(border = BorderStroke(4.dp, MaterialTheme.colors.primary)) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        if (!alertDialogParams.isEdit()) {
                            TabRow(selectedTabIndex = state) {
                                Tab(
                                    selected = state == 0,
//                                    enabled = forAddingNew == -1 || forAddingNew == 0,
                                    onClick = { state = 0 },
                                    text = {
                                        Text("Add link")
                                    },

                                    )
                                Tab(
                                    selected = state == 1,
//                                    enabled = forAddingNew == -1 || forAddingNew == 1,
                                    onClick = { state = 1 },
                                    text = {
                                        Text("Add link folder")
                                    }
                                )
                            }
                        }
                        when (state) {
                            0 -> {
                                addButtonConsensus.changeSize(2)
                                Column(modifier = Modifier.padding(4.dp)) {
                                    TextInputField(
                                        label = "Name",
                                        placeholder = "Enter name",
                                        value = linkName,
                                        addButtonConsensus,
                                        i = 0
                                    )
                                    TextInputField(
                                        label = "Link",
                                        placeholder = "Enter link",
                                        value = linkLink,
                                        isOk = addButtonConsensus,
                                        i = 1
                                    )
                                }
                            }

                            1 -> {
                                addButtonConsensus.changeSize(4)
                                Column(modifier = Modifier.padding(4.dp)) {
                                    TextInputField(
                                        label = "Name",
                                        placeholder = "Enter link folder name",
                                        value = lfName,
                                        isOk = addButtonConsensus,
                                        i = 0
                                    )
                                    TagInputField(
                                        label = "Tag1",
                                        placeholder = "(Optional) Enter tag",
                                        value = tags[0],
                                        addButtonConsensus,
                                        i = 1
                                    )
                                    TagInputField(
                                        label = "Tag2",
                                        placeholder = "(Optional) Enter tag",
                                        value = tags[1],
                                        isOk = addButtonConsensus,
                                        i = 2
                                    )
                                    TagInputField(
                                        label = "Tag3",
                                        placeholder = "(Optional) Enter tag",
                                        value = tags[2],
                                        isOk = addButtonConsensus,
                                        i = 3
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton =
            {
                Button(
                    enabled = addButtonEnabled,
                    onClick = {
                        when (state) {
                            0 -> addLink(
                                linkName.value,
                                linkLink.value,
                                alertDialogParams.getIndex()
                            )
                            1 -> addFolder(
                                lfName.value,
                                tags.map { it.value }.filter { it.isNotEmpty() },
                                alertDialogParams.getIndex()
                            )
                        }
                        dismissDialog()
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { dismissDialog() }) {
                    Text("Close")
                }
            }
        )
    }

    @Composable
    fun TextInputField(
        label: String,
        placeholder: String,
        value: MutableState<String>,
        isOk: Consensus,
        i: Int,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            value = value.value,
            onValueChange = { value.value = it },
            isError = value.value.isEmpty().also { if (it) isOk.notOk(i) else isOk.ok(i) }
        )
    }


    @Composable
    fun TagInputField(
        label: String,
        placeholder: String,
        value: MutableState<String>,
        isOk: Consensus,
        i: Int,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            value = value.value,
            onValueChange = { value.value = it },
            isError = (value.value.length > 15).also { if (it) isOk.notOk(i) else isOk.ok(i) }
        )
    }

}