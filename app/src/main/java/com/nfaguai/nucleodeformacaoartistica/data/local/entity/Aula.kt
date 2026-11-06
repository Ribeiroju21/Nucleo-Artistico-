package com.nfaguai.nucleodeformacaoartistica.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID
@Entity(
    tableName = "aulas",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = Turma::class,
            parentColumns = ["id"],
            childColumns = ["turma_id"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["turma_id", "data"], unique = true),
        Index(value = ["usuario_id"])
    ]
)
data class Aula(
    val id: String = UUID.randomUUID().toString(),
    val data: String,
    @ColumnInfo(name = "turma_id") val turmaId: String,
    @ColumnInfo(name = "usuario_id") val usuarioId: String
)