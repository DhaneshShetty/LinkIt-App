package com.ddevs.linkit.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddevs.linkit.LinkDAO
import com.ddevs.linkit.LinkDatabase
import com.ddevs.linkit.model.ShareLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class ShareRepository(application: Application) {
    private val database:LinkDatabase= LinkDatabase.getInstance(application)
    private val dao: LinkDAO =database.linkDao

    val getAllSharedLinks: Flow<List<ShareLink>>
       get()=dao.getSharedLinks()

    suspend fun deleteLink(link: ShareLink){
        dao.deleteSharedLink(link)
    }

    suspend fun addLink(link: ShareLink){
        dao.shareNewLink(link)
    }

}