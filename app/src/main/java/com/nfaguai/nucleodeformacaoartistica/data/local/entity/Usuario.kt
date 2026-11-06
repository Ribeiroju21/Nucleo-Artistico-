package com.nfaguai.nucleodeformacaoartistica.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "usuarios",
    primaryKeys = ["id"]
)
data class Usuario(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val email: String,
    val senha: String,
    val funcao: String,
    val ativo: Boolean = true
)
