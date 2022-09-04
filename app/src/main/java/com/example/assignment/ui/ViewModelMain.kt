package com.example.assignment.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment.models.pixabayitem.PixabayItem
import com.example.assignment.repository.MainRespository
import com.example.assignment.ui.fragment.ApplicationMain
import com.example.assignment.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ViewModelMain(app: Application, val repo: MainRespository) : AndroidViewModel(app) {

    val list: MutableLiveData<Resource<PixabayItem>> = MutableLiveData()

    init {
        getList("sun")
    }

    fun getList(string: String) = viewModelScope.launch {
        getSafeList(string)
    }

    private fun handleResponse(response: Response<PixabayItem>): Resource<PixabayItem> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun getSafeList(string: String) {
        list.postValue(Resource.Loding())
        try {
            if (hasInternateConnection()) {
                val response = repo.getImageList(string)
                list.postValue(handleResponse(response))
            } else {
                list.postValue(Resource.Error("internet problem "))


            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> list.postValue(Resource.Error("ioexception"))
                else -> list.postValue(Resource.Error("conversion error"))
            }
        }
    }

    fun hasInternateConnection(): Boolean {

        val connectivityManager = getApplication<ApplicationMain>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {

            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }


            }
        }
        return false
    }
}