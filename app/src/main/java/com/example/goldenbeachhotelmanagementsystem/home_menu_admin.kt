package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home_menu_admin.newInstance] factory method to
 * create an instance of this fragment.
 */
class home_menu_admin : Fragment(R.layout.fragment_home_menu_admin) {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_menu_admin, container,
            false)

        var name :String? = ""
        val bundle :Bundle ?=arguments
        if (bundle!=null){
            name = bundle.getString("name")
        }
        var txtName = view?.findViewById<TextView>(R.id.txtName)
        if (txtName != null) {
            txtName.text = name
        }

        val btnStaff = view?.findViewById<ImageButton>(R.id.btnStaff)
        if (btnStaff != null) {
            btnStaff.setOnClickListener(){
                val intent = Intent(activity,Booking::class.java)
                startActivity(intent)
            }
        }
        val btnAddNewStaff = view?.findViewById<ImageButton>(R.id.btnAddNewStaff)
        if (btnAddNewStaff != null) {
            btnAddNewStaff.setOnClickListener(){
                val intent = Intent(activity,CheckInCheckOutMenu::class.java)
                startActivity(intent)
            }
        }
        val btnEditStaff = view?.findViewById<ImageButton>(R.id.btnEditStaff)
        if (btnEditStaff != null) {
            btnEditStaff.setOnClickListener(){
                val intent = Intent(activity,RoomManagement::class.java)
                startActivity(intent)
            }
        }

        return view
    }

}