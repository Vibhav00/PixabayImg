package com.example.assignment.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.repository.MainRespository

class ViewModelProviderFactoryM(
    val app: Application,
    val repository: MainRespository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelMain(app, repository) as T
    }
}