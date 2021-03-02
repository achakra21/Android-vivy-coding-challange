package com.abhijit.vivy_codingtest.data.remote.api

import com.abhijit.vivy_codingtest.data.remote.model.Doctors
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("doctors.json")
    suspend fun  getDoctors():Response<Doctors>

    @GET("doctors-{lastKey}.json")
    suspend fun  getLastKeyDoctors(@Path("lastKey",encoded = true)lastKey:String):Response<Doctors>



}