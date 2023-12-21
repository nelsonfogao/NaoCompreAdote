package com.example.naocompreadote.api.model

class Adotante (
    var adotanteId:String? = null,
    var nome: String? = null,
    var email: String? = null,
    var senha: String? = null,
    var telefone: String? = null,
    var endereco: String? = null,
    var cpf:String? = null,
    var fotoUrl:String? = null,
    var adocoes: List<Adocoes>? = null
)