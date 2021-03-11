package com.example.goldenbeachhoteldataclasses

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DataSource {

    private lateinit var database : FirebaseDatabase

    fun loadRoomServiceFoodCategories():List<DataClassRoomServiceCategory>{
        database  = FirebaseDatabase.getInstance()
        var foodList : MutableList<DataClassRoomServiceCategory> =  mutableListOf()

        database.getReference("RoomServiceCategories").child("Food").get().addOnSuccessListener {
            for (i in it.children) {
                Log.d("TAG", it.toString())
                var category = i.getValue(DataClassRoomServiceCategory::class.java)
                if (category != null) {
                    foodList.add(category)
                }
            }
        }

        return foodList
    }


}