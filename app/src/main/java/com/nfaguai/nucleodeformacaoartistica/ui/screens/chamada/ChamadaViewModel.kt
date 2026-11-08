package com.nfaguai.nucleodeformacaoartistica.ui.chamada

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nfaguai.nucleodeformacaoartistica.NucleoApp
import com.nfaguai.nucleodeformacaoartistica.data.repository.ChamadaRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.MatriculaRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.TurmaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChamadaViewModel(
    private val turmaId: String,
    private val usuarioId: String,
    private val turmaRepository: TurmaRepository,
    private val matriculaRepository: MatriculaRepository,
    private val chamadaRepository: ChamadaRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ChamadaState())
    val state: StateFlow<ChamadaState> = _state.asStateFlow()

    init {
        carregar()
    }

    private fun carregar() {
        viewModelScope.launch {
            val data = _state.value.data.toString()

            val turma = turmaRepository.buscarPorId(turmaId)
            val alunos = matriculaRepository.alunosDaTurma(turmaId).first()
            val registradas = chamadaRepository.presencasDoDia(turmaId, data)

            _state.update {
                it.copy(
                    carregando = false,
                    turma = turma,
                    chamadaJaRegistrada = registradas.isNotEmpty(),
                    alunos = alunos.map { aluno ->
                        AlunoChamada(
                            id = aluno.id,
                            nome = aluno.nome,
                            presente = registradas[aluno.id] ?: false,
                        )
                    },
                )
            }
        }
    }

    fun alternarPresenca(alunoId: String) {
        _state.update { s ->
            s.copy(
                alunos = s.alunos.map { aluno ->
                    if (aluno.id == alunoId) {
                        aluno.copy(presente = !aluno.presente)
                    } else {
                        aluno
                    }
                }
            )
        }
    }

    fun alterarBusca(texto: String) {
        _state.update { it.copy(busca = texto) }
    }

    fun salvar() {
        val atual = _state.value
        if (atual.salvando || atual.alunos.isEmpty()) {
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(salvando = true) }
            try {
                chamadaRepository.abrirERegistrar(
                    turmaId = turmaId,
                    data = atual.data.toString(),
                    usuarioId = usuarioId,
                    statusPorAluno = atual.alunos.associate { it.id to it.presente },
                )
                _state.update {
                    it.copy(salvando = false, chamadaJaRegistrada = true, mensagem = "Chamada salva")
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(salvando = false, mensagem = "Erro ao salvar chamada")
                }
            }
        }
    }

    fun mensagemExibida() {
        _state.update { it.copy(mensagem = null) }
    }

    companion object {
        fun factory(turmaId: String, usuarioId: String) = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NucleoApp
                ChamadaViewModel(
                    turmaId = turmaId,
                    usuarioId = usuarioId,
                    turmaRepository = app.turmaRepository,
                    matriculaRepository = app.matriculaRepository,
                    chamadaRepository = app.chamadaRepository,
                )
            }
        }
    }
}