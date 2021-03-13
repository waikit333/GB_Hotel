package com.example.goldenbeachhoteldataclasses

import com.google.firebase.database.Exclude

data class DataClassRoom(
        var floor: Int? = 0, var room:Int? = 0, var status:String?= "", var cleaningStatus:Boolean? = true){

}