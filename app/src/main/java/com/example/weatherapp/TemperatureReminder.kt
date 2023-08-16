package com.example.weatherapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.tv.BroadcastInfoRequest
import android.widget.Toast
import java.util.*

class TemperatureReminder : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // This method will be called when the reminder is triggered
        // Perform the temperature checking operation here
        Toast.makeText(context, "Time to check the temperature!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun setReminder(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TemperatureReminder::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

            // Set the reminder time to 12:00 PM
            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 12)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }

            // Set the alarm to trigger every day at 12:00 PM
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        fun cancelReminder(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TemperatureReminder::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

            // Cancel the alarm
            alarmManager.cancel(pendingIntent)
        }
    }
}