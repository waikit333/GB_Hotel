package com.example.goldenbeachhoteldataclasses

import com.google.firebase.database.Exclude

data class DataClassRoomService(var quantity:Int?=0, var id:String? = ""){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "id" to id,
                "quantity" to quantity
        )
    }
}
