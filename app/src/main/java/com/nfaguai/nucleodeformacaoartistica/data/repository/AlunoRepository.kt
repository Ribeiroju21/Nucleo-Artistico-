package com.nfaguai.nucleodeformacaoartistica.data.repository

import com.nfaguai.nucleodeformacaoartistica.data.local.dao.AlunoDao
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aluno
import kotlinx.coroutines.flow.Flow

class AlunoRepository(private val dao: AlunoDao) {

    val alunosAtivos: Flow<List<Aluno>> = dao.listarAtivos()
    val todosAlunos: Flow<List<Aluno>> = dao.listarTodos()

    fun buscarPorNome(busca: String): Flow<List<Aluno>> = dao.buscarPorNome(busca)

    suspend fun salvar(aluno: Aluno) = dao.salvar(aluno)
    suspend fun buscarPorId(id: String) = dao.buscarPorId(id)

    suspend fun desativar(aluno: Aluno) = dao.salvar(aluno.copy(ativo = false))
}