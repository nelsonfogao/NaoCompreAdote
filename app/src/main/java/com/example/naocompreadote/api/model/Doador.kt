package com.example.naocompreadote.api.model

class Doador (
        var doadorId:String? = null,
        var nome: String? = null,
        var email: String? = null,
        var senha: String? = null,
        var telefone: String? = null,
        var endereco: String? = null,
        var pets:List<Pet>? = null
    )