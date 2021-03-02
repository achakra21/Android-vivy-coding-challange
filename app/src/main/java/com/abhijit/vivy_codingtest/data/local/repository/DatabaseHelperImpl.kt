package com.abhijit.vivy_codingtest.data.local.repository

import androidx.lifecycle.LiveData
import com.abhijit.vivy_codingtest.data.local.db.DoctorDatabase
import com.abhijit.vivy_codingtest.data.local.dbhelper.DatabaseHelper
import com.abhijit.vivy_codingtest.data.local.entity.Doc
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor() : DatabaseHelper {
    @Inject lateinit var doctorDatabse: DoctorDatabase
    override suspend fun getDoctors(): List<Doc>
            = doctorDatabse.doctorDao().getAll()
    override  suspend fun insertAll(doctor: List<Doc>)
            = doctorDatabse.doctorDao().insertAll(doctor)
    override  suspend fun insertDoctor(doctor: Doc): Unit
            = doctorDatabse.doctorDao().insert(doctor)
    override suspend fun deleteAll(doctor: Doc) :Unit
            = doctorDatabse.doctorDao().deleteAll(doctor)
    override suspend fun deleteAll():Unit
            = doctorDatabse.doctorDao().deleteAll()

}