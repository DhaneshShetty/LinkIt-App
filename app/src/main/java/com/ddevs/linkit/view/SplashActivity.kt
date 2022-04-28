package com.ddevs.linkit.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ddevs.linkit.R
import com.ddevs.linkit.SharingShortcutsManager
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var sharingShortcutsManager: SharingShortcutsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharingShortcutsManager = SharingShortcutsManager().also {
            it.pushDirectShareTargets(this)
        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finishAffinity()
            }
        }, 4000)
    }
}