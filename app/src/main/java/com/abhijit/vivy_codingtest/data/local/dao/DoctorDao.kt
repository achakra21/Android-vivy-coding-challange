package com.abhijit.vivy_codingtest.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abhijit.vivy_codingtest.data.local.entity.Doc


@Dao
interface DoctorDao {

    @Query("SELECT * FROM rdoctors")
    fun getAll(): List<Doc>

    @Insert
    fun insertAll(doctor: List<Doc>)

    @Delete
    fun deleteAll(doctor: Doc)

    @Query("DELETE FROM rdoctors")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(doc: Doc)


}