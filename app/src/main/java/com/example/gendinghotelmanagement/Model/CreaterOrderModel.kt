package com.example.gendinghotelmanagement.Model

import android.os.Parcel
import android.os.Parcelable

class CreateOrderModel : Parcelable {


    var CustomerName: String? = null
    var CustomerID: String? = null
    var Phone: String? = null
    var Address: String? = null
    var NoOfPerson: Int? = null
    var ExtraServices: String? = null
    var RoomType: String? = null
    var CheckInDate: String? = null
    var CheckOutDate: String? = null



    constructor() {}
    constructor(
            CustomerName: String?,
            CustomerID: String?,
            Phone: String?,
            Address: String?,
            NoOfPerson: Int?,
            ExtraServices: String?,
            RoomType: String?,
            CheckInDate: String?,
            CheckOutDate: String?





    ) {

        this.CustomerName= CustomerName
        this.CustomerID= CustomerID
        this.Phone= Phone
        this.Address= Address
        this.NoOfPerson= NoOfPerson
        this.ExtraServices= ExtraServices
        this.RoomType= RoomType
        this.CheckInDate=  CheckInDate
        this.CheckOutDate= CheckOutDate


    }

    protected constructor(`in`: Parcel) {

        CustomerName = `in`.readString()
        CustomerID = `in`.readString()
        Phone = `in`.readString()
        Address = `in`.readString()
        NoOfPerson = `in`.readInt()
        ExtraServices = `in`.readString()
        RoomType = `in`.readString()
        CheckInDate = `in`.readString()
        CheckOutDate = `in`.readString()


    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeString(CustomerName)
        dest.writeString(CustomerID)
        dest.writeString(Phone)
        dest.writeString(Address)
        NoOfPerson?.let { dest.writeInt(it) }
        dest.writeString(ExtraServices)
        dest.writeString(RoomType)
        dest.writeString(CheckInDate)
        dest.writeString(CheckOutDate)



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

