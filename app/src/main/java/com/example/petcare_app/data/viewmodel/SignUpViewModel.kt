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
    var numero: String = "",
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
