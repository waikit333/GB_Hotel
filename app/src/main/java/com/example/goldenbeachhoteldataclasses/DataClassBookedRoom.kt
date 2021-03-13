package com.example.goldenbeachhoteldataclasses

import com.google.firebase.database.Exclude

data class DataClassBookedRoom(var bookingID:String? = "",var custID:String? = "", var checkInDate:String? = "",var checkOutDate:String? = "", var checkedIn:Boolean?=true){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "bookingID" to bookingID,
            "custID" to custID
        )
    }
}
