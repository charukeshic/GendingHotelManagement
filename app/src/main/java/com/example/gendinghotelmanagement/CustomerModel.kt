package com.example.gendinghotelmanagement

import android.os.Parcel
import android.os.Parcelable

class CustomerModel : Parcelable {

    var CustomerID: String? = null
    var CustomerName: String? = null
    var Email: String? = null
    var Phones: Int? = null


    constructor() {}
    constructor(
            CustomerID: String?,
            CustomerName: String?,
            Email: String?,
            Phones: Int?


    ) {
        this.CustomerID= CustomerID
        this.CustomerName= CustomerName
        this.Email= Email
        this.Phones= Phones

    }

    protected constructor(`in`: Parcel) {

        CustomerID = `in`.readString()
        CustomerName = `in`.readString()
        Email = `in`.readString()
        Phones = `in`.readInt()

    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeString(CustomerID)
        dest.writeString(CustomerName)
        dest.writeString(Email)
        Phones?.let { dest.writeInt(it) }
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        val CREATOR: Parcelable.Creator<CustomerModel?> = object : Parcelable.Creator<CustomerModel?> {
            override fun createFromParcel(`in`: Parcel): CustomerModel? {
                return CustomerModel(`in`)
            }

            override fun newArray(size: Int): Array<CustomerModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}