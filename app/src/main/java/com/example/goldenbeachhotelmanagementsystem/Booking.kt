package com.example.goldenbeachhotelmanagementsystem

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteladapters.BookingRecyclerAdapter
import com.example.goldenbeachhoteladapters.RoomServiceCategoryAdapter
import com.example.goldenbeachhoteldataclasses.DataClassBooking
import com.example.goldenbeachhoteldataclasses.DataClassBookingItem
import com.example.goldenbeachhoteldataclasses.DataClassCustomer
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceCategory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Booking : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var myAdapter: BookingRecyclerAdapter
    private lateinit var bookingList: MutableList<DataClassBookingItem>
    private lateinit var rvBooking: RecyclerView
    private lateinit var helper: helper
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var svBooking: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        //show action bar with back arrow
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        drawerLayout = findViewById(R.id.bookingDrawerLayout)
        setSupportActionBar(toolbar)
        helper = helper()
        helper.changeNavIconAndTitle(
            supportActionBar,
            false,
            toolbar,
            drawerLayout,
            this,
            getString(R.string.booking)
        )

        rvBooking = findViewById(R.id.rvBooking)

        initRecycleView()
        val layoutManager = LinearLayoutManager(this)
        rvBooking.layoutManager = layoutManager
        rvBooking.addItemDecoration(
            DividerItemDecoration(
                rvBooking.context,
                layoutManager.orientation
            )
        )
        svBooking = findViewById(R.id.svBooking)

    }

    fun addNewBookingOnClick(v: View) {
        val intent = Intent(this, AddNewBookingCointainer::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if (svBooking != null) {
            svBooking.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    search(newText,false)
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    search(query,true)
                    return false
                }

            })
        }
    }

    private fun search(query:String,submit:Boolean){
        val searchBookingList = mutableListOf<DataClassBookingItem>()
        if(submit || !query.isEmpty()){
            for (booking in bookingList){
                if (booking.custName?.toLowerCase()?.contains(query) == true){
                    searchBookingList.add(booking)
                }
            }
            val searchAdapter = BookingRecyclerAdapter(this,searchBookingList)
            rvBooking.adapter = searchAdapter
        }
        else{
            rvBooking.adapter = myAdapter
        }

    }
    private fun initRecycleView() {
        database = FirebaseDatabase.getInstance()
        bookingList = mutableListOf()
        var ref = database.getReference("Bookings")
        val context = this
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dateSnapshot in snapshot.children) {
                    for (typeSnapshot in dateSnapshot.children) {
                        for (custSnapshot in typeSnapshot.children) {
                            var custID = custSnapshot.key.toString()
                            for (child in custSnapshot.children) {
                                val bookingDate = child.child("bookingDate").value.toString()
                                val fromTo =
                                    dateSnapshot.key.toString() + "-" + child.child("to").value.toString()
                                val booking = DataClassBookingItem(
                                    fromTo, bookingDate, ""
                                )
                                var cusRef = database.getReference("Customers")
                                cusRef.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (child in snapshot.children) {
                                            if (child.key.equals(custID)) {
                                                booking.custName =
                                                    child.child("firstName").value.toString() + " " + child.child(
                                                        "lastName"
                                                    ).value.toString()
                                                bookingList.add(booking)

                                            }
                                        }
                                        myAdapter = BookingRecyclerAdapter(context,bookingList)
                                        rvBooking.adapter = myAdapter
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.w("cancelled","Unable to get customer data",error.toException())
                                    }
                                })
                            }
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("cancelled","Unable to get booking data",error.toException())
            }

        })
    }


}