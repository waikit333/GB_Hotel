package com.example.goldenbeachhotelmanagementsystem

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteldataclasses.DataClassBooking
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceItem

class BookingRecyclerAdapter (private val context: Context,
                              private val dataset: List<DataClassBooking>,
                              private val fragmentManager: FragmentManager){//: RecyclerView.Adapter<RecyclerView.ViewHolder> (){
    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.PhotoHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.PhotoHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 0
    }

    class BookingViewHolder constructor(
            itemView: View
    ): RecyclerView.ViewHolder(itemView){
        /*val booking_id: TextView = itemView.txtID
        val booking_date: TextView = itemView.txtBookDate
        val cust_name: TextView = itemView.txtName*/
    }*/
}