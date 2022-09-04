package com.example.assignment.repository

import com.example.assignment.api.RetrofitInstance
import com.example.assignment.util.Constants.Companion.API_KEY

class MainRespository {

    suspend fun getImageList(string: String)=RetrofitInstance.api.getPixaImg(API_KEY,string,60)


}