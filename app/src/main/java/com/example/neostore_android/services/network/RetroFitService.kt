package com.example.neostore_android.services.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitService {

    private const val BASE_URL="http://staging.php-dev.in:8844/trainingapp/api/"

    private val retroFit= Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

   fun getProductAPI():ProductAPI{
       return  retroFit.create(ProductAPI::class.java)
   }
    fun getUserAPI():UserAPI{
        return  retroFit.create(UserAPI::class.java)
    }

}