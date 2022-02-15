package com.messieurme.mylinks.ui.screens.mainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.messieurme.mylinks.ui.common.AlertDialogAddNew
import com.messieurme.mylinks.ui.common.LinkFolderItem
import com.messieurme.mylinks.ui.common.LinkItem
import javax.inject.Inject

class MainScreen @Inject constructor(private val vm: MainViewModel) {

    suspend fun backPressed() = vm.backPressed()

    @ExperimentalComposeUiApi
    @Composable
    fun Screen() {
        val (showAlert, changeAlertVisibility) = remember { mutableStateOf(false) }
        val (editItem, editItemChange) = remember { vm.adAddEditParameters.alertDialogOpen }

        if (showAlert || editItem != -1) {
            AlertDialogAddNew().AddNew(
                isOpen = changeAlertVisibility,
                addLink = { n, l, i -> vm.addLink(n, l, i) },
                addFolder = { n, t, i -> vm.addLinkFolder(n, t, i) },
                alertDialogParams = vm.adAddEditParameters
            )
        }
        Scaffold(
            topBar = { FolderPath() },
            bottomBar = { Footer() },
            floatingActionButton = { AddButton(changeAlertVisibility) },
            isFloatingActionButtonDocked = true
        ) {
            LinkFamilyColumn()
        }
    }


    @Composable
    fun AddButton(changeAlertVisibility: (Boolean) -> Unit) {
        FloatingActionButton(onClick = { changeAlertVisibility(true) }) {
            Icon(Icons.Filled.Add, null)
        }
    }


    @Composable
    private fun FolderPath() {
        TopAppBar(contentPadding = PaddingValues(7.dp, 1.dp), elevation = 2.dp) {
            Text(vm.path.collectAsState().value, maxLines = 1, modifier = Modifier)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        }
    }

    @Composable
    private fun LinkFamilyColumn() {
        when (val node = vm.node.collectAsState().value) {
            is MainViewModel.CurrentFolder.Loading -> Text(text = "Loading")
            is MainViewModel.CurrentFolder.Loaded -> {
                LazyColumn {
                    itemsIndexed(node.lf.linkFolders) { i, item ->
                        LinkFolderItem(
                            item,
                            { vm.deleteLF(i) },
                            { vm.adAddEditParameters.startEditing(i, item) },
                            { /*TODO*/ },
                            { id -> vm.changeCurrentPath(id) })
                    }
                    itemsIndexed(node.lf.links) { i, item ->
                        LinkItem(link = item,
                            { vm.deleteLink(i) },
                            { vm.adAddEditParameters.startEditing(i, item) }, {})
                    }
                }
            }
        }
    }

    @Composable
    private fun Footer() {
        BottomAppBar(
            cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
        ) {
            // Add some info like stats?
            Text("Footer")
        }
    }
}



