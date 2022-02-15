package com.messieurme.mylinks.custom

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Short info about folder
 */
@JsonClass(generateAdapter = true)
data class LinkFolderFolded(
    val id: Long,
    val name: String,
    var tags: List<String> = listOf(),
) {
}
