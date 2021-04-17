package com.example.goldenbeachhotelmanagementsystem

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase


class CheckInDialogFragment : DialogFragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_check_in_dialog, container)
        if (arguments != null) {
            val args = arguments
            val roomType = args?.get("roomType").toString()
            val curDate = args?.get("curDate").toString()
            val custID = args?.get("custID").toString()
            val bookingID = args?.get("bookingID").toString()

            val nameText: TextView = view.findViewById(R.id.cust_name)
            val roomText: TextView = view.findViewById(R.id.room_type)
            val stayPeriodText: TextView = view.findViewById(R.id.stay_period)
            val phoneText: TextView = view.findViewById(R.id.phone_num)
            val cancelButton: Button = view.findViewById(R.id.cancel_button)
            val checkinButton: Button = view.findViewById(R.id.check_in_button)
            val floorSpinner: Spinner = view.findViewById(R.id.floorSpinner)
            val roomSpinner: Spinner = view.findViewById(R.id.roomSpinner)

            nameText.text = args?.get("custName").toString()
            roomText.text = roomType
            stayPeriodText.text = args?.get("periodOfStay").toString() + " days"
            phoneText.text = args?.get("custPhone").toString()

            //check available floors
            database.getReference("Rooms").child(roomType).get().addOnSuccessListener {
                val floorList = ArrayList<Int>()
                for (child in it.children){
                    if (child.child("status").value == "Available") {
                        val list = child.key.toString().substring(1).split('R')
                        if (!floorList.contains(list[0].toInt())){
                            floorList.add(list[0].toInt())
                        }
                    }
                }
                floorList.sort()
                floorSpinner.adapter = ArrayAdapter(
                    this.requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    floorList
                )
            }

            //check available room on floor select
            floorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedFloor = floorSpinner.selectedItem.toString()
                    val roomList = ArrayList<Int>()
                    database.getReference("Rooms").child(roomType).get().addOnSuccessListener {
                        for (child in it.children){
                            if (child.child("status").value == "Available") {
                                val list = child.key.toString().substring(1).split('R')
                                if (list[0] == selectedFloor){
                                    roomList.add(list[1].toInt())
                                }
                            }
                        }
                        roomList.sort()
                        roomSpinner.adapter = ArrayAdapter(
                            view!!.context,
                            android.R.layout.simple_spinner_dropdown_item,
                            roomList
                        )
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            cancelButton.setOnClickListener {
                this.dismiss()
            }

            checkinButton.setOnClickListener {
                val floor = floorSpinner.selectedItem.toString()
                val room = roomSpinner.selectedItem.toString()
                val roomString = "F" + floor + "R" + room
                //set status to Checked In
                database.getReference("Bookings").child(curDate).child(roomType).child(custID)
                    .child(bookingID).child("status").setValue("Checked In")
                //set room to selected Room
                database.getReference("Bookings").child(curDate).child(roomType).child(custID)
                    .child(bookingID).child("room").setValue(roomString)
                //set FXRX to Checked In
                database.getReference("Rooms").child(roomType).child(roomString)
                    .child("status").setValue("Checked In")

                //add to Booked Rooms
                database.getReference("BookedRooms").child(roomString)
                    .child("bookingID").setValue(bookingID)
                database.getReference("BookedRooms").child(roomString)
                    .child("custID").setValue(custID)

                this.dismiss()
                Toast.makeText(this.context, "Check in successful!", Toast.LENGTH_SHORT)
            }
        }
        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (activity is DialogInterface.OnDismissListener) {
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }

}