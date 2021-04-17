package com.example.goldenbeachhoteldataclasses
import com.google.firebase.database.Exclude

data class DataClassBooking(
    var hotelMeal:Boolean? = true, var numOfGuest: Int? = 0,  var to:String?= "",
    var roomTotal:Double? = 0.00,var otherGuest:String? = "",var bookingDate:String? ="", var  total:Double? = 0.00, var status:String? = "Booked",
    var room:String? = "-",var roomServiceTotal:Double? = 0.00){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "hotelMeal" to hotelMeal,
            "numOfGuest" to numOfGuest,
            "to" to to,
            "otherGuest" to otherGuest,
            "total" to total,
            "roomTotal" to roomTotal,
            "roomServiceTotal" to roomServiceTotal,
            "bookingDate" to bookingDate,
            "room" to room
        )
    }
}