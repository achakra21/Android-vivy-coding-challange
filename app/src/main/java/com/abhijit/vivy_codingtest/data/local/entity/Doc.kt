package com.abhijit.vivy_codingtest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//here doctorID is unique no duplicate id of same ID would insert in table
@Entity(tableName = "rdoctors",indices = [Index(value = ["doctorID"], unique = true)])
//@Entity(tableName = "rdoctors",primaryKeys = ["id", "doctorID"])
data class Doc(
        @PrimaryKey(autoGenerate = true) val id:Int,
        @ColumnInfo(name="address") var  address: String,
        @ColumnInfo(name="name") var  name: String,
        @ColumnInfo(name="photoId") var  photoId: String,
        @ColumnInfo(name="doctorID") var  doctorID: String

): Serializable







//        @SerializedName("address")
//var  address: String,
//@SerializedName("email")
//var email: Any,
//@SerializedName("highlighted")
//var highlighted: Boolean,
//@SerializedName("id")
//var id: String,
//@SerializedName("integration")
//var integration: Any,
//@SerializedName("lat")
//var lat: Double,
//@SerializedName("lng")
//var lng: Double,
//@SerializedName("name")
//var name: String,
//@SerializedName("openingHours")
//var openingHours: List<String>,
//@SerializedName("phoneNumber")
//var phoneNumber: String,
//@SerializedName("photoId")
//var photoId: String,
//@SerializedName("rating")
//var rating: Float,
//@SerializedName("reviewCount")
//var reviewCount: Int,
//@SerializedName("source")
//var source: String,
//@SerializedName("specialityIds")
//var specialityIds: List<Int>,
//@SerializedName("translation")
//var translation: Any,
//@SerializedName("website")
//var website: String

//)

