package com.example.gendinghotelmanagement

import android.os.Parcel
import android.os.Parcelable

class OrderModel : Parcelable {

    var CheckInDate: String? = null
    var CheckOutDate: String? = null
    var CustomerID: String? = null
    var ExtraServices: String? = null
    var NoOfPerson: Int? = null
    var OrderID: String? = null
    var OrderStatus: String? = null
    var QuantityOfRooms: Int? = null
    var RoomNo: String? = null
    var RoomType: String? = null
    var StaffName: String? = null
    var Total: Double? = null


    constructor() {}
    constructor(
            CheckInDate: String?,
            CheckOutDate: String?,
            CustomerID: String?,
            ExtraServices: String?,
            NoOfPerson: Int?,
            OrderID: String?,
            OrderStatus: String?,
            QuantityOfRooms: Int?,
            RoomNo: String?,
            RoomType: String?,
            StaffName: String?,
            Total: Double



    ) {
        this.CheckInDate= CheckInDate
        this.CheckOutDate= CheckOutDate
        this.CustomerID= CustomerID
        this.ExtraServices= ExtraServices
        this.NoOfPerson= NoOfPerson
        this.OrderID= OrderID
        this.OrderStatus= OrderStatus
        this.QuantityOfRooms= QuantityOfRooms
        this.RoomNo= RoomNo
        this.RoomType= RoomType
        this.StaffName= StaffName
        this.Total= Total

    }

    protected constructor(`in`: Parcel) {
        CheckInDate = `in`.readString()
        CheckOutDate = `in`.readString()
        CustomerID = `in`.readString()
        ExtraServices = `in`.readString()
        NoOfPerson = `in`.readInt()
        OrderID = `in`.readString()
        OrderStatus = `in`.readString()
        QuantityOfRooms = `in`.readInt()
        RoomNo = `in`.readString()
        RoomType = `in`.readString()
        StaffName = `in`.readString()
        Total = `in`.readDouble()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(CheckInDate)
        dest.writeString(CheckOutDate)
        dest.writeString(CustomerID)
        dest.writeString(ExtraServices)
        NoOfPerson?.let { dest.writeInt(it) }
        dest.writeString(OrderID)
        dest.writeString(OrderStatus)
        QuantityOfRooms?.let { dest.writeInt(it) }
        dest.writeString(RoomNo)
        dest.writeString(RoomType)
        dest.writeString(StaffName)
        Total?.let { dest.writeDouble(it) }

    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        val CREATOR: Parcelable.Creator<OrderModel?> = object : Parcelable.Creator<OrderModel?> {
            override fun createFromParcel(`in`: Parcel): OrderModel? {
                return OrderModel(`in`)
            }

            override fun newArray(size: Int): Array<OrderModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}

