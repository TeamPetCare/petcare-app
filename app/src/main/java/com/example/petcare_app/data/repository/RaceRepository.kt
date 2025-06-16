package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.Race
import com.example.petcare_app.data.services.RaceService
import retrofit2.Response

class RaceRepository(
    private val api : RaceService
) {
    suspend fun getRaces() : Response<List<Race>> {
        return api.getRaces()
    }
}