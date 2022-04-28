package com.ddevs.linkit.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.MeetLink
import com.ddevs.linkit.model.ShareLink
import com.ddevs.linkit.repository.LinkRepository
import kotlinx.coroutines.launch

class MeetViewModel(application: Application):AndroidViewModel(application) {
    private val repo: LinkRepository by lazy {  LinkRepository(application) }
    var allLinks: LiveData<List<MeetLink>> = repo.getAllMeets.asLiveData()

    fun deleteCurrentLink(link: MeetLink){
        val instanceWorkManager = WorkManager.getInstance(getApplication())
        instanceWorkManager.cancelUniqueWork(link.meetUrl)
        viewModelScope.launch {
            repo.deleteMeet(link)
        }
    }

    fun copyToClipBoard(link:String){
        val clipboard: ClipboardManager = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("Url",link)
        clipboard.setPrimaryClip(clipData)
    }
}