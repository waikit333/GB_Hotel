package com.example.goldenbeachhoteldataclasses

import com.google.firebase.database.Exclude

data class DataClassRoom(
        var floor: Int? = 0, var room:Int? = 0, var price:Double? = 0.00, var type:String? ="", var status:String?= "", var cleaningStatus:Boolean? = true){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "floor" to floor,
                "room" to room,
                "price" to price,
                "type" to type,
                "status" to status,
                "cleaningStatus" to cleaningStatus
        )
    }

}