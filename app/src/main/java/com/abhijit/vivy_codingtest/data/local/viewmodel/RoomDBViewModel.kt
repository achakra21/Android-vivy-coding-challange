package com.abhijit.vivy_codingtest.data.local.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijit.vivy_codingtest.data.local.dbhelper.DatabaseHelper
import com.abhijit.vivy_codingtest.data.local.entity.Doc
import com.abhijit.vivy_codingtest.data.local.repository.DatabaseHelperImpl
import com.abhijit.vivy_codingtest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class RoomDBViewModel @ViewModelInject constructor(private val databaseHelperImpl: DatabaseHelperImpl) :
        ViewModel() {
    @Inject
    lateinit var dbHelper: DatabaseHelper
    private val doctors = MutableLiveData<Resource<List<Doc>>>()
    //lateinit var doctor: Doc

    init {
        fetchDoctors()
    }

    private fun fetchDoctors() {
        viewModelScope.launch {

            doctors.postValue(Resource.loading(null))
            try {

                withContext(Dispatchers.IO) {
                    // val doctor: Doc = Doc(1,"abbb","djdjd","fdfdd","dididi")
                    //databaseHelperImpl.insertDoctor(doctor)
                    val doctorsFromDb = databaseHelperImpl.getDoctors()
                    //databaseHelperImpl.deleteAll()
                }


            } catch (e: Exception) {
                doctors.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun getDoctors(): LiveData<Resource<List<Doc>>> {
        return doctors
    }

    suspend fun insertDoctor(doctor: Doc) {

        withContext(Dispatchers.IO) {
            databaseHelperImpl.doctorDatabse.doctorDao().insert(doctor)
        }
    }

    suspend fun deleteDoctor(doctor: Doc) {
        withContext(Dispatchers.IO) {
            databaseHelperImpl.doctorDatabse.doctorDao().deleteAll(doctor)
        }

    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            databaseHelperImpl.doctorDatabse.doctorDao().deleteAll()
        }

    }

    suspend fun getAll(): List<Doc>? {
        lateinit var data:List<Doc>
        data = listOf()
        withContext(Dispatchers.IO){
            data =  databaseHelperImpl.doctorDatabse.doctorDao().getAll()
            return@withContext data
        }
        return data

    }
}