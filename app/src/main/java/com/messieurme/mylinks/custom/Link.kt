package com.messieurme.mylinks.custom

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Link(var name: String, var link: String)
