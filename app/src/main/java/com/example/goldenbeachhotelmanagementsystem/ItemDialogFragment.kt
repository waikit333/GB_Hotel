package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase


class ItemDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_item_dialog, container)
        if (arguments != null) {
            val args = arguments
            val nameText: TextView = view.findViewById(R.id.item_name)
            val itemPrice: TextView = view.findViewById(R.id.item_price)
            val totalPrice: TextView = view.findViewById(R.id.total_price)
            val quantityText: TextView = view.findViewById(R.id.quantityPicker)
            nameText.text = args?.get("name").toString()
            itemPrice.text = args?.get("price").toString()
            totalPrice.text = args?.get("price").toString()
            val floorPick: NumberPicker = view.findViewById(R.id.floor_picker)
            val roomPick: NumberPicker = view.findViewById(R.id.room_picker)

            floorPick.minValue = 1
            floorPick.maxValue = 10
            roomPick.minValue = 1
            roomPick.maxValue = 20

            val cancelButton: Button = view.findViewById(R.id.cancelOrderButton)
            val orderButton: Button = view.findViewById(R.id.confirmOrderButton)

            cancelButton.setOnClickListener {
                this.dismiss()
            }
            orderButton.setOnClickListener {
                val floorString = "F" + floorPick.value.toString() + "R" + roomPick.value.toString()
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                database.reference.get().addOnSuccessListener {
                    //check if selected room is booked
                    if (it.child("BookedRooms").child(floorString).exists()) {
                        //get booking id
                        val bookingID:String = it.child("BookedRooms").child(floorString).child("bookingID").value.toString()

                        //WIP : check if entry already exists

                        //add entry
                        val itemId:String = args?.get("id").toString()
                        val quantity:String = quantityText.text.toString()
                        database.reference.child("RoomServices").child(bookingID).child(itemId)
                            .child("quantity").setValue(quantity)
                        this.dismiss()
                    } else {
                        Toast.makeText(this.context, "This room is not booked", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        return view
    }
}