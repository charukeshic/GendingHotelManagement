package com.example.gendinghotelmanagement

import android.os.Parcel
import android.os.Parcelable

class RoomModel : Parcelable {

    var RoomNo: Int? = null
    var RoomStatus: String? = null
    var RoomType: String? = null



    constructor() {}
    constructor(
            RoomNo: Int?,
            RoomStatus: String?,
            RoomType: String?



    ) {
        this.RoomNo= RoomNo
        this.RoomStatus= RoomStatus
        this.RoomType= RoomType

    }

    protected constructor(`in`: Parcel) {

        RoomNo = `in`.readInt()
        RoomStatus = `in`.readString()
        RoomType = `in`.readString()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

        RoomNo?.let { dest.writeInt(it)}
        dest.writeString(RoomStatus)
        dest.writeString(RoomType)

    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        val CREATOR: Parcelable.Creator<RoomModel?> = object : Parcelable.Creator<RoomModel?> {
            override fun createFromParcel(`in`: Parcel): RoomModel? {
                return RoomModel(`in`)
            }

            override fun newArray(size: Int): Array<RoomModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}