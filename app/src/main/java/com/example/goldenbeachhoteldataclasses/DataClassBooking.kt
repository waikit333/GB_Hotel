package com.example.goldenbeachhoteldataclasses
import com.google.firebase.database.Exclude

data class DataClassBooking(
    var from:String? = "0", var hotelMeal:Boolean? = true, var numOfGuest: Int? = 0, var roomID:String? = "", var to:String?= "", var total:Double? = 0.00){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "from" to from,
            "hotelMeal" to hotelMeal,
            "numOfGuest" to numOfGuest,
            "roomID" to roomID,
            "to" to to,
            "total" to total
        )
    }
}