package com.ddevs.linkit.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.*
import com.ddevs.linkit.model.ShareLink
import com.ddevs.linkit.repository.ShareRepository
import kotlinx.coroutines.launch

class ShareViewModel(application: Application) : AndroidViewModel(application) {
    private val repo:ShareRepository= ShareRepository(application)
    var shared:LiveData<List<ShareLink>> = repo.getAllSharedLinks.asLiveData()

    fun deleteCurrentLink(link:ShareLink){
        viewModelScope.launch { repo.deleteLink(link) }
    }

    fun copyToClipBoard(link:String){
        val clipboard:ClipboardManager= getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("Url",link)
        clipboard.setPrimaryClip(clipData)
    }

    fun addNewLink(link: String){
        viewModelScope.launch {
            val sharedLink = ShareLink(url = link)
            repo.addLink(sharedLink)
        }
    }

}