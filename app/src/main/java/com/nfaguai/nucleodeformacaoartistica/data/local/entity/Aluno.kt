package com.nfaguai.nucleodeformacaoartistica.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.UUID

@Entity(
    tableName = "alunos",
    primaryKeys = ["id"]
)
data class Aluno(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    @ColumnInfo(name = "data_nascimento") val dataNascimento: String? = null,
    val ativo: Boolean = true
)