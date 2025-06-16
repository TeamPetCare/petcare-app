package com.example.petcare_app.data.model

enum class ScheduleStatus(val status: String, val displayName: String) {
    AGENDADO("AGENDADO", "Agendado"),
    CANCELADO("CANCELADO", "Cancelado"),
    CONCLUIDO("CONCLUIDO", "Concluído");
    
    companion object {
        fun fromString(status: String): ScheduleStatus {
            return values().find { it.status == status } ?: AGENDADO
        }
    }
} 