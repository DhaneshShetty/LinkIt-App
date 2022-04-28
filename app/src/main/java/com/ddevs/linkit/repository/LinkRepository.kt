package com.ddevs.linkit.repository

import android.app.Application
import com.ddevs.linkit.LinkDAO
import com.ddevs.linkit.LinkDatabase
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.MeetLink
import kotlinx.coroutines.flow.Flow

class LinkRepository(application: Application) {
    private val database: LinkDatabase = LinkDatabase.getInstance(application)
    private val dao: LinkDAO = database.linkDao

    val getAllLinks: Flow<List<Link>>
        get() = dao.getAllLinks()

    val getAllMeets: Flow<List<MeetLink>>
        get()=dao.getAllMeets()

    suspend fun deleteLink(link: Link) {
        dao.deleteLink(link)
    }

    suspend fun addLink(link: Link) {
        dao.insertNewLink(link)
    }

    suspend fun addNewMeet(link: MeetLink){
        dao.addMeet(link)
    }

    suspend fun deleteMeet(link:MeetLink){
        dao.deleteMeet(link)
    }
}