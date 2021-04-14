package com.example.gendinghotelmanagement

import android.os.Parcel
import android.os.Parcelable

class UserModel : Parcelable {

    var conPassword: String? = null
    var email: String? = null
    var password: String? = null
    var role: String? = null
    var userID: String? = null
//    var StaffName: String? = null
//    var PhoneNO: Int? = null
//    var Address: String? = null

    //var userImage: String? = null

    constructor() {}
    constructor(
            conPassword: String?,
            email: String?,
            password: String?,
            role: String?,
            userID: String?
//            StaffName: String?,
//            PhoneNO: Int?,
//            Address: String?
       // userImage: String?


    ) {
        this.conPassword = conPassword
        this.email = email
        this.password = password
        this.role = role
        this.userID = userID
//        this.StaffName = StaffName
//        this.PhoneNO = PhoneNO
//        this.Address = Address
        //this.userImage = userImage
    }

    protected constructor(`in`: Parcel) {
        conPassword = `in`.readString()
        email = `in`.readString()
        password = `in`.readString()
        role = `in`.readString()
        userID = `in`.readString()
//        StaffName = `in`.readString()
//        PhoneNO = `in`.readInt()
//        Address = `in`.readString()
       // userImage = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(conPassword)
        dest.writeString(email)
        dest.writeString(password)
        dest.writeString(role)
        dest.writeString(userID)
//        dest.writeString(StaffName)
//        PhoneNO?.let { dest.writeInt(it) }
//        dest.writeString(Address)
       // dest.writeString(userImage)
    }

    override fun describeContents(): Int {
        return 0
    }


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
