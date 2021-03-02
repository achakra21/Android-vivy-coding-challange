package com.abhijit.vivy_codingtest.data.remote.repository

import com.abhijit.vivy_codingtest.data.remote.api.ApiHelper
import com.abhijit.vivy_codingtest.data.remote.model.Doctors
import retrofit2.Response
import javax.inject.Inject

class FreeNowRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getDoctors():Response<Doctors> = apiHelper.getDoctors()
    suspend fun getLastKeyDoctors(lastKey:String):Response<Doctors> = apiHelper.getLasyKeyDocotrs(lastKey)


}