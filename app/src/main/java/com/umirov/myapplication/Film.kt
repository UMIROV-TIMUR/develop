package com.umirov.myapplication


import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize


@VersionedParcelize
class Film(
    val title: String,
    val poster: Int,
    val description: String,
    var rating: Float = 0f,
    var isInFavorites: Boolean = false
) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(poster)
        parcel.writeString(description)
    }


    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(
                parcel.readString() ?: "",
                parcel.readInt(),
                parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }


}

