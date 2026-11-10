package com.nfaguai.nucleodeformacaoartistica.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nfaguai.nucleodeformacaoartistica.NucleoApp
import com.nfaguai.nucleodeformacaoartistica.data.repository.MatriculaRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.TurmaRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel da tela inicial.
 * Busca os dados nos repositórios e preenche o HomeState.
 */
class HomeViewModel(
    private val usuarioId: String,
    private val usuarioRepository: UsuarioRepository,
    private val turmaRepository: TurmaRepository,
    private val matriculaRepository: MatriculaRepository,
) : ViewModel() {

    // Versão interna (pode ser alterada) e versão pública (só leitura) do estado
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        carregar()
    }

    private fun carregar() {
        viewModelScope.launch {
            // 1) Nome da professora logada
            val usuario = usuarioRepository.buscarPorId(usuarioId)
            val nome = usuario?.nome.orEmpty()

            // 2) Turmas de hoje, cada uma com sua quantidade de alunos
            val turmasHoje = turmaRepository.turmasDeHoje(usuarioId).first()

            val turmasHome = mutableListOf<TurmaHome>()
            for (turma in turmasHoje) {
                val quantidade = matriculaRepository.contarAlunos(turma.id).first()
                turmasHome.add(TurmaHome(turma = turma, quantidadeAlunos = quantidade))
            }

            // 3) Alunos ativos: alunos distintos matriculados em qualquer turma da professora.
            //    Usamos um conjunto (Set) para que um aluno em duas turmas conte uma vez só.
            val todasAsTurmas = turmaRepository.turmasDoProfessor(usuarioId).first()

            val idsDosAlunos = mutableSetOf<String>()
            for (turma in todasAsTurmas) {
                val alunos = matriculaRepository.alunosDaTurma(turma.id).first()
                for (aluno in alunos) {
                    idsDosAlunos.add(aluno.id)
                }
            }

            // 4) Publica o novo estado: a tela se redesenha sozinha
            _state.update {
                it.copy(
                    carregando = false,
                    nomeProfessor = nome,
                    turmasDeHoje = turmasHome,
                    totalAlunosAtivos = idsDosAlunos.size,
                )
            }
        }
    }

    companion object {
        /**
         * Ensina o Android a construir este ViewModel entregando os repositórios
         * que já existem no NucleoApp. Mesmo padrão do ChamadaViewModel.
         */
        fun factory(usuarioId: String) = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NucleoApp
                HomeViewModel(
                    usuarioId = usuarioId,
                    usuarioRepository = app.usuarioRepository,
                    turmaRepository = app.turmaRepository,
                    matriculaRepository = app.matriculaRepository,
                )
            }
        }
    }
}