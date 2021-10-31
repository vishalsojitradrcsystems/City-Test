package com.city.test.domain.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Cities")
data class City(
    @Embedded
    @SerializedName("coord")
    var coord: Coord? = null,
    @SerializedName("country")
    var country: String? = null,
    @PrimaryKey
    @SerializedName("_id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null
){
    fun getDisplayName(): String {
        return "$name, $country"
    }
}

@Entity
data class Coord(
    @PrimaryKey(autoGenerate = true)
    val coordId: Long,
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lon")
    var lon: Double? = null
)
