package com.nfaguai.nucleodeformacaoartistica.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.UUID

@Entity(
    tableName = "matriculas",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = Aluno::class,
            parentColumns = ["id"],
            childColumns = ["aluno_id"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Turma::class,
            parentColumns = ["id"],
            childColumns = ["turma_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["aluno_id", "turma_id"], unique = true),
        Index(value = ["turma_id"])
    ]
)
data class Matricula(
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "aluno_id") val alunoId: String,
    @ColumnInfo(name = "turma_id") val turmaId: String,
    val ativo: Boolean = true
)