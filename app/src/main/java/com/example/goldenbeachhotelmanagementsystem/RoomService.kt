package com.example.goldenbeachhotelmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteladapters.RoomServiceCategoryAdapter
import com.example.goldenbeachhoteldataclasses.DataSource

class RoomService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_service)

        val foodDataSet = DataSource().loadRoomServiceFoodCategories()
        val recyclerView = findViewById<RecyclerView>(R.id.food_categories)
        recyclerView.adapter = RoomServiceCategoryAdapter(this, foodDataSet)
    }
}