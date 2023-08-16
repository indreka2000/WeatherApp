// ButtonFocusUtil.kt
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.weatherapp.*

class ActivityChanger(private val homeButton: View, private val userButton: View,
                      private val historyButton: View, private val searchButton: View) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setFocus()
    }

    fun setFocus() {

        val clickListener = View.OnClickListener { view ->
            when (view) {
                homeButton -> {
                    homeButton.setBackgroundResource(R.drawable.home_button_selected)
                    userButton.setBackgroundResource(R.mipmap.user)
                    historyButton.setBackgroundResource(R.mipmap.history)
                    searchButton.setBackgroundResource(R.mipmap.search)

                    val intent = Intent(view.context, MainActivity::class.java)
                    view.context.startActivity(intent)
                    (view.context as Activity).finish()

                }

                userButton -> {
                    userButton.setBackgroundResource(R.drawable.user_button_selected)
                    homeButton.setBackgroundResource(R.mipmap.home)
                    historyButton.setBackgroundResource(R.mipmap.history)
                    searchButton.setBackgroundResource(R.mipmap.search)

                    val intent = Intent(view.context, UserActivity::class.java)
                    view.context.startActivity(intent)
                    (view.context as Activity).finish()

                }

                historyButton -> {
                    historyButton.setBackgroundResource(R.drawable.history_button_selected)
                    userButton.setBackgroundResource(R.mipmap.user)
                    homeButton.setBackgroundResource(R.mipmap.home)
                    searchButton.setBackgroundResource(R.mipmap.search)

                    val intent = Intent(view.context, HistoryActivity::class.java)
                    view.context.startActivity(intent)
                    (view.context as Activity).finish()

                }

                searchButton -> {
                    searchButton.setBackgroundResource(R.drawable.search_button_selected)
                    userButton.setBackgroundResource(R.mipmap.user)
                    historyButton.setBackgroundResource(R.mipmap.history)
                    homeButton.setBackgroundResource(R.mipmap.home)

                    val intent = Intent(view.context, SearchActivity::class.java)
                    view.context.startActivity(intent)
                    (view.context as Activity).finish()

                }
            }
        }

        homeButton.setOnClickListener(clickListener)
        userButton.setOnClickListener(clickListener)
        historyButton.setOnClickListener(clickListener)
        searchButton.setOnClickListener(clickListener)
    }

}
