package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase


class ItemDialogFragment : DialogFragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

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
            val quantityText: EditText = view.findViewById(R.id.quantityPicker)
            val floorSpinner: Spinner = view.findViewById(R.id.floorSpinner)
            val roomSpinner: Spinner = view.findViewById(R.id.roomSpinner)
            nameText.text = args?.get("name").toString()
            itemPrice.text = args?.get("price").toString()
            totalPrice.text = args?.get("price").toString()

            //check available floors
            database.getReference("BookedRooms").get().addOnSuccessListener {
                val floorList = ArrayList<Int>()
                for (room in it.children) {
                    val list = room.key.toString().substring(1).split('R')
                    if (!floorList.contains(list[0].toInt())) {
                        floorList.add(list[0].toInt())
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
            floorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedFloor = floorSpinner.selectedItem.toString()
                    val roomList = ArrayList<Int>()
                    database.getReference("BookedRooms").get().addOnSuccessListener {
                        for (room in it.children) {
                            val list = room.key.toString().substring(1).split('R')
                            if (list[0] == selectedFloor) {
                                roomList.add(list[1].toInt())
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

            val cancelButton: Button = view.findViewById(R.id.cancelOrderButton)
            val orderButton: Button = view.findViewById(R.id.confirmOrderButton)

            var qty: Int = 1
            quantityText.addTextChangedListener {
                try {
                    qty = quantityText.text.toString().toInt()
                } catch (e: NumberFormatException){
                    quantityText.setText("0")
                } finally {
                    val total = qty * itemPrice.text.toString().toDouble()
                    totalPrice.text = String.format("%.2f", total)
                }
            }
            
            cancelButton.setOnClickListener {
                this.dismiss()
            }
            
            orderButton.setOnClickListener {
                //check if quantity 0
                if (totalPrice.text.toString().toDouble() <= 0){
                    return@setOnClickListener
                }

                val floorString =
                    "F" + floorSpinner.selectedItem.toString() + "R" + roomSpinner.selectedItem.toString()
                database.reference.get().addOnSuccessListener {
                    //check if selected room is booked
                    if (it.child("BookedRooms").child(floorString).exists()) {
                        //get booking id
                        val bookingID: String = it.child("BookedRooms").child(floorString)
                            .child("bookingID").value.toString()
                        //add entry
                        val itemId: String = args?.get("id").toString()
                        val quantity = quantityText.text.toString().toInt()
                        database.reference.child("RoomServices").child(bookingID).child(itemId)
                            .child("quantity").setValue(quantity)
                        database.reference.child("RoomServices").child(bookingID).child(itemId)
                            .child("total").setValue(totalPrice.text.toString().toDouble())
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