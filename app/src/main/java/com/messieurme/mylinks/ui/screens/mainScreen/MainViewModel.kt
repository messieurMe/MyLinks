package com.messieurme.mylinks.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messieurme.mylinks.custom.LinkFolder
import com.messieurme.mylinks.custom.LinkFolderFolded
import com.messieurme.mylinks.custom.PathShower
import com.messieurme.mylinks.repository.BaseRepository
import com.messieurme.mylinks.ui.common.AlertDialogAddEditParameters
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: BaseRepository) : ViewModel() {
    private val delayConst = 500L


    private val _node: MutableStateFlow<CurrentFolder> = MutableStateFlow(CurrentFolder.Loading)
    val node = _node.asStateFlow()
    val path by PathShower(_node)

    // ad is for Alert Dialog
    val adAddEditParameters = AlertDialogAddEditParameters()


    init {
        viewModelScope.launch {
            _node.value = CurrentFolder.Loaded(repository.getRoot())
        }
    }

    fun addLinkFolder(name: String, tags: List<String>, position: Int?) {
        viewModelScope.launch {
            val current = _node.value as CurrentFolder.Loaded
            _node.value = CurrentFolder.Loading
            delay(delayConst)

            val a = CurrentFolder.Loaded(repository.add(current.lf, name, tags, position))
            _node.value = a
        }
    }

    fun addLink(name: String, link: String, position: Int? = null) {
        viewModelScope.launch {
            val current = _node.value as CurrentFolder.Loaded
            _node.value = CurrentFolder.Loading
            delay(delayConst)

            var correctLink = link
            if (!link.startsWith("https://") && !link.startsWith("http://")) {
                correctLink = "http://$link"
            }
            _node.value =
                CurrentFolder.Loaded(repository.add(current.lf.id, name, correctLink, position))
        }
    }


    fun changeCurrentPath(id: Long) {
        viewModelScope.launch {
            _node.value = CurrentFolder.Loading
            delay(delayConst)
            _node.value = CurrentFolder.Loaded(repository.get(id))
        }
    }

    fun deleteLink(position: Int) {
        viewModelScope.launch {
            when (val item = _node.value) {
                is CurrentFolder.Loading -> {
                }
                is CurrentFolder.Loaded -> {
                    _node.value = CurrentFolder.Loading
                    val newLinks =
                        item.lf.links.slice((0 until position) + ((position + 1) until item.lf.links.size))
                    repository.updateLinks(item.lf.id, newLinks)
                    changeCurrentPath(item.lf.id)
                }
            }
        }
    }

    fun deleteLF(position: Int) {
        viewModelScope.launch {
            when (val item = _node.value) {
                is CurrentFolder.Loading -> {
                }
                is CurrentFolder.Loaded -> {
                    _node.value = CurrentFolder.Loading
                    val idToDelete = item.lf.linkFolders[position]
                    repository.delete(idToDelete.id)
                    val newLFF =
                        item.lf.linkFolders.slice((0 until position) + ((position + 1) until item.lf.linkFolders.size))
                    repository.updateLF(item.lf.id, newLFF)
                    changeCurrentPath(item.lf.id)
                }
            }
        }
    }

    fun delete(lf: LinkFolderFolded) {
        viewModelScope.launch {
            repository.delete(lf.id)
        }
    }

    suspend fun backPressed(): Boolean =
        when (val curr = _node.value) {
            is CurrentFolder.Loading -> false
            is CurrentFolder.Loaded -> {
                val r = repository.onBackPressed(curr.lf.id)
                if (r != null) {
                    _node.value = CurrentFolder.Loaded(r)
                    false
                } else {
                    true
                }
            }
        }


    private fun setToLoading(): CurrentFolder {
        return _node.value.also { _node.value = CurrentFolder.Loading }
    }

    sealed class CurrentFolder {
        data class Loaded(val lf: LinkFolder) : CurrentFolder()
        object Loading : CurrentFolder()
    }

}