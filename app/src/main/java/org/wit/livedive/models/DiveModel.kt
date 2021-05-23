package org.wit.livedive.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiveModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbId : String? = "",
    var title: String? = "",
    var description: String? = "",
    var image: String? = "",
    var dayVisited: Int= 0,
    var monthVisited: Int= 0,
    var yearVisited: Int= 0,
    var maxDepth: String? = "",
    var mins: String? = "",
    var weight: String? = "",
    var weather: String?= "",
    var ocean: String ?= "",
    var wildlifeImage: String ?= "",
    var wildlife: String ?= "",
    var poiImage : String ?= "",
    var poi : String ?= "",
    var rating: Float = 0f,
    var favourite: Boolean = false,
    var wetsuit: Boolean = false,
    var air: Boolean = false,
    var nitrox: Boolean = false,
    @Embedded var location : Location = Location()
)
    : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),

    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(fbId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeInt(dayVisited)
        parcel.writeInt(monthVisited)
        parcel.writeInt(yearVisited)
        parcel.writeString(maxDepth)
        parcel.writeString(mins)
        parcel.writeString(weight)
        parcel.writeString(weather)
        parcel.writeString(ocean)
        parcel.writeString(wildlifeImage)
        parcel.writeString(wildlife)
        parcel.writeString(poiImage)
        parcel.writeString(poi)
        parcel.writeFloat(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiveModel> {

        override fun createFromParcel(parcel: Parcel): DiveModel {
            return  DiveModel(parcel)
        }

        override fun newArray(size: Int): Array<DiveModel?> {
            return arrayOfNulls(size)
        }
    }
}


data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeFloat(zoom)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}
