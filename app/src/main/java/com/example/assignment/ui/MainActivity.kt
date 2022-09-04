package com.example.assignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.R
import com.example.assignment.repository.MainRespository

class MainActivity : AppCompatActivity() {
    lateinit var viewModelMain: ViewModelMain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = MainRespository()
        val vmpf = ViewModelProviderFactoryM(application, repo)
        viewModelMain = ViewModelProvider(this, vmpf).get(ViewModelMain::class.java)
        setContentView(R.layout.activity_main)

    }
}