package com.example.goldenbeachhotelmanagementsystem

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.goldenbeachhoteldataclasses.DataClassRoom
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Home : AppCompatActivity(){
    private var pressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        var type :String? = ""
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            type = bundle.getString("type")
        }

        if(type!! == "Manager"){
            replaceFragment(home_menu_manager())
        }
        else if(type == "Admin"){
            replaceFragment(home_menu_admin())
        }
        else{
            replaceFragment(home_menu_staff())
        }

    }

    fun fabOnClick(v: View){
        val downtool = findViewById<BottomAppBar>(R.id.bottomAppBar)
        downtool.visibility = View.INVISIBLE
        val fabAddNewBooking = findViewById<FloatingActionButton>(R.id.fabAddBooking)
        fabAddNewBooking.visibility = View.INVISIBLE
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        var fragment = AddNewBooking()
        transaction.replace(R.id.homeView, fragment, "MY_FRAGMENT")
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed()
    {
        var backToast = Toast.makeText(this,"Press back again to exit",Toast.LENGTH_SHORT)

        val myFragment: Fragment? =
            supportFragmentManager.findFragmentByTag("MY_FRAGMENT") as Fragment?
        if (myFragment != null && myFragment.isVisible()) {
            val fabAddNewBooking = findViewById<FloatingActionButton>(R.id.fabAddBooking)
            fabAddNewBooking.visibility = View.VISIBLE
            val downtool = findViewById<BottomAppBar>(R.id.bottomAppBar)
            downtool.visibility = View.VISIBLE
            super.onBackPressed()
            return
        }
        else{
            if (pressedOnce) {
                backToast.cancel()
                super.onBackPressed()
                return
            }
            pressedOnce = true
            backToast.show()
            Handler().postDelayed(Runnable { pressedOnce = false }, 2000)
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        fragment.arguments = intent.extras
        transaction.replace(R.id.homeView, fragment)
        transaction.commit()
    }
}