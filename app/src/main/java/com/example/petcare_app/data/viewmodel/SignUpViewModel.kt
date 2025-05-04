package com.example.petcare_app.data.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.PetCreateDTO
import com.example.petcare_app.data.dto.SpecieDTO
import com.example.petcare_app.data.dto.UserCreateDTO
import com.example.petcare_app.data.model.Race
import com.example.petcare_app.data.model.Size
import com.example.petcare_app.data.model.Specie
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.SpecieRepository
import com.example.petcare_app.data.repository.UserRepository
import com.example.petcare_app.data.services.PetService
import com.example.petcare_app.data.services.RaceService
import com.example.petcare_app.data.services.SizeService
import com.example.petcare_app.data.services.SpecieService
import com.example.petcare_app.data.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class SignUpViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets
    private val _species = MutableStateFlow<List<Specie>>(emptyList())
    val species: StateFlow<List<Specie>> = _species.asStateFlow()
    private val _races = MutableStateFlow<List<Race>>(emptyList())
    val races: StateFlow<List<Race>> = _races.asStateFlow()
    private val _sizes = MutableStateFlow<List<Size>>(emptyList())
    val sizes: StateFlow<List<Size>> = _sizes.asStateFlow()

    private val _user = mutableStateOf(User())
    val user: State<User> = _user

    fun signUpUserAndPet(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val userApi = RetrofitInstance.retrofit.create(UserService::class.java)

        viewModelScope.launch {
            isLoading = true
            try {
                val userData = _user.value
                val dto = UserCreateDTO(
                    name = userData.nomeCompleto,
                    email = userData.email,
                    password = userData.senha,
                    cellphone = userData.celular,
                    role = "ROLE_CUSTOMER",
                    street = userData.logradouro,
                    number = userData.numero.toIntOrNull() ?: 0,
                    complement = userData.complemento,
                    cep = userData.cep,
                    district = userData.bairro,
                    city = userData.cidade,
                    cpfClient = userData.cpf,
                    petIds = null
                )

                Log.d("FORM_SIGNUP - VIEW MODEL", "Dados: " +
                        " ${dto}")

                val response = userApi.createUser(dto)
                if (response.isSuccessful) {
                    val userId = response.body()?.id
                    if (userId != null) {
                        _pets.value = _pets.value.map { it.copy(idUser = userId) }
                        signUpPet()
                        onSuccess()
                    }
                } else {
                    onError("Erro ao criar usuário e pet: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro: ${e.message}")
            }
            isLoading = false
        }
    }

    fun signUpUser(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val userApi = RetrofitInstance.retrofit.create(UserService::class.java)

        viewModelScope.launch {
            isLoading = true
            try {
                val userData = _user.value
                val dto = UserCreateDTO(
                    name = userData.nomeCompleto,
                    email = userData.email,
                    password = userData.senha,
                    cellphone = userData.celular,
                    role = "ROLE_CUSTOMER",
                    street = userData.logradouro,
                    number = userData.numero.toIntOrNull() ?: 0,
                    complement = userData.complemento,
                    cep = userData.cep,
                    district = userData.bairro,
                    city = userData.cidade,
                    cpfClient = userData.cpf,
                    petIds = null
                )

                Log.d("FORM_SIGNUP - VIEW MODEL", "Dados: " +
                        " ${dto}")

                val userResponse = userApi.createUser(dto)
                if (userResponse.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Erro ao criar usuário: ${userResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro: ${e.message}")
            }
            isLoading = false
        }
    }

    fun updateUser(update: User.() -> User) {
        _user.value = _user.value.update()
    }

    // Informações Pets
    fun getSpecies() {
        val speciesApi = RetrofitInstance.retrofit.create(SpecieService::class.java)

        viewModelScope.launch {
            isLoading = true
            try {
                val speciesResponse = speciesApi.getSpecies()
                if (speciesResponse.isSuccessful) {
                    _species.value = speciesResponse.body() ?: emptyList()
//                    Log.d("FORM_SIGNUP", "ESPECIES: " +
//                            " ${speciesResponse.body()}")
                } else {
                    Log.d("FORM_SIGNUP", "Erro body: ${speciesResponse.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro: ${e.message}")
            }
            isLoading = false

        }
    }

    fun getRaces() {
        val racesApi = RetrofitInstance.retrofit.create(RaceService::class.java)

        viewModelScope.launch {
            isLoading = true
            try {
                val racesResponse = racesApi.getRaces()
                if (racesResponse.isSuccessful) {
                    _races.value = racesResponse.body() ?: emptyList()
//                    Log.d("FORM_SIGNUP", "RAÇAS: " +
//                            " ${racesResponse.body()}")
                } else {
                    Log.d("FORM_SIGNUP", "Erro body: ${racesResponse.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro: ${e.message}")
            }
            isLoading = false

        }
    }

    fun getSizes() {
        val sizesApi = RetrofitInstance.retrofit.create(SizeService::class.java)

        viewModelScope.launch {
            isLoading = true
            try {
                val sizesResponse = sizesApi.getSizes()
                if (sizesResponse.isSuccessful) {
                    _sizes.value = sizesResponse.body() ?: emptyList()
                } else {
                    Log.d("FORM_SIGNUP", "Erro body: ${sizesResponse.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro: ${e.message}")
            }
            isLoading = false
        }
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

    @SuppressLint("NewApi")
    fun signUpPet(
    ) {
        val petApi = RetrofitInstance.retrofit.create(PetService::class.java)

        viewModelScope.launch {
            val petsData = _pets.value

            petsData.forEach { pet ->
                try {
                    val dataNascimento = pet.dataNascimento
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val parsedData: LocalDate = LocalDate.parse(dataNascimento, formatter)
                    val birthdate = parsedData.toString()

                    val petDto = PetCreateDTO(
                        name = pet.nome,
                        gender = pet.sexo,
                        color = pet.cor,
                        estimatedWeight = pet.peso.toDoubleOrNull() ?: 0.0,
                        birthdate = birthdate,
                        petObservations = pet.observacoes,
                        petImg = null,
                        planId = null,
                        specieId = pet.especie ?: 0,
                        raceId = pet.raca ?: 0,
                        sizeId = pet.porte ?: 0,
                        userId = pet.idUser ?: 0
                    )

                    val response = petApi.createPet(petDto)
                    if (!response.isSuccessful) {
                        Log.e("SIGNUP_PET", "Erro ao cadastrar pet ${pet.nome}: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("SIGNUP_PET", "Erro ao cadastrar pet: ${e.message}")
                }
            }
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
    var especie: Int? = null,
    var raca: Int? = null,
    var peso: String = "",
    var dataNascimento: String = "",
    var cor: String = "",
    var porte: Int? = null,
    var sexo: String = "",
    var observacoes: String = "",
    var idUser: Int? = null
)
