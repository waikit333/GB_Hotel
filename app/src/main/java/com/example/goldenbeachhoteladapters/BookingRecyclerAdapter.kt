package com.example.goldenbeachhoteladapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteldataclasses.DataClassBookingItem
import com.example.goldenbeachhotelmanagementsystem.BookingDetails
import com.example.goldenbeachhotelmanagementsystem.R

class BookingRecyclerAdapter (internal val context: Context,var bookingDataList: List<DataClassBookingItem>): RecyclerView.Adapter<BookingRecyclerAdapter.BookingViewHolder> (){

    inner class BookingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var bookingFromTo: TextView = itemView.findViewById(R.id.txtBookingFromTo)
        var bookingDate: TextView = itemView.findViewById(R.id.txtBookingDate)
        var custName: TextView = itemView.findViewById(R.id.txtCustName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingRecyclerAdapter.BookingViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_booking,parent,false)
        return BookingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookingRecyclerAdapter.BookingViewHolder, position: Int) {
        holder.bookingFromTo.text = bookingDataList[position].fromTo
        holder.bookingDate.text = bookingDataList[position].bookingDate
        holder.custName.text = bookingDataList[position].custName
        holder.itemView.setOnClickListener{
            val bookingItem = bookingDataList.get(position)
            val bookingFrom = bookingItem.fromTo?.substring(0,8)
            val custID = bookingItem.custID
            val bookingID = bookingItem.bookingID
            val type = bookingItem.type
            val intent = Intent(context,BookingDetails::class.java)
            intent.putExtra("from", bookingFrom)
            intent.putExtra("custID",custID)
            intent.putExtra("bookingID",bookingID)
            intent.putExtra("type",type)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return bookingDataList.size
    }


}