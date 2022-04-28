package com.ddevs.linkit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ddevs.linkit.NotifyWork
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.MeetLink
import com.ddevs.linkit.repository.LinkRepository
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class AddLinkViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: LinkRepository by lazy {  LinkRepository(application)}

    fun addLink(link: Link){
        viewModelScope.launch {
            repo.addLink(link)
        }
    }

    fun addMeetLink(link: MeetLink,hour:Int,minute:Int){
        val gc = GregorianCalendar()
        gc.timeInMillis =link.date
        val day = gc[Calendar.DAY_OF_MONTH]
        val month = gc[Calendar.MONTH]
        val year = gc[Calendar.YEAR]
        val customCalendar = Calendar.getInstance()
        customCalendar.set(
            year, month, day, hour, minute, 0
        )
        val customTime = customCalendar.timeInMillis
        val currentTime = System.currentTimeMillis()
        if (customTime > currentTime) {
            val data = Data.Builder().putInt(NotifyWork.NOTIFICATION_ID, 0).build()
            val delay = customTime - currentTime
            scheduleNotification(delay, data,link.meetUrl)
        }
        viewModelScope.launch {
            repo.addNewMeet(link)
        }
    }

    private fun scheduleNotification(delay: Long, data: Data,meetLink: String) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(getApplication())
        instanceWorkManager.beginUniqueWork(
            meetLink,
            ExistingWorkPolicy.APPEND,
            notificationWork
        ).enqueue()
    }

}