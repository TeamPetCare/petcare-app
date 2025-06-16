package com.example.petcare_app.data.viewmodel

import TokenDataStore
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.UserCreateDTO
import com.example.petcare_app.data.dto.UserUpdateDTO
import com.example.petcare_app.data.model.User
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.ImageRepository
import com.example.petcare_app.data.repository.UserRepository
import com.example.petcare_app.data.services.UserService
import com.example.petcare_app.ui.components.layouts.createMultipartFromUri
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class EditUserViewModel(
    private val userRepository: UserRepository,
    private val tokenDataStore: TokenDataStore,
    private val imageRepository: ImageRepository
) : ViewModel() {
    var editUser by mutableStateOf(EditUser())

    fun updateEditUser(update: EditUser.() -> EditUser) {
        editUser = editUser.update()
    }

    fun getUserById(token: String,id: Int){
        viewModelScope.launch {
            try {
                val userResponse = userRepository.getUserById(
                    token,
                    id)
                if(userResponse.isSuccessful){
                    editUser = editUser.copy(
                        nomeCompleto = userResponse.body()?.name ?: editUser.nomeCompleto,
                        userImg = userResponse.body()?.userImg ?: editUser.userImg,
                        cpf = userResponse.body()?.cpfClient ?: editUser.cpf,
                        email = userResponse.body()?.email ?: editUser.email,
                        celular = userResponse.body()?.cellphone ?: editUser.celular,
                        senha = userResponse.body()?.password ?: editUser.senha,
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

    fun updateUser(token: String,id: Int){
        val dto = UserUpdateDTO(
            name = editUser.nomeCompleto,
            userImg = editUser.userImg,
            password = editUser.confirmarSenha,
            email = editUser.email,
            cellphone = editUser.celular,
            role = "ROLE_CUSTOMER",
            street = editUser.logradouro,
            number = editUser.numero.toIntOrNull() ?: 0,
            complement = editUser.complemento,
            cep = editUser.cep,
            district = editUser.bairro,
            city = editUser.cidade,
            cpfClient = editUser.cpf
        )

        viewModelScope.launch {
            try {
                val userResponse = userRepository.updateUser(
                    token,
                    dto,
                    id)
                if(!userResponse.isSuccessful){
                    Log.e("Erro ao dar update em usuário", "Erro: ${userResponse.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro: ${e.message}")
            }
        }
    }



    fun uploadUserImage(context: Context, uri: Uri) {
        val imagePart = createMultipartFromUri(context, uri)

        viewModelScope.launch {
            val userId = tokenDataStore.getId.first()
            if (userId != null) {
                try {
                    val response = imageRepository.uploadImage(imagePart, userId = userId, isUser = true)
                    if(response.isSuccessful){
                    Toast.makeText(context, "Imagem enviada com sucesso", Toast.LENGTH_SHORT).show()
                    }else{
                        Log.e("ImageUpload", "Erro ao enviar imagem: $response.errorBody(")
                        Toast.makeText(context, "Erro ao enviar imagem", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("ImageUpload", "Erro ao enviar imagem", e)
                    Toast.makeText(context, "Erro ao enviar imagem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "ID do usuário não encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }




}

data class EditUser(
    var nomeCompleto: String = "",
    val userImg: String? = null,
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
