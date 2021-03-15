package com.example.goldenbeachhotelmanagementsystem

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Booking : AppCompatActivity() {
    private lateinit var bookingAdapter : BookingRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        initRecycleView()
        val rv: RecyclerView = findViewById(R.id.roomservice_items)
        rv.addItemDecoration(DividerItemDecoration(applicationContext,
                DividerItemDecoration.VERTICAL))

    }
    fun addNewBookingOnClick(v: View){
        val manager: FragmentManager = supportFragmentManager
        val fragment =  AddNewBooking()
        fragment.arguments = intent.extras
        val bookSearch = findViewById<CardView>(R.id.bookingSearch)
        bookSearch.visibility = View.INVISIBLE
        val btnAdd = findViewById<ImageButton>(R.id.btnAddNewBooking)
        btnAdd.visibility = View.INVISIBLE
        manager.beginTransaction().add(R.id.bookingView,fragment).commit()

    }
    private fun initRecycleView(){
        val rvBooking= findViewById<RecyclerView>(R.id.rvBooking)
        rvBooking.apply{
            layoutManager = LinearLayoutManager(this@Booking, LinearLayoutManager.HORIZONTAL,false)
            //bookingAdapter = BookingRecyclerAdapter()
           // adapter = bookingAdapter
        }
    }
}