package com.messieurme.mylinks.custom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class which stores full information about folder
 */
@Entity
data class LinkFolder(
    @PrimaryKey val id: Long,
    @ColumnInfo var name: String,
    @ColumnInfo var path: String,
    @ColumnInfo var parent: Long,
    @ColumnInfo var tags: List<String> = listOf(),
    @ColumnInfo var links: List<Link> = emptyList(),
    @ColumnInfo var linkFolders : List<LinkFolderFolded> = emptyList(),
) {}