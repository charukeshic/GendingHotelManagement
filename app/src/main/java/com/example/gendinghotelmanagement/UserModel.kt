package com.example.gendinghotelmanagement

import android.os.Parcel
import android.os.Parcelable

class UserModel : Parcelable {

    var conPassword: String? = null
    var email: String? = null
    var password: String? = null
    var role: String? = null
    var userID: String? = null

    //var userImage: String? = null

    constructor() {}
    constructor(
            conPassword: String?,
            email: String?,
            password: String?,
            role: String?,
            userID: String?
       // userImage: String?


    ) {
        this.conPassword = conPassword
        this.email = email
        this.password = password
        this.role = role
        this.userID = userID
        //this.userImage = userImage
    }

    protected constructor(`in`: Parcel) {
        conPassword = `in`.readString()
        email = `in`.readString()
        password = `in`.readString()
        role = `in`.readString()
        userID = `in`.readString()
       // userImage = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(conPassword)
        dest.writeString(email)
        dest.writeString(password)
        dest.writeString(role)
        dest.writeString(userID)
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
