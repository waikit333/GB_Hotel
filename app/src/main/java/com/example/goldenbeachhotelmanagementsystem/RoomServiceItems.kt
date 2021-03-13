package com.example.goldenbeachhotelmanagementsystem

import android.app.PendingIntent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class RoomServiceItems : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_service_items)
        val categoryName: TextView = findViewById(R.id.heading_category) as TextView

        if (intent.extras != null) {
            categoryName.text = intent.extras!!.getString("categoryName")
        }

    }
}