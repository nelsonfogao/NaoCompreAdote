package com.example.naocompreadote.api.service

import com.example.naocompreadote.api.model.Adotante
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectService {
    @GET("api/doadores")
    suspend fun getDoadores(): List<Doador>

    @GET("api/doadores/{id}")
    suspend fun getDoadorById(@Path("id")id:String): Doador

    @POST("api/doadores/login")
    suspend fun loginDoador(@Body credenciais: Credenciais) : Doador

    @POST("api/doadores")
    suspend fun criarDoador(@Body doador: Doador): Doador




    @POST("api/adotantes")
    suspend fun criarAdotante(@Body adotante: Adotante): Adotante

    @POST("api/adotantes/login")
    suspend fun loginAdotante(@Body credenciais: Credenciais): Adotante

}