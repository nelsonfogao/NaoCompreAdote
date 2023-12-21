package com.example.naocompreadote.api.model

import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naocompreadote.PetsRecyclerAdapter
import com.example.naocompreadote.api.ApiClient
import com.google.type.DateTime

class Pet (
    var petId:String? = null,
    var nome: String? = null,
    var dataNascimento: String? = null,
    var ehDog: Boolean? = null,
    var doadorId: String? = null,
    var disponivel: Boolean? = null,
    var fotoUrl: String? = null,
    var caracteristicas: List<Caracteristicas>? = null,
    var adocoes: List<Adocoes>? = null

)

