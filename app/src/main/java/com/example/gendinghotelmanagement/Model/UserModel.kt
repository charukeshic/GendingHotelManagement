package com.example.gendinghotelmanagement.Model

import android.os.Parcel
import android.os.Parcelable

class UserModel : Parcelable {

    var email: String? = null
    var role: String? = null
    var name: String? = null
    var address: String? = null
    var password: String? = null
    var conPassword: String? = null
    var phone: String? = null
    var userID: String? = null
//    var StaffName: String? = null
//    var PhoneNO: Int? = null
//    var Address: String? = null

    //var userImage: String? = null

    constructor() {}
    constructor(
            email: String?,
            role: String?,
            name: String?,
            address: String,
            password: String?,
            conPassword: String?,
            phone: String?,
            userID: String?
//            StaffName: String?,
//            PhoneNO: Int?,
//            Address: String?
       // userImage: String?


    ) {
        this.email = email
        this.role = role
        this.name = name
        this.address = address
        this.password = password
        this.conPassword = conPassword
        this.phone = phone
        this.userID = userID
//        this.StaffName = StaffName
//        this.PhoneNO = PhoneNO
//        this.Address = Address
        //this.userImage = userImage
    }

    protected constructor(`in`: Parcel) {
        email = `in`.readString()
        role = `in`.readString()
        name = `in`.readString()
        address = `in`.readString()
        password = `in`.readString()
        conPassword = `in`.readString()
        phone = `in`.readString()
        userID = `in`.readString()
//        StaffName = `in`.readString()
//        PhoneNO = `in`.readInt()
//        Address = `in`.readString()
       // userImage = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(email)
        dest.writeString(role)
        dest.writeString(name)
        dest.writeString(address)
        dest.writeString(password)
        dest.writeString(conPassword)
        dest.writeString(phone)
        dest.writeString(userID)
//        dest.writeString(StaffName)
//        PhoneNO?.let { dest.writeInt(it) }
//        dest.writeString(Address)
       // dest.writeString(userImage)
    }

    override fun describeContents(): Int {
        return 0
    }


    //comment
    companion object {
        val CREATOR: Parcelable.Creator<UserModel?> = object : Parcelable.Creator<UserModel?> {
            override fun createFromParcel(`in`: Parcel): UserModel? {
                return UserModel(`in`)
            }

            override fun newArray(size: Int): Array<UserModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}
