package com.example.petcare_app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

class SignUpViewModel : ViewModel() {
    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets
    private val _user = mutableStateOf(User())
    val user: State<User> = _user


    fun updateUser(update: User.() -> User) {
        _user.value = _user.value.update()
    }

    fun initializeWithUser(user: User) {
        _user.value = user
    }

    fun updatePet(index: Int, updatedPet: Pet) {
        val petList = _pets.value.toMutableList()

        if (index >= 0 && index < petList.size) {
            petList[index] = updatedPet
        } else {
            petList.add(updatedPet)
        }

        _pets.value = petList
    }


    fun addPet(pet: Pet) {
        if (_pets.value.none { it.nome == pet.nome }) {
            _pets.value = _pets.value + pet
        }
    }

    fun removePet(index: Int) {
        _pets.value = _pets.value.toMutableList().apply {
            removeAt(index)
        }
    }
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
