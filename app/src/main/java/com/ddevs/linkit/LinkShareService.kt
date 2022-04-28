package com.ddevs.linkit

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.ddevs.linkit.model.ShareLink
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit


class Server(private val dao: LinkDAO) : Runnable {

    private val embeddedServer=embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            gson {}
        }
        routing {
            get("/") {
                dao.getSharedLinks().collect { it1 ->
                    call.respond(it1)
                }
            }
            post("/addLink") {
                val data=call.receiveText()
                dao.shareNewLink(ShareLink(url=data))
                call.respond(HttpStatusCode.Accepted, "Ok")
            }
        }
    }

    override fun run() {
            embeddedServer.start(wait = true)
    }

    fun stopRunning() {
        embeddedServer.stop(0L, 0L, TimeUnit.SECONDS)
    }
}

class LinkShareService : Service() {
    private lateinit var mNM: NotificationManager
    private val NOTIFICATION: Int = 1
    private val CHANNEL_ID = "channel_1"
    private lateinit var thread: Thread
    private lateinit var server: Server

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mNM = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        showNotification()
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val database = LinkDatabase.getInstance(this.applicationContext)
        val dao = database.linkDao
        server = Server(dao)
        thread = Thread(server)
        thread.start()
        val mClipboardManager =
            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        mClipboardManager.addPrimaryClipChangedListener{
            val a=mClipboardManager.primaryClip?.getItemAt(0)?.text
            Toast.makeText(this,a,Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("WrongConstant")
    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            stopForeground(NOTIFICATION)
        server.stopRunning()
    }


    private fun showNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_link_24)
                .setContentTitle("LinkIt Server running")
                .setColor(resources.getColor(R.color.green, theme))
                .setContentText("Access links from other devices")
                .setWhen(System.currentTimeMillis())
                .build()
        startForeground(NOTIFICATION, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Link"
            val descriptionText = "Server channel for link sharing"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}