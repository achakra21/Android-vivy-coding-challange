package com.abhijit.vivy_codingtest.data.remote.api

import com.abhijit.vivy_codingtest.data.remote.model.Doctors
import retrofit2.Response

interface ApiHelper {
    //can with as non blocking
    suspend fun getDoctors(): Response<Doctors>
    suspend fun getLasyKeyDocotrs(lastKey: String): Response<Doctors>
}