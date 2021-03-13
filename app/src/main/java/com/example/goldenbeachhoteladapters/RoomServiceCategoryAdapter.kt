package com.example.goldenbeachhoteladapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenbeachhoteldataclasses.DataClassRoomServiceCategory
import com.example.goldenbeachhotelmanagementsystem.R
import com.example.goldenbeachhotelmanagementsystem.RoomService
import com.example.goldenbeachhotelmanagementsystem.RoomServiceItems

class RoomServiceCategoryAdapter (
        private val context: Context,
        private val dataset: List<DataClassRoomServiceCategory>
        ) : RecyclerView.Adapter<RoomServiceCategoryAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.category_name)
        val itemLayout: LinearLayout = view.findViewById(R.id.item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_room_service_category, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.name
        holder.itemLayout.setOnClickListener{
            val intent = Intent(context, RoomServiceItems::class.java)
            intent.putExtra("categoryName", item.name.toString())
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = dataset.size

}