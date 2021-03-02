package com.abhijit.vivy_codingtest.ui.adapter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhijit.vivy_codingtest.R
import com.abhijit.vivy_codingtest.data.local.entity.Doc
import com.abhijit.vivy_codingtest.data.remote.model.Doctor
import com.abhijit.vivy_codingtest.databinding.DoctorSelectedListBinding
import com.abhijit.vivy_codingtest.databinding.FragmentDoctorListBinding
import com.abhijit.vivy_codingtest.rootui.MainActivity
import com.abhijit.vivy_codingtest.ui.doctor_details.DoctorsDetailFragment
import dagger.hilt.android.internal.managers.ViewComponentManager


class SectedDoctorAdapter(
        private val doctorList: List<Doc>

) : RecyclerView.Adapter<SectedDoctorAdapter.ViewHolder>() {

    private lateinit var context:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        LayoutInflater.from(parent.context)
                .inflate(R.layout.doctor_selected_list, parent, false)
        context = parent.context
        return ViewHolder(
                DataBindingUtil.inflate
                (
                        LayoutInflater.from(parent.context),
                        R.layout.doctor_selected_list,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.doctors = doctorList[position]
        holder.binding.itemView.setOnClickListener(View.OnClickListener {
            val b = Bundle()
            var doctor =  Doctor(doctorList[position].address,"",false,"",""
            ,2.0,2.0,doctorList[position].name, listOf(),"w",doctorList[position].photoId,2f,1,"", listOf(),"","")
            b.putSerializable("DoctorObj",doctor);

            val fragment: Fragment = DoctorsDetailFragment()
            fragment.setArguments(b);
            val fragmentManager: FragmentManager = (activityContext() as MainActivity).supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
    }

    override fun getItemCount(): Int = doctorList.size

    inner class ViewHolder(val binding: DoctorSelectedListBinding) :
            RecyclerView.ViewHolder(binding.root)

    private fun activityContext(): Context? {

        return if (context is ViewComponentManager.FragmentContextWrapper) {
            (context as ViewComponentManager.FragmentContextWrapper).baseContext
        } else context
    }
}


