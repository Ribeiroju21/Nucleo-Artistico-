package com.nfaguai.nucleodeformacaoartistica.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.UUID

@Entity(
    tableName = "turmas",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["professor_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["professor_id"])
    ]
)
data class Turma(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    @ColumnInfo(name = "dia_semana") val diaSemana: Int,
    @ColumnInfo(name = "hora_inicio") val horaInicio: String,
    @ColumnInfo(name = "hora_fim") val horaFim: String,
    @ColumnInfo(name = "idade_minima") val idadeMinima: Int? = null,
    @ColumnInfo(name = "idade_maxima") val idadeMaxima: Int? = null,
    @ColumnInfo(name = "professor_id") val professorId: String,
    val ativo: Boolean = true
)