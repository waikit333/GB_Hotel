package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.helperclasses.Helper

class CheckInCheckOutMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin_checkout_menu)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Check In / Check Out"

        findViewById<Button>(R.id.view_check_ins_button).setOnClickListener{
            val intent = Intent(this, CheckIns::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.view_check_outs_button).setOnClickListener{
            val intent = Intent(this, CheckOuts::class.java)
            startActivity(intent)
        }


    }
}