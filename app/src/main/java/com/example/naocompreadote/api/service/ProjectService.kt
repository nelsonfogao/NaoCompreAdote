package com.example.naocompreadote.api.service

import com.example.naocompreadote.api.model.Adocoes
import com.example.naocompreadote.api.model.Adotante
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.api.model.Pet
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
    suspend fun loginDoador(@Body credenciais: Credenciais) : Doador?

    @POST("api/doadores")
    suspend fun criarDoador(@Body doador: Doador): Doador


    @GET("api/pets/adotantespet/{id}")
    suspend fun getPetsNonFavorite(@Path("id")id:String): Pet

    @GET("api/pets/{id}")
    suspend fun getPetById(@Path("id")id:String): Pet

    @POST("api/pets/{id}")
    suspend fun createPet(@Body pet: Pet, @Path("id")id:String): Pet



    @GET("api/adotantes/{id}")
    suspend fun getAdotanteById(@Path("id")id:String): Adotante

    @POST("api/adotantes/adocao")
    suspend fun criarAdocao(@Body adocoes: Adocoes):Adocoes

    @POST("api/adotantes")
    suspend fun criarAdotante(@Body adotante: Adotante): Adotante

    @POST("api/adotantes/login")
    suspend fun loginAdotante(@Body credenciais: Credenciais): Adotante?

    @GET("api/adotantes/adotantespet/{id}")
    suspend fun getAdotantesByPetIdAsync(@Path("id")id:String): List<Adotante>

}