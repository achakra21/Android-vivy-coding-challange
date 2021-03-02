package com.abhijit.vivy_codingtest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhijit.vivy_codingtest.data.local.dao.DoctorDao
import com.abhijit.vivy_codingtest.data.local.entity.Doc

@Database(entities = [Doc::class],version = 1)
abstract class DoctorDatabase : RoomDatabase() {
    abstract fun doctorDao():DoctorDao
}