package com.example.weatherapp

import ActivityChanger
import TemperatureData
import TemperatureDataAdapter
import TemperatureDatabase
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var homeButton: ImageButton
    private lateinit var userButton: ImageButton
    private lateinit var historyButton: ImageButton
    private lateinit var searchButton: ImageButton
    private lateinit var activityChanger: ActivityChanger
    private lateinit var temperatureTextView: TextView

    private lateinit var historyListView: ListView
    private lateinit var temperatureDatabase: TemperatureDatabase
    private lateinit var temperatureDataList: List<TemperatureData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_history)

        homeButton = findViewById(R.id.homeScreenButton)
        userButton = findViewById(R.id.userScreenButton)
        historyButton = findViewById(R.id.historyScreenButton)
        searchButton = findViewById(R.id.searchScreenButton)
        historyListView = findViewById(R.id.history)

        historyButton.setBackgroundResource(R.drawable.history_button_selected)
        activityChanger = ActivityChanger(homeButton, userButton, historyButton, searchButton)
        activityChanger.setFocus()

        historyListView = findViewById(R.id.history)
        temperatureDatabase = TemperatureDatabase(this)

        // Retrieve all temperature data from the database
        temperatureDataList = temperatureDatabase.getAllTemperatureData()

        // Create an adapter to display the temperature data in the ListView
        val adapter = TemperatureDataAdapter(this, temperatureDataList)

        // Set the adapter to the ListView
        historyListView.adapter = adapter

    }
    override fun onDestroy() {
        super.onDestroy()
        temperatureDatabase.close()
    }

}

