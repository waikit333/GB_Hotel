package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class home_menu_staff : Fragment(R.layout.fragment_home_menu_staff) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var txtName:TextView
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_menu_staff, container,
            false)
        val btnBooking = view?.findViewById<ImageButton>(R.id.btnBooking)
        txtName = view?.findViewById(R.id.txtName)

        readName()

        if (btnBooking != null) {
            btnBooking.setOnClickListener(){
                val intent = Intent(activity,Booking::class.java)
                startActivity(intent)
            }
        }
        val btnCheckInOut = view?.findViewById<ImageButton>(R.id.btnCheckInOut)
        if (btnCheckInOut != null) {
            btnCheckInOut.setOnClickListener(){
                val intent = Intent(activity,CheckInCheckOutMenu::class.java)
                startActivity(intent)
            }
        }
        val btnRoomManagement = view?.findViewById<ImageButton>(R.id.btnRoomManagement)
        if (btnRoomManagement != null) {
            btnRoomManagement.setOnClickListener(){
                val intent = Intent(activity,RoomManagement::class.java)
                startActivity(intent)
            }
        }
        val btnRoomService = view?.findViewById<ImageButton>(R.id.btnRoomService)
        if (btnRoomService != null) {
            btnRoomService.setOnClickListener(){
                val intent = Intent(activity,RoomService::class.java)
                startActivity(intent)
            }
        }

        return view
    }

    private fun readName(){
        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        database.getReference("Users").child(auth.uid.toString()).get().addOnSuccessListener {
            txtName.text = it.child("name").value.toString()
        }
    }
}