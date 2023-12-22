package com.example.naocompreadote

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Adotante
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.api.model.Adocoes
import com.example.naocompreadote.api.model.Pet
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import java.util.UUID

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

    private val _imagemPet = MutableLiveData<String>()
    val imagemPet: LiveData<String> = _imagemPet

    private val _imagemAdotante = MutableLiveData<String>()
    val imagemAdotante: LiveData<String> = _imagemAdotante

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
    fun updateImagemPet(imagemPet: String) {
        _imagemPet.value = imagemPet
    }
    fun updateImagemAdotante(imagemAdotante: String) {
        _imagemAdotante.value = imagemAdotante
    }
    fun loginDoador(credenciais: Credenciais): Doador? {
        viewModelScope.launch {
            var login = ApiClient.getProjectService().loginDoador(credenciais)
            if (login != null) {
                updateDoador(login)
            }
        }
        return _doador.value
    }
    fun criarDoador(createDoador: Doador){
        viewModelScope.launch {
            ApiClient.getProjectService().criarDoador(createDoador)
        }
    }
    fun criarAdotante(createAdotante: Adotante){
        viewModelScope.launch {
            ApiClient.getProjectService().criarAdotante(createAdotante)
        }
    }
    fun loginAdotante(credenciais: Credenciais): Adotante? {
        viewModelScope.launch{
            var loginAdotante = ApiClient.getProjectService().loginAdotante(credenciais)
            if (loginAdotante != null) {
                updateAdotanteLogado(loginAdotante)
            }
        }
        return _adotanteLogado.value
    }
    fun getDoadorById(doadorId : String): Doador? {
        viewModelScope.launch{
            var login = ApiClient.getProjectService().getDoadorById(doadorId)
            updateDoador(login)
        }
        return _doador.value
    }
    fun getAdotanteById(adotanteId : String): Adotante? {
        viewModelScope.launch {
            var login = ApiClient.getProjectService().getAdotanteById(adotanteId)
            updateAdotanteLogado(login)
        }
        return _adotanteLogado.value
    }
    fun getPetById(petId : String): Pet? {
        viewModelScope.launch {
            var login = ApiClient.getProjectService().getPetById(petId)
            updatePet(login)
        }
        return _pet.value
    }
    fun getAdotanteByPetId(petId : String): List<Adotante>? {
        viewModelScope.launch {
            var login = ApiClient.getProjectService().getAdotantesByPetIdAsync(petId)
            updateAdotantes(login)
        }
        return _adotantes.value
    }
    fun postCadastarPet(cadastrarPet : Pet, id:String): Pet? {
        viewModelScope.launch {
            var pet2 = ApiClient.getProjectService().createPet(cadastrarPet, id)
            updateCadastrarPet(pet2)
        }
        return _cadastrarPet.value
    }
    fun getPetPrincipal(adotanteId : String): Pet? {
        viewModelScope.launch {
            var getPet = ApiClient.getProjectService().getPetsNonFavorite(adotanteId)
            updatePetPrincipal(getPet)
        }
        return _petPrincipal.value
    }
    fun postAdocaoAtual() {
        var adocao2 = createAdocao()
        viewModelScope.launch {
            ApiClient.getProjectService().criarAdocao(adocao2)
            getPetPrincipal(adocao2.adotanteId!!)
        }
    }
    fun createAdocao(): Adocoes{
        var adocoes = Adocoes()
        adocoes.adotanteId = adotanteLogado.value?.adotanteId
        adocoes.petId = petPrincipal.value?.petId
        return adocoes
    }


    fun uploadImagePet(uri: Uri) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()

        val storageRef: StorageReference = storage.reference
        val imagesRef: StorageReference = storageRef.child("${UUID.randomUUID()}")
        val uploadTask = imagesRef.putFile(uri)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imagesRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    updateImagemPet(imageUrl)
                }
            }
        }
    }
    fun uploadImageAdotante(uri: Uri) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()

        val storageRef: StorageReference = storage.reference
        val imagesRef: StorageReference = storageRef.child("${UUID.randomUUID()}")
        val uploadTask = imagesRef.putFile(uri)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                imagesRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    updateImagemAdotante(imageUrl)
                }
            }
        }
    }
}