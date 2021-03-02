package com.abhijit.vivy_codingtest.data.remote.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Doctor (
        @SerializedName("address")
        var address: String,
        @SerializedName("email")
        val email: Any,
        @SerializedName("highlighted")
        val highlighted: Boolean,
        @SerializedName("id")
        var id: String,
        @SerializedName("integration")
        val integration: Any,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lng")
        val lng: Double,
        @SerializedName("name")
        var name: String,
        @SerializedName("openingHours")
        val openingHours: List<String>,
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @SerializedName("photoId")
        var photoId: String,
        @SerializedName("rating")
        var rating: Float,
        @SerializedName("reviewCount")
        val reviewCount: Int,
        @SerializedName("source")
        val source: String,
        @SerializedName("specialityIds")
        val specialityIds: List<Int>,
        @SerializedName("translation")
        val translation: Any,
        @SerializedName("website")
        val website: String
) : Serializable