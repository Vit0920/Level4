package com.vkunitsyn.level4.model

import android.os.Parcel
import android.os.Parcelable


class Contact(
    var id: String? = "",
    var picture: String? = "",
    var name: String? = "",
    var career: String? = "",
    var phone: String? = "",
    var address: String? = "",
    var birthday: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(picture)
        parcel.writeString(name)
        parcel.writeString(career)
        parcel.writeString(phone)
        parcel.writeString(address)
        parcel.writeString(birthday)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}
