package com.example.goldenbeachhoteldataclasses

class DataSource {
    fun loadRoomServiceFoodCategories():List<DataClassRoomServiceCategory>{
        return listOf<DataClassRoomServiceCategory>(
                DataClassRoomServiceCategory("Snacks",""),
                DataClassRoomServiceCategory("À la carte,",""),
                DataClassRoomServiceCategory("Desserts",""),
        )
    }

}