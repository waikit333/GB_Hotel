package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteladapters.RoomServiceItemAdapter
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceItem
import com.google.firebase.database.FirebaseDatabase

class RoomServiceItems : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_service_items)
        val categoryName: TextView = findViewById(R.id.heading_category)
        var category = ""

        if (intent.extras != null) {
            category = intent.extras!!.getString("categoryName").toString()
            categoryName.text = category
        }

        val rv: RecyclerView = findViewById(R.id.roomservice_items)
        rv.addItemDecoration(DividerItemDecoration(applicationContext,
                DividerItemDecoration.VERTICAL))

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val itemIDList: MutableList<String> = mutableListOf()
        val itemList: MutableList<DataClassRoomServiceItem> = mutableListOf()
        //get item IDs
        database.getReference("RoomServiceCategories").get().addOnSuccessListener {
            for (i in it.child("Food").children) {
                if (i.key == category) {
                    for (j in i.children) {
                        itemIDList.add(j.value.toString())
                    }
                }
            }
            for (i in it.child("Drink").children) {
                if (i.key == category) {
                    if (i.key == category) {
                        for (j in i.children) {
                            itemIDList.add(j.value.toString())
                        }
                    }
                }
            }
            database.getReference("RoomServiceItems").get().addOnSuccessListener { items ->
                for (i in itemIDList) {
                    if (items.child(i).exists()) {
                        val item = items.child(i).getValue(DataClassRoomServiceItem::class.java)
                        if (item != null) {
                            item.id = i;
                            itemList.add(item)
                        }
                    }
                }
                rv.adapter = RoomServiceItemAdapter(this, itemList, this.supportFragmentManager)
            }
        }
    }
}