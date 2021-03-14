package com.example.goldenbeachhoteladapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceItem
import com.example.goldenbeachhotelmanagementsystem.ItemDialogFragment
import com.example.goldenbeachhotelmanagementsystem.R

class RoomServiceItemAdapter(
        private val context: Context,
        private val dataset: List<DataClassRoomServiceItem>,
        private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RoomServiceItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.item_name)
        val price: TextView = view.findViewById(R.id.item_price)
        val itemLayout: LinearLayout = view.findViewById(R.id.item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_room_service_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.name.text = item.name
        holder.price.text = "RM " + item.price.toString()
        holder.itemLayout.setOnClickListener{
            val dialog = ItemDialogFragment()
            val args = Bundle()
            args.putString("name", item.name)
            args.putString("price", item.price.toString())
            dialog.arguments = args
            dialog.show(fragmentManager, "Name")
        }
    }

    override fun getItemCount() = dataset.size

}