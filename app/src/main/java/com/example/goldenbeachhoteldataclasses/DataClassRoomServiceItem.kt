package com.example.goldenbeachhoteldataclasses

data class DataClassRoomServiceItem(val name:String? = "", val description:String? = "", val price: Double? = 0.00, var id:String? = ""){
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "name" to name
        )
    }
}
