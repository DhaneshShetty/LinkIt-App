package com.ddevs.linkit.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.ShareLink
import com.ddevs.linkit.repository.LinkRepository
import kotlinx.coroutines.launch

class LinkViewModel(application: Application):AndroidViewModel(application) {
    private val repo: LinkRepository by lazy {  LinkRepository(application) }
    var allLinks: LiveData<List<Link>> = repo.getAllLinks.asLiveData()

    fun addLink(link: Link){
        viewModelScope.launch {
            repo.addLink(link)
        }
    }

    fun deleteCurrentLink(link: Link){
        viewModelScope.launch { repo.deleteLink(link) }
    }

    fun copyToClipBoard(link:String){
        val clipboard: ClipboardManager = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("Url",link)
        clipboard.setPrimaryClip(clipData)
    }
}