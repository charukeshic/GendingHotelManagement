package com.example.gendinghotelmanagement.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.DataSnapshot

class PaymentModel : Parcelable {

//   var OrderID: String? = null
//    var OrderStatus: String? = null
    var PayID: String? = null
    var PaymentMethod: String? = null


    constructor() {}
    constructor(
//           OrderID: String?,
//            OrderStatus: String?,
            PayID: String?,
            PaymentMethod: String?


    ) {
 //      this.OrderID= OrderID
//        this.OrderStatus= OrderStatus
        this.PayID= PayID
        this.PaymentMethod= PaymentMethod

    }

    protected constructor(`in`: Parcel) {

 //       OrderID = `in`.readString()
//        OrderStatus = `in`.readString()
        PayID = `in`.readString()
        PaymentMethod = `in`.readString()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

 //       dest.writeString(OrderID)
//        dest.writeString(OrderStatus)
        dest.writeString(PayID)
        dest.writeString(PaymentMethod)

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