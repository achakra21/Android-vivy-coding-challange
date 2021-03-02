package com.abhijit.vivy_codingtest.data.remote.api

import com.abhijit.vivy_codingtest.data.remote.model.Doctors
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getDoctors(): Response<Doctors> = apiService.getDoctors()
    override suspend fun getLasyKeyDocotrs(lastKey: String):Response<Doctors> = apiService.getLastKeyDoctors(lastKey)
}