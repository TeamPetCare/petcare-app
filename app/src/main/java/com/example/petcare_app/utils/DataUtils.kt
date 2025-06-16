package com.example.petcare_app.utils

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DataUtils {
    @SuppressLint("NewApi")
    fun formatarDataHora(dataHora: LocalDateTime): String {
        val hoje = LocalDate.now()
        val dataAgendamento = dataHora.toLocalDate()

        val horaFormatada = if (dataHora.minute == 0)
            "${dataHora.hour}h"
        else
            "${dataHora.hour}h${dataHora.minute.toString().padStart(2, '0')}"

        return when (dataAgendamento) {
            hoje -> "Hoje às $horaFormatada"
            hoje.plusDays(1) -> "Amanhã às $horaFormatada"
            hoje.minusDays(1) -> "Ontem às $horaFormatada"
            else -> {
                val dataFormatada = dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                "$dataFormatada às $horaFormatada"
            }
        }
    }

    @SuppressLint("NewApi")
    fun calcularHorarioFinal(scheduleDate: String, scheduleTime: String): String {
        val inicio = LocalDateTime.parse(scheduleDate)
        val tempoExtra = LocalTime.parse(scheduleTime)

        val resultado = inicio
            .plusHours(tempoExtra.hour.toLong())
            .plusMinutes(tempoExtra.minute.toLong())

        val formatter = DateTimeFormatter.ofPattern("HH'h'mm")
        return resultado.format(formatter)
    }

}