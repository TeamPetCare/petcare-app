package com.example.petcare_app.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.services.UserService
import kotlinx.coroutines.launch

class EditUserViewModel : ViewModel() {
    var editUser by mutableStateOf(EditUser())

    fun updateEditUser(update: EditUser.() -> EditUser) {
        editUser = editUser.update()
    }

    fun getUserById(token: String,id: Int){
        val userApi = RetrofitInstance.retrofit.create(UserService::class.java)
        viewModelScope.launch {
            try {
                val userResponse = userApi.getUserById(
                    token,
                    id)
                if(userResponse.isSuccessful){
                    editUser = editUser.copy(
                        nomeCompleto = userResponse.body()?.name ?: editUser.nomeCompleto,
                        cpf = userResponse.body()?.cpfClient ?: editUser.cpf,
                        email = userResponse.body()?.email ?: editUser.email,
                        celular = userResponse.body()?.cellphone ?: editUser.celular,
//                        senha = userResponse.body()?.password ?: editUser.senha,
                        senha = "Senha mockada",
                        novaSenha = "",
                        confirmarSenha = "",
                        cep = userResponse.body()?.cep ?: editUser.cep,
                        logradouro = userResponse.body()?.street ?: editUser.logradouro,
                        bairro = userResponse.body()?.district ?: editUser.bairro,
                        numero = (userResponse.body()?.number ?: editUser.numero).toString(),
                        complemento = userResponse.body()?.complement ?: editUser.complemento,
                        cidade = userResponse.body()?.city ?: editUser.cidade
                    )
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro: ${e.message}")
            }
        }
    }
}

data class EditUser(
    var nomeCompleto: String = "",
    var cpf: String = "",
    var email: String = "",
    var celular: String = "",
    var senha: String = "",
    var novaSenha: String = "",
    var confirmarSenha: String = "",
    var cep: String = "",
    var logradouro: String = "",
    var bairro: String = "",
    var numero: String = "",
    var complemento: String = "",
    var cidade: String = ""
) {
    fun isEmpty() = nomeCompleto.isEmpty() && email.isEmpty()
}
