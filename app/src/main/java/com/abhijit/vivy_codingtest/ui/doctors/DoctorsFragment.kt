package com.abhijit.vivy_codingtest.ui.doctors


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhijit.vivy_codingtest.R
import com.abhijit.vivy_codingtest.data.local.entity.Doc
import com.abhijit.vivy_codingtest.data.local.viewmodel.RoomDBViewModel
import com.abhijit.vivy_codingtest.data.remote.model.Doctor
import com.abhijit.vivy_codingtest.data.remote.model.Doctors
import com.abhijit.vivy_codingtest.databinding.FragmentDoctorsBinding
import com.abhijit.vivy_codingtest.ui.adapter.SectedDoctorAdapter
import com.abhijit.vivy_codingtest.ui.adapter.DoctorListRecyclerViewAdapter
import com.abhijit.vivy_codingtest.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_doctors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A fragment representing list of vehicles.
 */
@AndroidEntryPoint
class DoctorsFragment : Fragment(), SearchView.OnQueryTextListener {

    private var columnCount = 1
    private val doctorViewModel: DotcorsViewModel by viewModels()
    private val roomDBViewModel: RoomDBViewModel by viewModels()
    private lateinit var binding: FragmentDoctorsBinding
    private var doctorList: List<Doctor>? = null
    private var doctor: Doctor? = null
    private var rootObjectDoctor: Doctors? = null
    private var localDoctorData: List<Doc>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        getRecentlySectedDoctors()
        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            val fm = requireActivity().supportFragmentManager
            fm?.let {
                if (it.backStackEntryCount == 0) {
                    getRecentlySectedDoctors()
                }
            }
        }
    }

    private fun setupObserver() {

        roomDBViewModel.getDoctors().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        doctorViewModel.doctors.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { doctors ->

                        renderList(doctors.doctors)
                        doctorList = doctors.doctors
                        rootObjectDoctor = doctors
                    }
                    list.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    list.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        //lastKey doctor Observe

        doctorViewModel.doctorsLastKey.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { doctors ->
                        var listDoctor = doctorList?.let { it1 -> concatenate(doctors.doctors, it1) }
                        if (listDoctor != null) {
                            renderList(listDoctor)
                        }
                        doctorList = listDoctor
                        rootObjectDoctor = doctors
                    }
                    list.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    list.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_doctors, container, false)
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    rootObjectDoctor?.lastKey?.let {
                        doctorViewModel.fetchDoctorLastKey(it)
                    }

                }
            }
        })

        return binding.root
    }

    private fun renderList(doctor: List<Doctor>) {


        if (binding.list is RecyclerView) {
            with(binding.list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                        DoctorListRecyclerViewAdapter(
                                //sort from higher to lower
                                doctor.sortedWith(compareByDescending({ it.rating }))
                        )
            }
        }
    }

    private fun renderSelectedDoctorList(doctor: List<Doc>) {


        if (binding.recentSearchList is RecyclerView) {
            with(binding.recentSearchList) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =
                        SectedDoctorAdapter(doctor)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.option_menu, menu)
        context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.getActionView() as SearchView
        searchView.setSubmitButtonEnabled(true)
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        var tempList = doctorList
        //filter list of names based on search query
        tempList?.filter { it.name.contains(query.toString(), true) }?.let { renderList(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            //when we clear the search list should refresh to beginning
            if (newText.isEmpty()) {
                doctorList?.let { renderList(it) }
            }
        }
        return true
    }

    fun <Doctor> concatenate(vararg lists: List<Doctor>): List<Doctor> {
        return listOf(*lists).flatten()
    }

    fun getRecentlySectedDoctors() {
        //background thread
        GlobalScope.launch {
            localDoctorData = roomDBViewModel.getAll()!!;
            if (localDoctorData != null) {
                //main UI thread
                launch(Dispatchers.Main) {
                    renderSelectedDoctorList(localDoctorData!!.reversed())
                }


            }
            print(localDoctorData!!.size)
        }

    }


}