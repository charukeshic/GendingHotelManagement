package com.example.gendinghotelmanagement

import android.os.Parcel
import android.os.Parcelable
import org.intellij.lang.annotations.JdkConstants

class ScheduleModel : Parcelable {

    var StaffEmail: String? = null
    var Monday: String? = null
    var Tuesday: String? = null
    var Wednesday: String? = null
    var Thursday: String? = null
    var Friday: String? = null
    var Saturday: String? = null
    var Sunday: String? = null



    constructor() {}
    constructor(
            StaffEmail: String?,
            Monday: String?,
            Tuesday: String?,
            Wednesday: String?,
            Thursday: String?,
            Friday: String?,
            Saturday: String?,
            Sunday: String?




    ) {
        this.StaffEmail= StaffEmail
        this.Monday= Monday
        this.Tuesday= Tuesday
        this.Wednesday= Wednesday
        this.Thursday= Thursday
        this.Friday= Friday
        this.Saturday= Saturday
        this.Sunday= Sunday

    }

    protected constructor(`in`: Parcel) {
        StaffEmail = `in`.readString()
        Monday = `in`.readString()
        Tuesday = `in`.readString()
        Wednesday = `in`.readString()
        Thursday = `in`.readString()
        Friday = `in`.readString()
        Saturday = `in`.readString()
        Sunday = `in`.readString()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(StaffEmail)
        dest.writeString(Monday)
        dest.writeString(Tuesday)
        dest.writeString(Wednesday)
        dest.writeString(Thursday)
        dest.writeString(Friday)
        dest.writeString(Saturday)
        dest.writeString(Sunday)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        val CREATOR: Parcelable.Creator<ScheduleModel?> = object : Parcelable.Creator<ScheduleModel?> {
            override fun createFromParcel(`in`: Parcel): ScheduleModel? {
                return ScheduleModel(`in`)
            }

            override fun newArray(size: Int): Array<ScheduleModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}

