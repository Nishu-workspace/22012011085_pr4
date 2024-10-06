package com.example.a22012011085_pr4


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class AlarmBroadcastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
        override fun onReceive(context: Context, intent: Intent) {
            val str1 = intent.getStringExtra("Service1")
            Log.d("AlarmBroadcastReceiver", "str1 value: $str1")
        Toast.makeText(context, "AlarmReceiver: Action is $str1", Toast.LENGTH_SHORT).show()
            val serviceIntent = Intent(context, AlarmService::class.java)
        serviceIntent.putExtra("Service1", intent.getStringExtra("Service1"))
            if (str1 == "Start") {
                context.startForegroundService(serviceIntent)
                Log.d("AlarmReceiver", "Starting AlarmService")


            }
        else if (str1 == "Stop") {
            context.stopService(serviceIntent)
            Log.d("AlarmReceiver","Stopping Alarm ")
        }
        }
    }
