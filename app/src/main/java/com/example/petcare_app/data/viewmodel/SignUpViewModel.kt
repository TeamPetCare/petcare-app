package com.example.petcare_app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*

class SignUpViewModel : ViewModel() {
    var user by mutableStateOf(User())
    var pets by mutableStateOf<List<Pet>>(listOf(Pet()))

    // Método para atualizar informações de um pet na lista
    fun updatePet(index: Int, updatedPet: Pet) {
        pets = pets.toMutableList().apply {
            this[index] = updatedPet
        }
    }

    // Função para adicionar um novo pet à lista
    fun addPet(pet: Pet) {
        pets = pets + pet
    }

    // Função para remover um pet da lista
    fun removePet(index: Int) {
        pets = pets.toMutableList().apply {
            removeAt(index)
        }
    }
}


data class User(
    var nomeCompleto: String = "",
    var cpf: String = "",
    var email: String = "",
    var celular: String = "",
    var senha: String = "",
    var confirmarSenha: String = "",
    var cep: String = "",
    var logradouro: String = "",
    var bairro: String = "",
    var complemento: String = "",
    var cidade: String = ""
)

data class Pet(
    var nome: String = "",
    var especie: String = "",
    var raca: String = "",
    var dataNascimento: String = "",
    var cor: String = "",
    var porte: String = "",
    var sexo: String = "",
    var observacoes: String = ""
)
