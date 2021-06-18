package com.ddevs.linkit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ddevs.linkit.model.Link
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDAO {
    @Insert
    suspend fun insert(link: Link)

    @Query("SELECT * FROM links_table")
    suspend fun getAllLinks():Flow<List<Link>>

    @Query("SELECT DISTINCT category FROM links_table")
    suspend fun getAllCategory():Flow<List<Link>>
}