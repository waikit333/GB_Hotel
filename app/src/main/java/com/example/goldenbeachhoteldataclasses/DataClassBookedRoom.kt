package com.example.goldenbeachhoteldataclasses

import com.google.firebase.database.Exclude

data class DataClassBookedRoom(var bookingID:String? = "",var custID:String? = ""){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "bookingID" to bookingID,
            "custID" to custID
        )
    }
}
