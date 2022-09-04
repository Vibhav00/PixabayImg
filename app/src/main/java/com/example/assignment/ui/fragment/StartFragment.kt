package com.example.assignment.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignment.R
import com.example.assignment.adapter.ISAdapter
import com.example.assignment.ui.MainActivity
import com.example.assignment.ui.ViewModelMain
import com.example.assignment.util.Resource
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.chrono.IsoChronology

class StartFragment : Fragment(R.layout.fragment_start) {
    lateinit var viewmodel: ViewModelMain
    lateinit var isadapter: ISAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = (activity as MainActivity).viewModelMain
        if (!viewmodel.hasInternateConnection()) {
            Toast.makeText(
                (activity as MainActivity).applicationContext,
                "NO ITERNET CONNECTION ",
                Toast.LENGTH_SHORT
            ).show()
        }
        setUpRecyclerView()

        var job: Job? = null
        et_main.addTextChangedListener { it ->
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                it?.let {
                    if(it.isNotEmpty())
                    viewmodel.getList(it.toString())
                }
            }
        }

        isadapter.setOnItemClickListner {
            val bundle = Bundle().apply {
                putSerializable("url", it.largeImageURL)

            }
            findNavController().navigate(R.id.action_startFragment_to_descFragment, bundle)
        }



        viewmodel.list.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { res ->
                        isadapter.differ.submitList(res.hits)

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.e("kjkj", "Erroor occcured in iavl")
                    }
                }
                is Resource.Loding -> {

                    showProgressBar()
                }
            }

        }

        )


    }

    fun hideProgressBar() {
        pb_main.visibility = View.INVISIBLE
    }

    fun showProgressBar() {
        pb_main.visibility = View.VISIBLE
    }

    fun setUpRecyclerView() {
        isadapter = ISAdapter()
        rv_main.apply {
            adapter = isadapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}