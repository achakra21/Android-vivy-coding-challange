package com.abhijit.vivy_codingtest.ui.doctor_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.abhijit.vivy_codingtest.BR
import com.abhijit.vivy_codingtest.R
import com.abhijit.vivy_codingtest.data.local.entity.Doc
import com.abhijit.vivy_codingtest.data.local.viewmodel.RoomDBViewModel
import com.abhijit.vivy_codingtest.data.remote.model.Doctor
import com.abhijit.vivy_codingtest.data.remote.model.Doctors
import com.abhijit.vivy_codingtest.databinding.FragmentDoctordetailsBinding
import com.abhijit.vivy_codingtest.ui.doctors.DotcorsViewModel
import com.abhijit.vivy_codingtest.utils.CommonUtils
import com.google.android.gms.maps.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DoctorsDetailFragment : Fragment() {

    private val mapsViewModel: DoctorsDetailViewModel by viewModels()
    private val vehicleViewModel: DotcorsViewModel by viewModels()
    private lateinit var doctor: Doctor
    private lateinit var binding: FragmentDoctordetailsBinding
    private val roomDBViewModel: RoomDBViewModel by viewModels()
    lateinit var localDoctorData: List<Doc>


    private val callback = OnMapReadyCallback { googleMap ->

        vehicleViewModel.doctors.observe(viewLifecycleOwner, Observer {
            val poiList: Doctors? = it.data
            if (poiList != null) {
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_doctordetails, container, false)
        doctor = (requireArguments().getSerializable("DoctorObj") as Doctor?)!!
        if (doctor.address == null) {
            doctor.address = null.toString()
        }
        if (doctor.name == null) {
            doctor.name = null.toString()
        }
        if (doctor.photoId == null) {
            doctor.photoId = null.toString()
        }
        if (doctor.id == null) {
            doctor.id = null.toString()

        }
        var doc = Doc(0, doctor.address, doctor.name, doctor.photoId, doctor.id)
        binding.setVariable(BR.doctor, doctor)

        localDoctorData = listOf()

        GlobalScope.launch {
            localDoctorData = roomDBViewModel.getAll()!!;
            print(localDoctorData.size)
            //roomDBViewModel.deleteAll()
            if (localDoctorData.isEmpty() || localDoctorData.size < 3) {
                if (!doc.doctorID.isEmpty()) {
                    roomDBViewModel.insertDoctor(doc)
                }
            } else {

                if (!doc.doctorID.isEmpty()) {

                    if (localDoctorData.size == 3) {
                        if(localDoctorData.filter { it.doctorID == doc.doctorID }.size !=1) {
                            roomDBViewModel.deleteDoctor(localDoctorData[0])
                        }


                    }
                    roomDBViewModel.insertDoctor(doc)
                }

            }
            localDoctorData = roomDBViewModel.getAll()!!;

        }

        showBackButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onPause() {
        super.onPause()
        CommonUtils.setRecentDoctor(localDoctorData)

        print("localdata" + localDoctorData)
    }


    /**
     * Changes the icon of the drawer to back
     */
    fun showBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        }
    }


}




