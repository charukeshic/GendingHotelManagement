package com.example.gendinghotelmanagement.Model

import android.os.Parcel
import android.os.Parcelable

class CreateOrderModel : Parcelable {

    var OrderID: String? = null
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



    constructor() {}
    constructor(
            OrderID: String?,
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
            CheckOutYear: String?





    ) {

        this.OrderID= OrderID
        this.CustomerName= CustomerName
        this.CustomerID= CustomerID
        this.Phone= Phone
        this.Address= Address
        this.NoOfPerson= NoOfPerson
        this.NoOfRoom= NoOfRoom
        this.ExtraServices= ExtraServices
        this.RoomType= RoomType
        this.CheckInDay=  CheckInDay
        this.CheckInMonth=  CheckInMonth
        this.CheckInYear=  CheckInYear
        this.CheckOutDay= CheckOutDay
        this.CheckOutMonth= CheckOutMonth
        this.CheckOutYear= CheckOutYear


    }

    protected constructor(`in`: Parcel) {

        OrderID = `in`.readString()
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


    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeString(OrderID)
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



    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        val CREATOR: Parcelable.Creator<CreateOrderModel?> = object : Parcelable.Creator<CreateOrderModel?> {
            override fun createFromParcel(`in`: Parcel): CreateOrderModel? {
                return CreateOrderModel(`in`)
            }

            override fun newArray(size: Int): Array<CreateOrderModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}

