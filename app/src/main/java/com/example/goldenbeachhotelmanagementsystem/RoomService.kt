package com.example.goldenbeachhotelmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteladapters.RoomServiceCategoryAdapter
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceCategory
import com.google.firebase.database.FirebaseDatabase

class RoomService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_service)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Room Service"

        var database : FirebaseDatabase = FirebaseDatabase.getInstance()
        var foodList : MutableList<DataClassRoomServiceCategory> =  mutableListOf()
        var drinkList : MutableList<DataClassRoomServiceCategory> =  mutableListOf()

        //load food
        database.getReference("RoomServiceCategories").child("Food").get().addOnSuccessListener{
            for (i in it.children){
                val name = i.key
                foodList.add(DataClassRoomServiceCategory(name,""))
            }
            val recyclerView = findViewById<RecyclerView>(R.id.food_categories)
            recyclerView.adapter = RoomServiceCategoryAdapter(this, foodList)
        }

        //load drink
        database.getReference("RoomServiceCategories").child("Drink").get().addOnSuccessListener{
            for (i in it.children){
                val name = i.key
                drinkList.add(DataClassRoomServiceCategory(name,""))
            }
            val recyclerView = findViewById<RecyclerView>(R.id.drink_categories)
            recyclerView.adapter = RoomServiceCategoryAdapter(this, drinkList)
        }
    }
}