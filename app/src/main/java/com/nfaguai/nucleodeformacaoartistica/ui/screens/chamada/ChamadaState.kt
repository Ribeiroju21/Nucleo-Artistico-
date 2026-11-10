package com.nfaguai.nucleodeformacaoartistica.ui.chamada

import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import java.time.LocalDate

data class AlunoChamada(
    val id: String,
    val nome: String,
    val presente: Boolean,
)

data class ChamadaState(
    val carregando: Boolean = true,
    val turma: Turma? = null,
    val data: LocalDate = LocalDate.now(),
    val alunos: List<AlunoChamada> = emptyList(),
    val busca: String = "",
    val chamadaJaRegistrada: Boolean = false,
    val salvando: Boolean = false,
    val mensagem: String? = null,
) {
    val presentes: Int get() = alunos.count { it.presente }
    val faltas: Int get() = alunos.size - presentes
    val total: Int get() = alunos.size

    val alunosFiltrados: List<AlunoChamada>
        get() = if (busca.isBlank()) {
            alunos
        } else {
            alunos.filter { it.nome.contains(busca.trim(), ignoreCase = true) }
        }
}