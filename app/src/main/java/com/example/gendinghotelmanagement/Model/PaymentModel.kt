package com.example.gendinghotelmanagement.Model

import android.os.Parcel
import android.os.Parcelable

class PaymentModel : Parcelable {

//   var OrderID: String? = null
//    var OrderStatus: String? = null
    var OrderID: String? = null
    var PaymentMethod: String? = null
    var Total: Int? = null


    constructor() {}
    constructor(
//           OrderID: String?,
//            OrderStatus: String?,
            OrderID: String?,
            PaymentMethod: String?,
            Total: Int?


    ) {
 //      this.OrderID= OrderID
//        this.OrderStatus= OrderStatus
        this.OrderID= OrderID
        this.PaymentMethod= PaymentMethod
        this.Total= Total

    }

    protected constructor(`in`: Parcel) {

 //       OrderID = `in`.readString()
//        OrderStatus = `in`.readString()
        OrderID = `in`.readString()
        PaymentMethod = `in`.readString()
        Total = `in`.readInt()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

 //       dest.writeString(OrderID)
//        dest.writeString(OrderStatus)
        dest.writeString(OrderID)
        dest.writeString(PaymentMethod)
        Total?.let { dest.writeInt(it) }

    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        val CREATOR: Parcelable.Creator<PaymentModel?> = object : Parcelable.Creator<PaymentModel?> {
            override fun createFromParcel(`in`: Parcel): PaymentModel? {
                return PaymentModel(`in`)
            }

            override fun newArray(size: Int): Array<PaymentModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}