package com.example.weatherapp

import ActivityChanger
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {

    private lateinit var homeButton : ImageButton
    private lateinit var userButton : ImageButton
    private lateinit var historyButton : ImageButton
    private lateinit var searchButton : ImageButton
    private lateinit var activityChanger : ActivityChanger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user)

        homeButton = findViewById(R.id.homeScreenButton)
        userButton = findViewById(R.id.userScreenButton)
        historyButton = findViewById(R.id.historyScreenButton)
        searchButton = findViewById(R.id.searchScreenButton)

        userButton.setBackgroundResource(R.drawable.user_button_selected)
        activityChanger = ActivityChanger(homeButton, userButton, historyButton, searchButton)
        activityChanger.setFocus()
    }
}