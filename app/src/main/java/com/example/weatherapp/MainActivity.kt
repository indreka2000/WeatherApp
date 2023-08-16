package com.example.weatherapp

import ActivityChanger
import LocationClimateDataHandler
import TemperatureDatabase
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var homeButton: ImageButton
    private lateinit var userButton: ImageButton
    private lateinit var historyButton: ImageButton
    private lateinit var searchButton: ImageButton
    private lateinit var activityChanger: ActivityChanger
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var temperatureDatabase: TemperatureDatabase
    private lateinit var dataParser: DataParser
    private lateinit var locationClimateDataHandler: LocationClimateDataHandler

    private lateinit var country: TextView
    private lateinit var city: TextView
    private lateinit var tempValue: TextView
    private lateinit var feelsLikeTempValue: TextView
    private lateinit var humidityValue: TextView
    private lateinit var airPressureValue: TextView
    private lateinit var windSpeedValue: TextView

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        homeButton = findViewById(R.id.homeScreenButton)
        userButton = findViewById(R.id.userScreenButton)
        historyButton = findViewById(R.id.historyScreenButton)
        searchButton = findViewById(R.id.searchScreenButton)
        country = findViewById(R.id.country)
        city = findViewById(R.id.city)
        tempValue = findViewById(R.id.temperatureField)
        feelsLikeTempValue = findViewById(R.id.feelsLikeField)
        humidityValue = findViewById(R.id.humidityField)
        airPressureValue = findViewById(R.id.pressureField)
        windSpeedValue = findViewById(R.id.windSpeedField)


        homeButton.setBackgroundResource(R.drawable.home_button_selected)
        activityChanger = ActivityChanger(homeButton, userButton, historyButton, searchButton)
        activityChanger.setFocus()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        temperatureDatabase = TemperatureDatabase(this)
        locationClimateDataHandler = LocationClimateDataHandler(this)
        dataParser = DataParser()
        TemperatureReminder.setReminder(this)




        getLocation()

    }


    private fun getLocation() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener { location ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses: List<Address> =
                    geocoder.getFromLocation(latitude, longitude, 1) as List<Address>
                if (addresses.isNotEmpty()) {
                    val address: Address = addresses[0]
                    val cityName = address.locality
                    val countryName = address.countryName
                    country.text = countryName
                    city.text = cityName
                    Toast.makeText(applicationContext, "$cityName, $countryName", Toast.LENGTH_LONG).show()

                    latitude = location.latitude
                    longitude = location.longitude

                    fetchClimateDataList(latitude, longitude)
                    locationClimateDataHandler.fetchLocationClimateData(latitude, longitude)
                }

            }
        }
    }

private fun fetchClimateDataList(latitude: Double, longitude: Double) {
    dataParser.fetchClimateDataList(latitude, longitude, object : DataParser.DataParserListener {
        override fun onClimateDataListFetched(climateDataList: List<String>?) {
            if (climateDataList != null) {
                if (climateDataList.isNotEmpty()) {
                    val temperature = climateDataList[0]
                    val feelsLikeTemperature = climateDataList[1]
                    val humidity = climateDataList[2]
                    val airPressure = climateDataList[3]
                    val windSpeed = climateDataList[4]

                    tempValue.text = temperature + "°C"
                    feelsLikeTempValue.text = feelsLikeTemperature + " °C"
                    humidityValue.text = humidity + " %"
                    airPressureValue.text = airPressure + " Pa"
                    windSpeedValue.text = windSpeed + " m/s"

                } else {

                    // Handle the case when the climateDataList is empty
                }
            }
        }
    })
}





    fun addTodayDate() {
        val dateField = findViewById<DatePicker>(R.id.todayDate)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        dateField.init(year, month, day, null)
    }

}
