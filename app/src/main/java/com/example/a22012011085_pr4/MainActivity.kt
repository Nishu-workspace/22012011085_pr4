package com.example.a22012011085_pr4
import com.example.a22012011085_pr4.databinding.ActivityMainBinding
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.annotation.RequiresApi

import java.util.Calendar
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedAlarmTime:Long = 0
    private var remindTime: Long = 0

//    lateinit var previewSelectedTimeTextView: TextView
//    private val timePickerDiaglogListner: TimePickerDialog.OnTimeSetListener = object : TimePickerDialog.OnTimeSetListener{
//        override fun onTimeSet(p0: TimePicker?, hourOfDay: Int , minute: Int) {
//
//                val formattedTime: String = when {
//                hourOfDay == 0 -> {
//                    if (minute < 10) {
//                        "${hourOfDay + 12}:0${minute} am"
//                    } else {
//                        "${hourOfDay + 12}:${minute} am"
//                    }
//                }
//                hourOfDay > 12 -> {
//                    if (minute < 10) {
//                        "${hourOfDay - 12}:0${minute} pm"
//                    } else {
//                        "${hourOfDay - 12}:${minute} pm"
//                    }
//                }
//                hourOfDay == 12 -> {
//                    if (minute < 10) {
//                        "${hourOfDay}:0${minute} pm"
//                    } else {
//                        "${hourOfDay}:${minute} pm"
//                    }
//                }
//                else -> {
//                    if (minute < 10) {
//                        "${hourOfDay}:${minute} am"
//                    } else {
//                        "${hourOfDay}:${minute} am"
//                    }
//                }
//            }
//            previewSelectedTimeTextView.text = formattedTime
//        }
//
//    }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        val createAlarm = findViewById<Button>(R.id.createAlram)
//        createAlarm.setOnClickListener{
//            val timepicker: TimePickerDialog = TimePickerDialog(
//                this,
//                timePickerDiaglogListner,
//                12,
//                10,
//                false
//                )
//            timepicker.show()
//        }
    binding.btn1.setOnClickListener{
        showTimerDialog()
    }
    binding.btn2.setOnClickListener{
        cancelAlarm()
    }
    binding.card2.visibility = View.GONE
    binding.remainderTime.visibility = View.GONE

    val timePicker = binding.remainderTime
    timePicker.hour = getHour()
    timePicker.minute = getMinute()

    }
    private fun getHour():Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = remindTime
        return cal[Calendar.HOUR_OF_DAY]
    }
    private fun getMinute(): Int{
        val cal = Calendar.getInstance()
        cal.timeInMillis = selectedAlarmTime
        return cal[Calendar.MINUTE]
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showTimerDialog(){
        val cldr = Calendar.getInstance()
        val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cldr.get(Calendar.MINUTE)
        val picker = TimePickerDialog(
            this,
            {_, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute)},
            hour,
            minutes,
            false
        )
        picker.show()
    }
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("SimpleDataFormat")
    private fun sendDialogDataToActivity(hour: Int, minute:Int){
        val alarmCalender = Calendar.getInstance()
        alarmCalender.set(Calendar.HOUR_OF_DAY, hour) // hour is in 24-hour format
        alarmCalender.set(Calendar.MINUTE, minute)
        alarmCalender.set(Calendar.SECOND, 0)
        val alarmTimeText = findViewById<TextView>(R.id.alarmTimeText)
        binding.card2.visibility = View.VISIBLE
        alarmTimeText.text = SimpleDateFormat("hh:mm:ss a dd MMM yyyy").format(alarmCalender.time)
        selectedAlarmTime = alarmCalender.timeInMillis
        Toast.makeText(this,"time: ${alarmCalender.timeInMillis}", Toast.LENGTH_SHORT ).show()
        setAlarm(selectedAlarmTime,"Start")
        Toast.makeText(this,"Time: hour ${hour}, minutes: ${minute}", Toast.LENGTH_SHORT).show()
    }
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(millisTime: Long, str: String){

        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1","Start")
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            2407,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(str == "Start"){
            val calendar = Calendar.getInstance()
            binding.remainderTime.visibility = View.VISIBLE
            if(alarmManager.canScheduleExactAlarms()){
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    millisTime,
                    pendingIntent
                )

                Toast.makeText(this, "Alarm set for ${millisTime}", Toast.LENGTH_SHORT).show() // Confirm alarm is set

            }
        }
        else if(str == "Stop") {
            alarmManager.cancel(pendingIntent)
            binding.card2.visibility = View.GONE
        }
    }
    private  fun cancelAlarm(){
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1","Stop")
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 2407, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        binding.card2.visibility = View.GONE
        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show()
    }

}