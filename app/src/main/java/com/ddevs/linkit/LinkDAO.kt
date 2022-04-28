package com.ddevs.linkit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.MeetLink
import com.ddevs.linkit.model.ShareLink
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDAO {
    @Insert
    suspend fun addMeet(link: MeetLink)

    @Delete
    suspend fun deleteMeet(link:MeetLink)

    @Query("SELECT * FROM meet_link_table")
    fun getAllMeets():Flow<List<MeetLink>>


    @Query("SELECT * FROM share_link_table")
    fun getSharedLinks():Flow<List<ShareLink>>

    @Insert
    suspend fun shareNewLink(link: ShareLink)

    @Delete
    suspend fun deleteSharedLink(link:ShareLink)

    @Query("SELECT * FROM links_table")
    fun getAllLinks():Flow<List<Link>>

    @Query("SELECT DISTINCT category FROM links_table")
    fun getAllCategory():Flow<List<String>>

    @Insert
    suspend fun insertNewLink(link:Link)

    @Delete
    suspend fun deleteLink(link:Link)

    @Query("SELECT * FROM links_table WHERE category=:category")
    fun getCategoryLinks(category:String):Flow<List<Link>>
}