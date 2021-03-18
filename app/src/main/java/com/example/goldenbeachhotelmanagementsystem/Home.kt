package com.example.goldenbeachhotelmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.helperclasses.Helper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class Home : AppCompatActivity() {
    private var pressedOnce = false
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var homeNavView: NavigationView
    private var helper = Helper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //Navigation Drawer
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.home_layout)
        homeNavView = findViewById(R.id.homeNavView)
        setSupportActionBar(toolbar)

        homeNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_booking -> {
                    val intent = Intent(this, Booking::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_addNewbooking -> {
                    fabOnClick(findViewById(R.id.fabAddBooking))
                    true
                }
                R.id.nav_checkInOut -> {
                    val intent = Intent(this, CheckInCheckOutMenu::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_checkIn -> {
                    val intent = Intent(this, CheckIns::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_checkOut -> {
                    val intent = Intent(this, CheckIns::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_roomManagement -> {
                    val intent = Intent(this, RoomManagement::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_roomService -> {
                    val intent = Intent(this, RoomService::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        helper.changeNavIconAndTitle(supportActionBar,true,toolbar,drawerLayout,this,getString(R.string.hotel_name))

        var type: String? = ""
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            type = bundle.getString("type")
        }
        replaceFragment(home_menu_staff())
        /*if (type!! == "Manager") {
            replaceFragment(home_menu_manager())
        } else if (type == "Admin") {
            replaceFragment(home_menu_admin())
        } else {
            replaceFragment(home_menu_staff())
        }*/
    }



    fun fabOnClick(v: View) {
        val intent = Intent(this, AddNewBooking::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        var backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT)
        val myFragment: Fragment? =
            supportFragmentManager.findFragmentByTag("MY_FRAGMENT") as Fragment?
        if (myFragment != null && myFragment.isVisible()) {
            super.onBackPressed()
            return
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
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

    private fun replaceFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        fragment.arguments = intent.extras
        transaction.replace(R.id.homeView, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item
        return super.onOptionsItemSelected(item)
    }
}