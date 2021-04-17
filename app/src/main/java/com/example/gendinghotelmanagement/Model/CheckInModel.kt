package com.example.gendinghotelmanagement.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.FirebaseDatabase

class CheckInModel : Parcelable {


    var CustomerName: String? = null
    var CustomerID: String? = null
    var Phone: String? = null
    var Address: String? = null
    var NoOfPerson: Int? = null
    var NoOfRoom: Int? = null
    var ExtraServices: String? = null
    var RoomType: String? = null
    var CheckInDay: String? = null
    var CheckInMonth: String? = null
    var CheckInYear: String? = null
    var CheckOutDay: String? = null
    var CheckOutMonth: String? = null
    var CheckOutYear: String? = null
    var StaffName: String? = null
    var RoomKey: String? = null
    var RoomStatus: String? = null



    constructor() {}
    constructor(
            CustomerName: String?,
            CustomerID: String?,
            Phone: String?,
            Address: String?,
            NoOfPerson: Int?,
            NoOfRoom: Int?,
            ExtraServices: String?,
            RoomType: String?,
            CheckInDay: String?,
            CheckInMonth: String?,
            CheckInYear: String?,
            CheckOutDay: String?,
            CheckOutMonth: String?,
            CheckOutYear: String?,
            StaffName: String?,
            RoomKey: String?,
            RoomStatus: String?
    ) {

        this.CustomerName= CustomerName
        this.CustomerID= CustomerID
        this.Phone= Phone
        this.Address= Address
        this.NoOfPerson= NoOfPerson
        this.NoOfRoom= NoOfPerson
        this.ExtraServices= ExtraServices
        this.RoomType= RoomType
        this.CheckInDay=  CheckInDay
        this.CheckInMonth=  CheckInMonth
        this.CheckInYear=  CheckInYear
        this.CheckOutDay= CheckOutDay
        this.CheckOutMonth= CheckOutMonth
        this.CheckOutYear= CheckOutYear
        this.StaffName= StaffName
        this.RoomKey= RoomKey
        this.RoomStatus= RoomKey


    }

    protected constructor(`in`: Parcel) {

        CustomerName = `in`.readString()
        CustomerID = `in`.readString()
        Phone = `in`.readString()
        Address = `in`.readString()
        NoOfPerson = `in`.readInt()
        NoOfRoom = `in`.readInt()
        ExtraServices = `in`.readString()
        RoomType = `in`.readString()
        CheckInDay = `in`.readString()
        CheckInMonth = `in`.readString()
        CheckInYear = `in`.readString()
        CheckOutDay = `in`.readString()
        CheckOutMonth = `in`.readString()
        CheckOutYear = `in`.readString()
        StaffName = `in`.readString()
        RoomKey = `in`.readString()
        RoomStatus = `in`.readString()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeString(CustomerName)
        dest.writeString(CustomerID)
        dest.writeString(Phone)
        dest.writeString(Address)
        NoOfPerson?.let { dest.writeInt(it) }
        NoOfRoom?.let { dest.writeInt(it) }
        dest.writeString(ExtraServices)
        dest.writeString(RoomType)
        dest.writeString(CheckInDay)
        dest.writeString(CheckInMonth)
        dest.writeString(CheckInYear)
        dest.writeString(CheckOutDay)
        dest.writeString(CheckOutMonth)
        dest.writeString(CheckOutYear)
        dest.writeString(StaffName)
        dest.writeString(RoomKey)
        dest.writeString(RoomStatus)


    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        val CREATOR: Parcelable.Creator<CheckInModel?> = object : Parcelable.Creator<CheckInModel?> {
            override fun createFromParcel(`in`: Parcel): CheckInModel? {
                return CheckInModel(`in`)
            }

            override fun newArray(size: Int): Array<CheckInModel?> {
                return arrayOfNulls(size)
            }
        }
    }





}
