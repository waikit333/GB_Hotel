package com.example.goldenbeachhotelmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

class AddNewBookingCointainer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_booking_cointainer)
        //show action bar with back arrow
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.addNewBoookingDL)
        setSupportActionBar(toolbar)
        val helper = helper()
        helper.changeNavIconAndTitle(supportActionBar,false,toolbar,drawerLayout, this,getString(R.string.addNewBooking))
    }
}