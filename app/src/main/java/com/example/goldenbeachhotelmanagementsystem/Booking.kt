package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteladapters.BookingRecyclerAdapter
import com.example.goldenbeachhoteldataclasses.DataClassBookingItem
import com.example.helperclasses.Helper
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Booking : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var myAdapter: BookingRecyclerAdapter
    private lateinit var bookingList: MutableList<DataClassBookingItem>
    private lateinit var rvBooking: RecyclerView
    private lateinit var helper: Helper
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var svBooking: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        //show action bar with back arrow
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.bookingDrawerLayout)
        setSupportActionBar(toolbar)
        helper = Helper()
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

        if (svBooking != null) {
            svBooking.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    search(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    search(query)
                    return false
                }

            })
        }

    }


    fun addNewBookingOnClick(v: View) {
        val intent = Intent(this, AddNewBooking::class.java)
        startActivity(intent)
    }

    fun btnSortOnClick(v: View) {
        showSortDialog()
    }

    private fun showSortDialog() {
        val sortOptions = arrayOf("Newest", "Oldest")
        val builder = AlertDialog.Builder(this)
        var sortAdapter: BookingRecyclerAdapter
        with(builder) {
            setTitle("Sort by:")
            setIcon(R.drawable.icon_material_sort)
            setItems(sortOptions) { _, which ->
                if (which == 0) {
                    bookingList.sortByDescending { it.bookingDate }
                } else {
                    bookingList.sortBy { it.bookingDate }
                }
                sortAdapter = BookingRecyclerAdapter(this@Booking, bookingList)
                rvBooking.adapter = sortAdapter
            }
        }
        builder.show()
    }

    private fun search(query: String) {
        val searchBookingList = mutableListOf<DataClassBookingItem>()
        if (!query.isEmpty()) {
            for (booking in bookingList) {
                if (booking.custName?.toLowerCase()?.contains(query.toLowerCase()) == true) {
                    searchBookingList.add(booking)
                }
            }
            val searchAdapter = BookingRecyclerAdapter(this, searchBookingList)
            rvBooking.removeAllViewsInLayout()
            rvBooking.adapter = searchAdapter
        } else {
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
                        var type = typeSnapshot.key.toString()
                        for (custSnapshot in typeSnapshot.children) {
                            var custID = custSnapshot.key.toString()
                            for (child in custSnapshot.children) {
                                var bookingID = child.key.toString()
                                val bookingDate = child.child("bookingDate").value.toString()
                                val fromTo =
                                    dateSnapshot.key.toString() + "-" + child.child("to").value.toString()
                                val booking = DataClassBookingItem(
                                    fromTo, bookingDate, "",custID,bookingID,type
                                )
                                var cusRef = database.getReference("Customers")
                                cusRef.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        rvBooking.adapter = null
                                        for (child in snapshot.children) {
                                            if (child.key.equals(custID)) {
                                                booking.custName =
                                                    child.child("firstName").value.toString() + " " + child.child(
                                                        "lastName"
                                                    ).value.toString()
                                                bookingList.add(booking)

                                            }
                                        }
                                        rvBooking.removeAllViewsInLayout()
                                        myAdapter = BookingRecyclerAdapter(context, bookingList)
                                        rvBooking.adapter = myAdapter
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.w(
                                            "cancelled",
                                            "Unable to get customer data",
                                            error.toException()
                                        )
                                    }
                                })
                            }
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("cancelled", "Unable to get booking data", error.toException())
            }

        })
    }

    class BookingViewHolder(var bview: View) : RecyclerView.ViewHolder(bview) {
        fun bind(bookingItem: DataClassBookingItem) {
            val fromTo = bview.findViewById<TextView>(R.id.txtBookingFromTo)
            val bookingDate = bview.findViewById<TextView>(R.id.txtBookingDate)
            val custName = bview.findViewById<TextView>(R.id.txtCustName)
            fromTo?.text = bookingItem.fromTo
            bookingDate?.text = bookingItem.bookingDate
            custName?.text = bookingItem.custName
        }
    }

    private fun loadFirebase() {
        var bQuery = database
            .getReference("Booking")
            .child("").child("categories")
            .limitToLast(50)

        val options = FirebaseRecyclerOptions.Builder<DataClassBookingItem>()
            .setQuery(bQuery, DataClassBookingItem::class.java)
            .setLifecycleOwner(this)
            .build()
        val adapter =
            object : FirebaseRecyclerAdapter<DataClassBookingItem, BookingViewHolder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): BookingViewHolder {
                    return BookingViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_booking, parent, false)
                    )
                }

                override fun onBindViewHolder(
                    holder: BookingViewHolder,
                    position: Int,
                    model: DataClassBookingItem
                ) {
                    holder.bind(model)
                }

            }

    }

}