package com.ddevs.linkit.view

import android.app.ActivityManager
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.ddevs.linkit.LinkShareService
import com.ddevs.linkit.adapter.ShareRVAdapter
import com.ddevs.linkit.databinding.ActivityShareBinding
import com.ddevs.linkit.viewmodel.ShareViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ShareActivity : AppCompatActivity() {
    private val viewModel: ShareViewModel by lazy{
        ViewModelProvider(this).get(ShareViewModel::class.java)
    }
    private lateinit var binding: ActivityShareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val serviceIntent=Intent(applicationContext, LinkShareService::class.java)
        changeVisibility()
        binding.button.setOnClickListener {
            startShareService(serviceIntent)
            changeVisibility()
        }
        binding.stopButton.setOnClickListener {
            this.stopService(serviceIntent)
            changeVisibility()
        }
        if (Intent.ACTION_SEND == intent.action && "text/plain" == intent.type) {
            val textToShare = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (textToShare != null) {
                viewModel.addNewLink(textToShare)
                if(!isMyServiceRunning(LinkShareService::class.java)){
                    startShareService(serviceIntent)
                    changeVisibility()
                }
            }
        }

        val bottomSheet: ConstraintLayout = binding.sheet.bottomSheet
        val bottomSheetBehavior= BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state= BottomSheetBehavior.STATE_HIDDEN
        binding.fabShare.setOnClickListener {
            bottomSheetBehavior.state= BottomSheetBehavior.STATE_EXPANDED
        }
        binding.sheet.closeSheet.setOnClickListener {
            bottomSheetBehavior.state= BottomSheetBehavior.STATE_HIDDEN
        }
        binding.sheet.shareBtn.setOnClickListener {
            if(!URLUtil.isValidUrl(binding.sheet.shareLink.text.toString())){
                binding.sheet.shareLink.error = "Enter a proper URL with https://, http:// or ftp://"

            }
            else {
                viewModel.addNewLink(binding.sheet.shareLink.text.toString())
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        val shareRVAdapter= ShareRVAdapter(viewModel)
        shareRVAdapter.setSharedLinks(listOf())
        binding.rvShared.adapter=shareRVAdapter
        viewModel.shared.observe(this, {
            if (it != null)
                shareRVAdapter.setSharedLinks(it)
        })
        val wifiManager: WifiManager = this.applicationContext?.getSystemService(WIFI_SERVICE) as WifiManager
        val ipAddress = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress);
        val text=
            "Enter this ip address in LinkIt chrome extension or in browser to view shared links after starting server.Make sure the devices are connected to same wifi network\n IP ADDRESS:$ipAddress:8080"
        binding.ipAdd.text=text
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun changeVisibility(){
        if(isMyServiceRunning(LinkShareService::class.java)){
            binding.button.visibility= View.GONE
            binding.stopButton.visibility= View.VISIBLE
            binding.status.visibility= View.VISIBLE
        }
        else{
            binding.button.visibility= View.VISIBLE
            binding.stopButton.visibility= View.GONE
            binding.status.visibility= View.INVISIBLE
        }
    }

    private fun startShareService(intent: Intent){
        this.startService(intent)
    }

}