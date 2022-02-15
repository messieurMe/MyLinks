package com.messieurme.mylinks.repository.room

import androidx.room.*
import com.messieurme.mylinks.custom.Link
import com.messieurme.mylinks.custom.LinkFolder
import com.messieurme.mylinks.custom.LinkFolderFolded

@Dao
interface LinksFamilyDao {

    @Query("SELECT * FROM LinkFolder")
    suspend fun getAll(): List<LinkFolder>

    @Query("SELECT * FROM LinkFolder WHERE id=:id")
    suspend fun getSafe(id: Long): LinkFolder?

    @Query("SELECT * FROM LinkFolder WHERE id=:id")
    suspend fun get(id: Long): LinkFolder

    @Query("DELETE FROM LinkFolder WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("UPDATE LinkFolder SET links=:newLinks WHERE id=:id")
    suspend fun updateLinks(newLinks: List<Link>, id: Long)

    @Query("UPDATE LinkFolder SET linkFolders=:newLinks WHERE id=:id")
    suspend fun updateLff(newLinks: List<LinkFolderFolded>, id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(lf: LinkFolder)

    @Delete
    suspend fun delete(lf: LinkFolder)


}