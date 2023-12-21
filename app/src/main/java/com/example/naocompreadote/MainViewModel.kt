package com.example.naocompreadote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Adotante
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.api.model.Pet

class MainViewModel : ViewModel(){
    private var _doador: MutableLiveData<Doador> = MutableLiveData()
    val doador: LiveData<Doador>
        get() = _doador

    private var _adotanteLogado : MutableLiveData<Adotante> = MutableLiveData()
    val adotanteLogado: LiveData<Adotante>
        get() = _adotanteLogado


    private var _pet: MutableLiveData<Pet> = MutableLiveData()
    val pet: LiveData<Pet>
        get() = _pet

    private var _adotantes : MutableLiveData<List<Adotante>?> = MutableLiveData()
    val adotante: LiveData<List<Adotante>?>
        get() = _adotantes

    private var _cadastrarPet:MutableLiveData<Pet> = MutableLiveData()
    val cadastrarPet: LiveData<Pet>
        get() = _cadastrarPet

    private var _petPrincipal : MutableLiveData<Pet> = MutableLiveData()
    val petPrincipal: LiveData<Pet>
        get() = _petPrincipal



    fun updateDoador(newData: Doador) {
        _doador.postValue(newData)
    }
    fun updatePet(data: Pet) {
        _pet.postValue(data)
    }
    fun updateAdotanteLogado(data: Adotante) {
        _adotanteLogado.postValue(data)
    }
    fun updateAdotantes(data:List<Adotante>){
        _adotantes.postValue(data)
    }
    fun clearAdotante(){
        _adotantes.postValue(null)
    }
    fun updateCadastrarPet(data: Pet) {
        _cadastrarPet.postValue(data)
    }
    fun updatePetPrincipal(newData: Pet) {
        _petPrincipal.postValue(newData)
    }

    suspend fun loginDoador(credenciais: Credenciais): Doador? {
        var login = ApiClient.getProjectService().loginDoador(credenciais)
        if (login != null) {
            updateDoador(login)
        }
        return _doador.value
    }
    suspend fun loginAdotante(credenciais: Credenciais): Adotante? {
        var loginAdotante = ApiClient.getProjectService().loginAdotante(credenciais)
        if (loginAdotante != null) {
            updateAdotanteLogado(loginAdotante)
        }
        return _adotanteLogado.value
    }
    suspend fun getDoadorById(doadorId : String): Doador? {
        var login = ApiClient.getProjectService().getDoadorById(doadorId)
        updateDoador(login)
        return _doador.value
    }
    suspend fun getAdotanteById(adotanteId : String): Adotante? {
        var login = ApiClient.getProjectService().getAdotanteById(adotanteId)
        updateAdotanteLogado(login)
        return _adotanteLogado.value
    }
    suspend fun getPetById(petId : String): Pet? {
        var login = ApiClient.getProjectService().getPetById(petId)
        updatePet(login)
        return _pet.value
    }
    suspend fun getAdotanteByPetId(petId : String): List<Adotante>? {
        var login = ApiClient.getProjectService().getAdotantesByPetIdAsync(petId)
        updateAdotantes(login)
        return _adotantes.value
    }
    suspend fun postCadastarPet(pet : Pet, id:String): Pet {
        var pet2 = ApiClient.getProjectService().createPet(pet, id)
        updateCadastrarPet(pet2)
        return _cadastrarPet.value!!
    }
    suspend fun getPetPrincipal(adotanteId : String): Pet {
        var pet = ApiClient.getProjectService().getPetsNonFavorite(adotanteId)
        updatePetPrincipal(pet)
        return _petPrincipal.value!!
    }
}