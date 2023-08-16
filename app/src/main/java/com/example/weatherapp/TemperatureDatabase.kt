import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TemperatureDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "temperature.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "temperature_data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_COUNTRY = "country"
        private const val COLUMN_CITY = "city"
        private const val COLUMN_TEMPERATURE = "temperature"
        private const val COLUMN_FEELS_LIKE_TEMPERATURE = "feels_like_temperature"
        private const val COLUMN_HUMIDITY = "humidity"
        private const val COLUMN_AIR_PRESSURE = "air_pressure"
        private const val COLUMN_WIND_SPEED = "wind_speed"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_CITY TEXT, " +
                "$COLUMN_COUNTRY TEXT, " +
                "$COLUMN_TEMPERATURE REAL, " +
                "$COLUMN_FEELS_LIKE_TEMPERATURE REAL, " +
                "$COLUMN_HUMIDITY REAL, " +
                "$COLUMN_AIR_PRESSURE REAL, " +
                "$COLUMN_WIND_SPEED REAL)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertTemperatureData(
        city: String,
        country: String,
        temperature: Double,
        feelsLikeTemperature: Double,
        humidity: Double,
        airPressure: Double,
        windSpeed: Double
    ): Long {
        val contentValues = ContentValues().apply {
            put(COLUMN_CITY, city)
            put(COLUMN_COUNTRY, country)
            put(COLUMN_TEMPERATURE, temperature)
            put(COLUMN_FEELS_LIKE_TEMPERATURE, feelsLikeTemperature)
            put(COLUMN_HUMIDITY, humidity)
            put(COLUMN_AIR_PRESSURE, airPressure)
            put(COLUMN_WIND_SPEED, windSpeed)
        }

        val db = writableDatabase
        return db.insert(TABLE_NAME, null, contentValues)
    }

    @SuppressLint("Range")
    fun getAllTemperatureData(): List<TemperatureData> {
        val temperatureDataList = mutableListOf<TemperatureData>()
        val selectAllQuery = "SELECT * FROM $TABLE_NAME"

        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery(selectAllQuery, null)
        cursor?.let {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                    val city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
                    val country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))
                    val temperature = cursor.getDouble(cursor.getColumnIndex(COLUMN_TEMPERATURE))
                    val feelsLikeTemperature = cursor.getDouble(cursor.getColumnIndex(COLUMN_FEELS_LIKE_TEMPERATURE))
                    val humidity = cursor.getDouble(cursor.getColumnIndex(COLUMN_HUMIDITY))
                    val airPressure = cursor.getDouble(cursor.getColumnIndex(COLUMN_AIR_PRESSURE))
                    val windSpeed = cursor.getDouble(cursor.getColumnIndex(COLUMN_WIND_SPEED))

                    val temperatureData = TemperatureData(
                        id,
                        city,
                        country,
                        temperature,
                        feelsLikeTemperature,
                        humidity,
                        airPressure,
                        windSpeed
                    )
                    temperatureDataList.add(temperatureData)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }

        return temperatureDataList
    }
}
