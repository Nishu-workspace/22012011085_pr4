package com.example.a22012011085_pr4


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
        override fun onReceive(context: Context, intent: Intent) {
            val str1 = intent.getStringExtra("Service1")
            Log.d("AlarmBroadcastReceiver", "str1 value: $str1")
            val serviceIntent = Intent(context, AlarmService::class.java)
            if (str1 == "Start") {
                context.startService(serviceIntent)
                Log.d("AlarmReceiver", "Starting AlarmService")
                serviceIntent.putExtra("Service1", intent.getStringExtra("Service1"))

            }
        else if (str1 == "Stop") {
            context.stopService(serviceIntent)
            Log.d("AlarmReciver","Stopping AlarmService")
        }
        }
    }
