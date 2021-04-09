package com.example.gendinghotelmanagement

import android.os.Parcel
import android.os.Parcelable

class UserModel : Parcelable {
    var email: String? = null
    var password: String? = null
    var conPassword: String? = null
    var role: String? = null
    //var userImage: String? = null

    constructor() {}
    constructor(

        email: String?,
        password: String?,
        conPassword: String?,
        role: String?
       // userImage: String?

    ) {
        this.email = email
        this.password = password
        this.conPassword = conPassword
        this.role = role
        //this.userImage = userImage
    }

    protected constructor(`in`: Parcel) {
        email = `in`.readString()
        password = `in`.readString()
        conPassword = `in`.readString()
        role = `in`.readString()
       // userImage = `in`.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(email)
        dest.writeString(password)
        dest.writeString(conPassword)
        dest.writeString(role)
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
