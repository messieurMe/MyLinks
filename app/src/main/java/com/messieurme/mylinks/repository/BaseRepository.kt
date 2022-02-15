package com.messieurme.mylinks.repository

import com.messieurme.mylinks.custom.Counter
import com.messieurme.mylinks.custom.Link
import com.messieurme.mylinks.custom.LinkFolder
import com.messieurme.mylinks.custom.LinkFolderFolded
import com.messieurme.mylinks.repository.room.LinksFamilyDao
import java.lang.Integer.min
import javax.inject.Inject

class BaseRepository @Inject constructor(
    private val lf: LinksFamilyDao,
    private val counter: Counter
) {
    suspend fun getRoot(): LinkFolder {
        lf.getSafe(0) ?: lf.add(LinkFolder(id = 0L, "root", "./root", -1))
        return lf.get(0)
    }

    suspend fun onBackPressed(current: Long): LinkFolder? {
        val parentId = get(current).parent
        return if (parentId == -1L) null else get(parentId)
    }

    suspend fun get(id: Long): LinkFolder {
        return lf.get(id)
    }

    suspend fun add(
        current: LinkFolder,
        name: String,
        tags: List<String>,
        position: Int?
    ): LinkFolder {
        val newLF =
            if (position == null) {
                LinkFolder(
                    name = name,
                    tags = tags,
                    path = "${current.path}/$name",
                    id = counter.inc(),
                    parent = current.id
                )
            } else {
                lf.get(current.linkFolders[position].id).copy(
                    name = name, tags = tags, path = "${current.path}/$name"
                )
            }
        lf.add(newLF)
        lf.add(
            current.apply {
                linkFolders = concatWithNewItem(
                    position,
                    linkFolders,
                    LinkFolderFolded(newLF.id, newLF.name, newLF.tags)
                )
            }
        )
        return lf.get(current.id)
    }

    private fun <E> concatWithNewItem(position: Int?, list: List<E>, item: E): List<E> {
        return if (position == null) {
            list + item
        } else {
            list.subList(0, position) +
                    item +
                    list.subList(min(list.size, position + 1), list.size)
        }
    }


    suspend fun add(current: Long, name: String, link: String, position: Int? = null): LinkFolder {
        lf.add(
            lf.get(current).apply {
                links = concatWithNewItem(position, links, Link(name, link))
            }
        )
        return lf.get(current)
    }

    suspend fun delete(id: Long) {
        lf.delete(id)
    }

    suspend fun updateLinks(id: Long, newLinks: List<Link>) {
        lf.updateLinks(newLinks, id)
    }

    suspend fun updateLF(id: Long, lff: List<LinkFolderFolded>) {
        lf.updateLff(lff, id)
    }

}