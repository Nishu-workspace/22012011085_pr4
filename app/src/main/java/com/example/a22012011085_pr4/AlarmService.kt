package com.example.a22012011085_pr4


import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class AlarmService : Service() {
    private var mp: MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "AlarmService started", Toast.LENGTH_SHORT).show()
        val action = intent?.getStringExtra("Service1")
        Log.d("AlarmService", "Received Action: $action")


        if(action == "Start"){
            if (mp == null){
            mp = MediaPlayer.create(this,R.raw.alarm)
            mp?.isLooping = true
            mp?.start()
            Log.d("AlarmService", "Alarm Started")

            }
        }else if(action == "Stop"){
            mp?.stop()
            mp?.release()
            mp = null
            Log.d("AlarmService", "Alarm Stopped")
            stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        mp?.stop()
        mp?.release()
        mp = null
        Log.d("AlarmService", "Service Destroyed")
        super.onDestroy()
    }
}