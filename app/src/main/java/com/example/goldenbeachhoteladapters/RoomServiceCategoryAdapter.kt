package com.example.goldenbeachhoteladapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceCategory
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceItem
import com.example.goldenbeachhotelmanagementsystem.R

class RoomServiceCategoryAdapter (
        private val context: Context,
        private val dataset: List<DataClassRoomServiceCategory>
        ) : RecyclerView.Adapter<RoomServiceCategoryAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_room_service_category, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
    }

    override fun getItemCount() = dataset.size

}