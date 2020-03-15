package ua.ck.zabochen.mapapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUi()
    }

    private fun setUi(){
        // Layout
        setContentView(R.layout.activity_main)

        activityMain_button_openMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

    }
}
