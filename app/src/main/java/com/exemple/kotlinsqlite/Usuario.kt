package com.exemple.kotlinsqlite

class Usuario {
    var id = 0
    var nome: String = ""
    var idade = 0

    constructor(nome: String, idade: Int){
        this.nome = nome
        this.idade = idade
    }
    constructor(){
    }
}