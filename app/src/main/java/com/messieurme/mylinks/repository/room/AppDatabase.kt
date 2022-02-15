package com.messieurme.mylinks.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.messieurme.mylinks.custom.LinkFolder

@Database(entities = [LinkFolder::class], version = 1)
@TypeConverters(LinkFamilyConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linkFamilyDao(): LinksFamilyDao
}