package com.example.petcare_app.data.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EditUserViewModel : ViewModel() {
    var editUser by mutableStateOf(EditUser())

    fun initializeWithUser(user: User) {
        editUser = editUser.copy(
            nomeCompleto = user.nomeCompleto,
            cpf = user.cpf,
            email = user.email,
            celular = user.celular,
            senha = user.senha,
            novaSenha = "",
            confirmarSenha = "",
            cep = user.cep,
            logradouro = user.logradouro,
            bairro = user.bairro,
            numero = user.numero,
            complemento = user.complemento,
            cidade = user.cidade
        )
    }

    fun updateUser(update: EditUser.() -> EditUser) {
        editUser = editUser.update()
    }
}

data class EditUser(
    val nomeCompleto: String = "",
    val cpf: String = "",
    val email: String = "",
    val celular: String = "",
    val senha: String = "",
    val novaSenha: String = "",
    val confirmarSenha: String = "",
    val cep: String = "",
    val logradouro: String = "",
    val bairro: String = "",
    val numero: String = "",
    val complemento: String = "",
    val cidade: String = ""
) {
    fun isEmpty() = nomeCompleto.isEmpty() && email.isEmpty()
}
