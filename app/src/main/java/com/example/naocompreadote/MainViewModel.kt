package com.example.naocompreadote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.naocompreadote.api.ApiClient
import com.example.naocompreadote.api.model.Credenciais
import com.example.naocompreadote.api.model.Doador
import com.example.naocompreadote.api.model.Pet
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    var _doador: MutableLiveData<Doador> = MutableLiveData()
    val doador: LiveData<Doador>
        get() = _doador

    fun updateDoador(newData: Doador) {
        _doador.value = newData
    }
}