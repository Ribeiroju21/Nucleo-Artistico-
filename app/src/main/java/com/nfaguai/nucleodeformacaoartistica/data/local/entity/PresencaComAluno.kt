package com.nfaguai.nucleodeformacaoartistica.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class PresencaComAluno(
    @Embedded val presenca: Presenca,
    @ColumnInfo(name = "aluno_nome") val alunoNome: String
)