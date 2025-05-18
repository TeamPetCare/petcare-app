package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.Specie
import com.example.petcare_app.data.services.SpecieService
import retrofit2.Response

interface SpecieRepository {
    val api : SpecieService
    suspend fun getSpecies() : Response<List<Specie>> {
        return api.getSpecies()
    }
}