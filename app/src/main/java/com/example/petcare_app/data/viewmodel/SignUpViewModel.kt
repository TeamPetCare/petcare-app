package com.example.petcare_app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {
    private val _pets = MutableStateFlow<List<Pet>>(listOf(Pet()))
    val pets = _pets.asStateFlow()
    var user by mutableStateOf(User())


    fun updatePet(index: Int, updatedPet: Pet) {
        _pets.value = _pets.value.toMutableList().apply {
            this[index] = updatedPet
        }
    }

    fun addPet(pet: Pet) {
        if (!_pets.value.contains(pet)) {
            _pets.value = _pets.value + pet
        }
    }

    fun removePet(index: Int) {
        _pets.value = _pets.value.toMutableList().apply {
            removeAt(index)
        }
    }
}

class User {
    var nomeCompleto by mutableStateOf("")
    var cpf by mutableStateOf("")
    var email by mutableStateOf("")
    var celular by mutableStateOf("")
    var senha by mutableStateOf("")
    var confirmarSenha by mutableStateOf("")
    var cep by mutableStateOf("")
    var logradouro by mutableStateOf("")
    var bairro by mutableStateOf("")
    var numero by mutableStateOf("")
    var complemento by mutableStateOf("")
    var cidade by mutableStateOf("")
}

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
