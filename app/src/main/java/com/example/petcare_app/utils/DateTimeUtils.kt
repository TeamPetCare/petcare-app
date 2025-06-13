package com.example.petcare_app.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateTimeUtils {
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToISODateTime(date: String, time: String): String? {
        return try {
            // Converter de "dd/mm/yyyy" e "HH:mm" para "yyyy-MM-ddTHH:mm:ss"
            val dateParts = date.split("/")
            val timeParts = time.split(":")
            
            if (dateParts.size == 3 && timeParts.size == 2) {
                val day = dateParts[0].padStart(2, '0')
                val month = dateParts[1].padStart(2, '0')
                val year = dateParts[2]
                val hour = timeParts[0].padStart(2, '0')
                val minute = timeParts[1].padStart(2, '0')
                
                "$year-$month-${day}T$hour:$minute:00"
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentISODateTime(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertFromISOToDisplay(isoDateTime: String): Pair<String, String>? {
        return try {
            // Converter de "yyyy-MM-ddTHH:mm:ss" para "dd/mm/yyyy" e "HH:mm"
            val dateTime = LocalDateTime.parse(isoDateTime)
            val date = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            Pair(date, time)
        } catch (e: DateTimeParseException) {
            null
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun getEstimatedDuration(services: List<String>): String {
        // Calcular duração baseada nos serviços (exemplo: 1 hora por serviço)
        val totalMinutes = services.size * 60
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return String.format("%02d:%02d:00", hours, minutes)
    }
    
    fun validateDate(date: String): Boolean {
        if (date.length != 10) return false
        val regex = Regex("^\\d{2}/\\d{2}/\\d{4}$")
        if (!regex.matches(date)) return false
        
        val parts = date.split("/")
        val day = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val year = parts[2].toIntOrNull() ?: return false
        
        return day in 1..31 && month in 1..12 && year >= 2024
    }
    
    fun validateTime(time: String): Boolean {
        if (time.length != 5) return false
        val regex = Regex("^\\d{2}:\\d{2}$")
        if (!regex.matches(time)) return false
        
        val parts = time.split(":")
        val hour = parts[0].toIntOrNull() ?: return false
        val minute = parts[1].toIntOrNull() ?: return false
        
        return hour in 0..23 && minute in 0..59
    }
} 