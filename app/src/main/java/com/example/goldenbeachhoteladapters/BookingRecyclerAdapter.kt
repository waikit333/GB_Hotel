package com.example.goldenbeachhoteladapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteldataclasses.DataClassBookingItem
import com.example.goldenbeachhotelmanagementsystem.R

class BookingRecyclerAdapter (internal val context: Context,var bookingDataList: List<DataClassBookingItem>): RecyclerView.Adapter<BookingRecyclerAdapter.BookingViewHolder> (){

    inner class BookingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var bookindID: TextView = itemView.findViewById(R.id.txtBookingFromTo)
        var bookingDate: TextView = itemView.findViewById(R.id.txtBookingDate)
        var custName: TextView = itemView.findViewById(R.id.txtCustName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingRecyclerAdapter.BookingViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_booking,parent,false)
        return BookingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookingRecyclerAdapter.BookingViewHolder, position: Int) {
        holder.bookindID.text = bookingDataList[position].fromTo
        holder.bookingDate.text = bookingDataList[position].bookingDate
        holder.custName.text = bookingDataList[position].custName

    }

    override fun getItemCount(): Int {
        return bookingDataList.size
    }


}