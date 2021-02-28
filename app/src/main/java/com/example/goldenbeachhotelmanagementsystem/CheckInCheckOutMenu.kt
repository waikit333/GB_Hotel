package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CheckInCheckOutMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin_checkout_menu)

        val btn: Button = findViewById<Button>(R.id.view_check_ins_button)
        btn.setOnClickListener{
            val intent = Intent(this, CheckIns::class.java)
            startActivity(intent)
        }


    }
}