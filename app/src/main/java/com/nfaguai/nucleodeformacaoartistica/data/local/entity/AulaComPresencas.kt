package com.nfaguai.nucleodeformacaoartistica.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AulaComPresencas(
    @Embedded val aula: Aula,
    @Relation(
        parentColumn = "id",
        entityColumn = "aula_id"
    )
    val presencas: List<Presenca>
)