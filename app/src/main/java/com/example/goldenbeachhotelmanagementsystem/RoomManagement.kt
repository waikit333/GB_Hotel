package com.example.goldenbeachhotelmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.database.*
import com.example.goldenbeachhoteldataclasses.*
import com.example.helperclasses.Helper

class RoomManagement : AppCompatActivity() {
    private var selectedFloor = 1
    private var selectedRoom = 1
    private lateinit var database: FirebaseDatabase
    private lateinit var roomRef: DatabaseReference
    private lateinit var roomServiceRef: DatabaseReference
    private lateinit var bookedRoomRef: DatabaseReference
    private lateinit var roomServiceItemRef: DatabaseReference
    private lateinit var txtTypeOfRoom: TextView
    private var roomID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_management)

        //show action bar with back arrow
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.roomManagermentDrawerLayout)
        setSupportActionBar(toolbar)
        var helper = Helper()
        helper.changeNavIconAndTitle(
            supportActionBar,
            false,
            toolbar,
            drawerLayout,
            this,
            getString(R.string.roomManagement)
        )

        val floorSpinner = findViewById<Spinner>(R.id.floorSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.floor_num,
            android.R.layout.simple_spinner_item
        ).also { floorAdapter ->
            floorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            floorSpinner.adapter = floorAdapter
        }
        floorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    selectedFloor = parent.selectedItem.toString().toInt()
                }
            }

        }
        val cvRoom1 = findViewById<CardView>(R.id.cvRoom1)
        cvRoom1.setOnClickListener {
            selectedRoom = 1
            readData()
        }
        val cvRoom2 = findViewById<CardView>(R.id.cvRoom2)
        cvRoom2.setOnClickListener {
            selectedRoom = 2
            readData()
        }
        val cvRoom3 = findViewById<CardView>(R.id.cvRoom3)
        cvRoom3.setOnClickListener {
            selectedRoom = 3
            readData()
        }
        val cvRoom4 = findViewById<CardView>(R.id.cvRoom4)
        cvRoom4.setOnClickListener {
            selectedRoom = 4
            readData()
        }
        val cvRoom5 = findViewById<CardView>(R.id.cvRoom5)
        cvRoom5.setOnClickListener {
            selectedRoom = 5
            readData()
        }
        val cvRoom6 = findViewById<CardView>(R.id.cvRoom6)
        cvRoom6.setOnClickListener {
            selectedRoom = 6
            readData()
        }
        val cvRoom7 = findViewById<CardView>(R.id.cvRoom7)
        cvRoom7.setOnClickListener {
            selectedRoom = 7
            readData()
        }
        val cvRoom8 = findViewById<CardView>(R.id.cvRoom8)
        cvRoom8.setOnClickListener {
            selectedRoom = 8
            readData()
        }
        val cvRoom9 = findViewById<CardView>(R.id.cvRoom9)
        cvRoom9.setOnClickListener {
            selectedRoom = 9
            readData()
        }
        val cvRoom10 = findViewById<CardView>(R.id.cvRoom10)
        cvRoom10.setOnClickListener {
            selectedRoom = 10
            readData()
        }
        val cvRoom11 = findViewById<CardView>(R.id.cvRoom11)
        cvRoom11.setOnClickListener {
            selectedRoom = 11
            readData()
        }
        val cvRoom12 = findViewById<CardView>(R.id.cvRoom12)
        cvRoom12.setOnClickListener {
            selectedRoom = 12
            readData()
        }
        val cvRoom13 = findViewById<CardView>(R.id.cvRoom13)
        cvRoom13.setOnClickListener {
            selectedRoom = 13
            readData()
        }
        val cvRoom14 = findViewById<CardView>(R.id.cvRoom14)
        cvRoom14.setOnClickListener {
            selectedRoom = 14
            readData()
        }
        val cvRoom15 = findViewById<CardView>(R.id.cvRoom15)
        cvRoom15.setOnClickListener {
            selectedRoom = 15
            readData()
        }
        val cvRoom16 = findViewById<CardView>(R.id.cvRoom16)
        cvRoom16.setOnClickListener {
            selectedRoom = 16
            readData()
        }
        val cvRoom17 = findViewById<CardView>(R.id.cvRoom17)
        cvRoom17.setOnClickListener {
            selectedRoom = 17
            readData()
        }
        val cvRoom18 = findViewById<CardView>(R.id.cvRoom18)
        cvRoom18.setOnClickListener {
            selectedRoom = 18
            readData()
        }
        val cvRoom19 = findViewById<CardView>(R.id.cvRoom19)
        cvRoom19.setOnClickListener {
            selectedRoom = 19
            readData()
        }
        val cvRoom20 = findViewById<CardView>(R.id.cvRoom20)
        cvRoom20.setOnClickListener {
            selectedRoom = 20
            readData()
        }
        val cvRoom21 = findViewById<CardView>(R.id.cvRoom21)
        cvRoom21.setOnClickListener {
            selectedRoom = 21
            readData()
        }
        val cvRoom22 = findViewById<CardView>(R.id.cvRoom22)
        cvRoom22.setOnClickListener {
            selectedRoom = 22
            readData()
        }
        val cvRoom23 = findViewById<CardView>(R.id.cvRoom23)
        cvRoom23.setOnClickListener {
            selectedRoom = 23
            readData()
        }
        val cvRoom24 = findViewById<CardView>(R.id.cvRoom24)
        cvRoom24.setOnClickListener {
            selectedRoom = 24
            readData()
        }
        val cvRoom25 = findViewById<CardView>(R.id.cvRoom25)
        cvRoom25.setOnClickListener {
            selectedRoom = 25
            readData()
        }
        val cvRoom26 = findViewById<CardView>(R.id.cvRoom26)
        cvRoom26.setOnClickListener {
            selectedRoom = 26
            readData()
        }
        val cvRoom27 = findViewById<CardView>(R.id.cvRoom27)
        cvRoom27.setOnClickListener {
            selectedRoom = 27
            readData()
        }
        val cvRoom28 = findViewById<CardView>(R.id.cvRoom28)
        cvRoom28.setOnClickListener {
            selectedRoom = 28
            readData()
        }
    }

    fun btnRequestOnClick(view: View) {
        var txtCleaningStatus = findViewById<TextView>(R.id.txtCleaningStatus)
        if (txtCleaningStatus.text.equals("-")) {
            roomRef.child(txtTypeOfRoom.text.toString()).child(roomID).child("cleaningStatus")
                .setValue(true).addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Cleaning Services is requested! The cleaner will clean the room as soon as possible",
                        Toast.LENGTH_LONG
                    ).show()
                }
            txtCleaningStatus.text = "Requested"
        } else {
            Toast.makeText(this, "This room has requested the cleaning service!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun readData() {
        var bookingID = ""
        var custID = ""
        var roomService = ""
        var roomServiceList: MutableList<DataClassRoomService>
        database = FirebaseDatabase.getInstance()
        roomRef = database.getReference("Rooms")
        var txtFloor = findViewById<TextView>(R.id.txtFloor)
        var txtRoom = findViewById<TextView>(R.id.txtRoom)
        var txtAvailability = findViewById<TextView>(R.id.txtAvailability)
        var txtRoomStatus = findViewById<TextView>(R.id.txtRoomStatus)
        var txtCust = findViewById<TextView>(R.id.txtCust)
        txtTypeOfRoom = findViewById(R.id.txtTypeOfRoom)
        var txtCleaningStatus = findViewById<TextView>(R.id.txtCleaningStatus)
        var txtRoomService = findViewById<TextView>(R.id.txtRoomService)
        var type = ""
        var status = ""
        var cleaningStatus = true
        roomID = "F" + selectedFloor + "R" + selectedRoom
        roomRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (typeSnapshot in snapshot.children) {
                    for (roomSnapshot in typeSnapshot.children) {
                        if (roomID.equals(roomSnapshot.key.toString())) {
                            type = typeSnapshot.key.toString()
                            status =
                                roomSnapshot.child("status").getValue(String::class.java).toString()
                            cleaningStatus =
                                roomSnapshot.child("cleaningStatus").getValue(Boolean::class.java)!!
                        }
                    }
                }
                var room = DataClassRoom(selectedFloor, selectedRoom, status, cleaningStatus)
                txtFloor.text = selectedFloor.toString()
                txtRoom.text = selectedRoom.toString()
                txtTypeOfRoom.text = type
                txtRoomStatus.text = room?.status
                val btnRequest = findViewById<Button>(R.id.btnRequest)
                if (room?.cleaningStatus == true)
                    txtCleaningStatus.text = "Requested"
                else
                    txtCleaningStatus.text = "-"

                if (room?.status.toString() == "Checked In") {
                    txtAvailability.text = "No"

                    //Read ID
                    bookedRoomRef = database.getReference("BookedRooms").child(roomID)
                    bookedRoomRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            btnRequest.isClickable = true
                            if (snapshot.key.toString().equals(roomID)) {
                                bookingID = snapshot.child("bookingID").getValue(String::class.java)
                                    .toString()
                                custID =
                                    snapshot.child("custID").getValue(String::class.java).toString()
                                var txtCust = findViewById<TextView>(R.id.txtCust)
                                var cusRef = database.getReference("Customers")
                                cusRef.child(custID).get().addOnSuccessListener {
                                    var custName =
                                        it.child("firstName").value.toString() + " " + it.child("lastName").value.toString()
                                    txtCust.text = custName
                                    roomServiceList = mutableListOf()
                                    roomServiceList.clear()
                                    roomServiceRef = database.getReference("RoomServices")
                                    roomServiceRef.child(bookingID).get().addOnSuccessListener {
                                        for (ds in it.children) {
                                            var quantity = ds.child("quantity").getValue(Int::class.java)
                                            var roomService = DataClassRoomService(quantity, ds.key.toString())
                                            if (roomService != null) {
                                                roomServiceList.add(roomService)
                                            }
                                        }
                                        var txtRoomService = findViewById<TextView>(R.id.txtRoomService)
                                        txtRoomService.text = ""
                                        if (!roomServiceList.isNullOrEmpty()) {
                                            for (i in roomServiceList) {
                                                roomServiceItemRef =
                                                    database.getReference("RoomServiceItems").child(i.id.toString())
                                                roomServiceItemRef.addValueEventListener(object : ValueEventListener {
                                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                                        var name = dataSnapshot.child("name").getValue(String::class.java)
                                                        roomService = txtRoomService.text.toString()
                                                        roomService += name + " x" + i.quantity + "\n"
                                                        txtRoomService.text = roomService
                                                    }

                                                    override fun onCancelled(error: DatabaseError) {
                                                        Log.w("cancel", "Failed to load room services", error.toException())
                                                    }

                                                })
                                            }
                                        } else {
                                            txtRoomService.text = "-"
                                        }
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w("cancel", "Failed to load data", error.toException())
                        }
                    })

                } else {
                    btnRequest.isClickable = false
                    txtAvailability.text = "Yes"
                    txtCust.text = "-"
                    txtRoomService.text = "-"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("cancel", "Failed to load data", error.toException())
            }
        })

    }




}



