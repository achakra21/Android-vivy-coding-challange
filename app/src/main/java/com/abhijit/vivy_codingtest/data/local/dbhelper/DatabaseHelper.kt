package com.abhijit.vivy_codingtest.data.local.dbhelper

import androidx.lifecycle.LiveData
import com.abhijit.vivy_codingtest.data.local.entity.Doc
import com.abhijit.vivy_codingtest.data.remote.model.Doctor

interface DatabaseHelper {
     suspend fun  getDoctors():List<Doc>
     suspend fun insertAll(doctor: List<Doc>)
     suspend fun insertDoctor(doctor:Doc)
     suspend fun deleteAll(doctor: Doc)
     suspend fun deleteAll()
}