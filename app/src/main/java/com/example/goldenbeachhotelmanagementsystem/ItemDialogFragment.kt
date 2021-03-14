package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kotlin.math.log


class ItemDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_item_dialog, container)
        if (arguments != null) {
            val args = arguments
            val nameText: TextView = view.findViewById(R.id.item_name)
            val itemPrice: TextView = view.findViewById(R.id.item_price)
            val totalPrice: TextView = view.findViewById(R.id.total_price)
            nameText.text = args?.get("name").toString()
            itemPrice.text = args?.get("price").toString()
            totalPrice.text = args?.get("price").toString()
        }

        val floorPick: NumberPicker = view.findViewById(R.id.floor_picker)
        val roomPick: NumberPicker = view.findViewById(R.id.room_picker)

        floorPick.minValue = 1
        floorPick.maxValue = 10
        roomPick.minValue = 1
        roomPick.maxValue = 20

        return view
    }
}