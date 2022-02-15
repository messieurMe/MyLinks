package com.messieurme.mylinks.repository.room

import androidx.room.TypeConverter
import com.messieurme.mylinks.custom.Link
import com.messieurme.mylinks.custom.LinkFolder
import com.messieurme.mylinks.custom.LinkFolderFolded
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class LinkFamilyConverter {

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    @TypeConverter
    fun stringListToString(list: List<String>): String =
        moshi.adapter<List<String>>(
            Types.newParameterizedType(
                List::class.java,
                String::class.java
            )
        ).toJson(list)

    @TypeConverter
    fun linkListToString(list: List<Link>): String =
        moshi.adapter<List<Link>>(
            Types.newParameterizedType(
                List::class.java,
                Link::class.java
            )
        ).toJson(list)!!


    @TypeConverter
    fun stringToStringList(s: String): List<String> =
        moshi.adapter<List<String>>(
            Types.newParameterizedType(
                List::class.java,
                String::class.java
            )
        ).fromJson(s)!!

    @TypeConverter
    fun stringToLinkList(s: String): List<Link> =
        moshi.adapter<List<Link>>(
            Types.newParameterizedType(
                List::class.java,
                Link::class.java
            )
        ).fromJson(s)!!

    @TypeConverter
    fun stringToLinkFamilyList(s: String): List<LinkFolder> =
        moshi.adapter<List<LinkFolder>>(List::class.java).fromJson(s)!!

    @TypeConverter
    fun stringToLinkFamilyFolded(s: String): List<LinkFolderFolded> =
        moshi.adapter<List<LinkFolderFolded>>(
            Types.newParameterizedType(
                List::class.java,
                LinkFolderFolded::class.java
            )
        ).fromJson(s)!!


    @TypeConverter
    fun linkFamilyFoldedToString(lff: List<LinkFolderFolded>): String =
        moshi.adapter<List<LinkFolderFolded>>(
            Types.newParameterizedType(
                List::class.java,
                LinkFolderFolded::class.java
            )
        ).toJson(lff)


}
