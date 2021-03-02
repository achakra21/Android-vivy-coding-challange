package com.abhijit.vivy_codingtest.data.remote.model



import com.google.gson.annotations.SerializedName

data class Doctors(
    @SerializedName("doctors")
    val doctors: List<Doctor>,
    @SerializedName("lastKey")
    val lastKey: String
)