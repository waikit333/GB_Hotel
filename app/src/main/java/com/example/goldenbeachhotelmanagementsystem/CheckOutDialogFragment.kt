package com.example.goldenbeachhotelmanagementsystem

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.goldenbeachhoteldataclasses.DataClassBooking
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.floor

class CheckOutDialogFragment : DialogFragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_check_out_dialog, container)

        if (arguments != null) {
            val args = arguments
            val roomType = args?.get("roomType").toString()
            val roomName = args?.get("roomName").toString()
            val curDate = args?.get("curDate").toString()
            val fromDate = args?.get("fromDate").toString()
            val custID = args?.get("custID").toString()
            val bookingID = args?.get("bookingID").toString()
            var totalBill = args?.get("total").toString()
            val roomServiceTotal = args?.get("roomServiceTotal").toString()
            val roomTotal = args?.get("roomTotal").toString()

            val nameText: TextView = view.findViewById(R.id.cust_name)
            val roomText: TextView = view.findViewById(R.id.room_type)
            val roomIDText: TextView = view.findViewById(R.id.room_id)
            val stayPeriodText: TextView = view.findViewById(R.id.stay_period)
            val totalBillText: TextView = view.findViewById(R.id.total_cost)
            val roomTotalText: TextView = view.findViewById(R.id.room_cost)
            val roomServiceTotalText: TextView = view.findViewById(R.id.room_service_cost)
            val phoneText: TextView = view.findViewById(R.id.phone_num)
            val cancelButton: Button = view.findViewById(R.id.cancel_button)
            val checkoutButton: Button = view.findViewById(R.id.check_out_button)

            var rstotal = 0.0

            //calculate roomServiceTotal
            database.getReference("RoomServices").get().addOnSuccessListener {
                if (it.child(bookingID).exists()){
                    for (item in it.child(bookingID).children){
                        rstotal += item.child("total").value.toString().toDouble()
                    }
                }
                roomServiceTotalText.text = rstotal.toString()
                totalBill = (roomTotal.toDouble() + rstotal).toString()
                totalBillText.text = totalBill
            }

            nameText.text = args?.get("custName").toString()
            roomText.text = roomType
            roomIDText.text = roomName
            stayPeriodText.text = args?.get("periodOfStay").toString() + " days"
            roomTotalText.text = roomTotal
            roomServiceTotalText.text = roomServiceTotal
            phoneText.text = args?.get("custPhone").toString()

            cancelButton.setOnClickListener {
                this.dismiss()
            }

            checkoutButton.setOnClickListener {
                database.getReference("BookedRooms").child(roomName).removeValue()

                database.getReference("Rooms").child(roomType).child(roomName)
                    .child("status").setValue("Available")

                database.getReference("Rooms").child(roomType).child(roomName)
                    .child("cleaningStatus").setValue(false)

                database.getReference("Bookings").child(fromDate).child(roomType).child(custID)
                    .child(bookingID).get().addOnSuccessListener {
                        val booking = it.getValue(DataClassBooking::class.java)
                        booking!!.roomServiceTotal = rstotal
                        booking!!.status = "Checked Out"
                        database.getReference("Bookings").child(fromDate).child(roomType).child(custID)
                            .child(bookingID).setValue(booking)
                        this.dismiss()
                    }
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