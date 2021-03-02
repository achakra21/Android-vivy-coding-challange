package com.abhijit.vivy_codingtest.ui.doctors

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijit.vivy_codingtest.R
import com.abhijit.vivy_codingtest.data.remote.model.Doctor
import com.abhijit.vivy_codingtest.data.remote.model.Doctors
import com.abhijit.vivy_codingtest.data.remote.repository.FreeNowRepository
import com.abhijit.vivy_codingtest.utils.NetworkHelper
import com.abhijit.vivy_codingtest.utils.Resource
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.launch

//method for binding image in xml using glide
@BindingAdapter("profileImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.getContext())
            .load(imageUrl).apply(RequestOptions().error(R.drawable.ic_baseline_person_24).circleCrop())
            .into(view)
}

//method for binding image in xml using glide
@BindingAdapter("passportImage")
fun loadPassportImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.getContext())
            .load(imageUrl).apply(RequestOptions().error(R.drawable.ic_baseline_person_24))
            .into(view)
}

class DotcorsViewModel @ViewModelInject constructor(
        private val freeNowRepository: FreeNowRepository,
        private val networkHelper: NetworkHelper
) : ViewModel() {


    private val _doctors = MutableLiveData<Resource<Doctors>>()
    val doctors: LiveData<Resource<Doctors>>
        get() = _doctors
    private val _doctorsLastKey = MutableLiveData<Resource<Doctors>>()
    val doctorsLastKey: LiveData<Resource<Doctors>>
        get() = _doctorsLastKey

    init {
        fetchDoctors()
    }

    private fun fetchDoctors() {
        viewModelScope.launch {
            _doctors.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                freeNowRepository.getDoctors(

                ).let {
                    if (it.isSuccessful) {

                        _doctors.postValue(Resource.success(it.body()))
                    } else _doctors.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _doctors.postValue(Resource.error("No internet connection", null))
        }
    }


    public fun fetchDoctorLastKey(lastKey: String) {
        viewModelScope.launch {
            _doctorsLastKey.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                freeNowRepository.getLastKeyDoctors(lastKey

                ).let {
                    if (it.isSuccessful) {

                        _doctorsLastKey.postValue(Resource.success(it.body()))
                    } else _doctorsLastKey.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _doctorsLastKey.postValue(Resource.error("No internet connection", null))
        }
    }


}