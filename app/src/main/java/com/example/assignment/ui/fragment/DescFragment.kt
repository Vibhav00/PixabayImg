package com.example.assignment.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.ui.MainActivity
import com.example.assignment.ui.ViewModelMain
import kotlinx.android.synthetic.main.fragment_desc.*
import kotlinx.android.synthetic.main.items.*

class DescFragment : Fragment(R.layout.fragment_desc) {
    val args: DescFragmentArgs by navArgs()
    lateinit var viewmodel: ViewModelMain
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val circularProgressDrawable = CircularProgressDrawable((activity as MainActivity).applicationContext)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()


        val url = args.url
        Glide.with(this).load(url).placeholder(circularProgressDrawable).into(iv_desc)


    }
}