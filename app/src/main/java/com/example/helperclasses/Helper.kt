package com.example.helperclasses

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.goldenbeachhotelmanagementsystem.R

class Helper {
    fun changeNavIconAndTitle(actionbar: androidx.appcompat.app.ActionBar?,enable:Boolean,toolbar:Toolbar,drawerLayout:DrawerLayout,context:AppCompatActivity,title:String){
        var toggle: ActionBarDrawerToggle? = null
        if(enable){
            toggle = ActionBarDrawerToggle(
                context,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)

            toggle.syncState()
        }
        else{
            actionbar?.setHomeButtonEnabled(true)
            actionbar?.setDisplayShowHomeEnabled(true)
            actionbar?.setDisplayHomeAsUpEnabled(true)
        }
        actionbar?.title = title
    }
}