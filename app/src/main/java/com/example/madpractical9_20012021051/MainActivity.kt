package com.example.madpractical9_20012021051

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.SMSView
import com.example.madpractical9_20012021051.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val SMS_PERMISSION_CODE=110
    private  lateinit var binder: ActivityMainBinding
    private lateinit var al:ArrayList<SMSView>
    private lateinit var lv: ListView

    private fun requestSMSPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS))
        {
        }
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_SMS,
            Manifest. permission. SEND_SMS ,
            Manifest. permission. RECEIVE_SMS),
            SMS_PERMISSION_CODE)
        }
    private  fun loadSMSInbox(){
        if(!checkRequestPermission()) return
        val uriSMS=Uri.parse("content://sms/inbox")
        val c=contentResolver.query(uriSMS,null,null,null,null)
        al.clear()
        while (c!!.moveToNext()){
            al.add(SMSView(c.getString(2),c.getString(12)))
        }
        lv.adapter=SMSViewAdapter(this, al)
    }

    private fun checkRequestPermission(): Boolean{
        return isSMSReadPermission && isSMSWritePermission
    }

    private val isSMSReadPermission:Boolean
    get() = ContextCompat.checkSelfPermission(this , Manifest.permission.READ_SMS)==PackageManager.PERMISSION_GRANTED
    private val isSMSWritePermission:Boolean
    get() = ContextCompat.checkSelfPermission(this , Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}