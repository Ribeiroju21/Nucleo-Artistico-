package com.nfaguai.nucleodeformacaoartistica.ui.screens.home

import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import java.time.LocalDate

/**
 * Uma turma do jeito que a tela inicial precisa mostrar:
 * os dados da turma + quantos alunos estão matriculados nela.
 */
data class TurmaHome(
    val turma: Turma,
    val quantidadeAlunos: Int,
)

/**
 * Estado da tela inicial (Home).
 * Guarda tudo o que a tela precisa para se desenhar.
 * A tela só lê este objeto; quem o preenche é o HomeViewModel.
 */
data class HomeState(
    val carregando: Boolean = true,
    val nomeProfessor: String = "",
    val data: LocalDate = LocalDate.now(),
    val turmasDeHoje: List<TurmaHome> = emptyList(),
    val totalAlunosAtivos: Int = 0,
) {
    // Valor calculado a partir da lista: não precisa ser guardado separado
    val totalTurmasHoje: Int
        get() = turmasDeHoje.size
}