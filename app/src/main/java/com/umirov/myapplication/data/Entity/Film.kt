package com.umirov.myapplication.data.Entity


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize


@VersionedParcelize
@Entity(tableName = "cached_films", indices = [androidx.room.Index(value = ["title"], unique = true)])
data class Film(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val poster: String,
    @ColumnInfo(name = "overview") val description: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "vote_average") val rating: Double = 0.0,
    var isInFavorites: Boolean = false
) : Parcelable {

    override fun describeContents(): Int {
        return 0
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(poster)
        parcel.writeString(description)
    }


    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film {
            return Film(
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }

        override fun newArray(size: Int): Array<Film?> {
            return arrayOfNulls(size)
        }
    }


}

