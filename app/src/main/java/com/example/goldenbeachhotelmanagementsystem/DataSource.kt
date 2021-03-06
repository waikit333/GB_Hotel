package com.example.goldenbeachhotelmanagementsystem

class DataSource {

    fun loadRoomServiceCategories() : List<RoomServiceCategory> {
        return listOf<RoomServiceCategory>(
            RoomServiceCategory(R.string.booking_id)
        )
    }

}