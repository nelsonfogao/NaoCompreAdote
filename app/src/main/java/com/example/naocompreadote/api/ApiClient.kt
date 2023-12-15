package com.example.naocompreadote.api

import com.example.naocompreadote.api.service.ProjectService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var instance: Retrofit? = null
    private fun getRetrofit(): Retrofit{
        if (instance == null)
            instance = Retrofit
                .Builder()
                .baseUrl("http://10.0.2.2:5275/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return instance as Retrofit
    }

    fun getProjectService(): ProjectService {
        var retrofit:Retrofit = getRetrofit()
        return retrofit.create(ProjectService::class.java)
    }
}