package com.example.goldenbeachhotelmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class RoomManagement : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_management)
        val floorSpinner = findViewById<Spinner>(R.id.floorSpinner)
        val selectedFloor = findViewById<TextView>(R.id.txtFloor)
        ArrayAdapter.createFromResource(this,R.array.floor_num,android.R.layout.simple_spinner_item).also{
            floorAdapter -> floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            floorSpinner.adapter = floorAdapter
        }
        floorSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}