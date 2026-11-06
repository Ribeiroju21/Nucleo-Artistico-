package com.nfaguai.nucleodeformacaoartistica.data.repository

import com.nfaguai.nucleodeformacaoartistica.data.local.dao.MatriculaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aluno
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Matricula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import kotlinx.coroutines.flow.Flow

class MatriculaRepository(private val dao: MatriculaDao) {

    fun alunosDaTurma(turmaId: String): Flow<List<Aluno>> = dao.alunosDaTurma(turmaId)
    fun turmasDoAluno(alunoId: String): Flow<List<Turma>> = dao.turmasDoAluno(alunoId)
    fun contarAlunos(turmaId: String): Flow<Int> = dao.contarAlunosDaTurma(turmaId)

    suspend fun matricular(alunoId: String, turmaId: String) {
        val existente = dao.buscarPorAlunoETurma(alunoId, turmaId)
        if (existente == null) {
            dao.salvar(Matricula(alunoId = alunoId, turmaId = turmaId))
        } else if (!existente.ativo) {
            dao.salvar(existente.copy(ativo = true))
        }
    }

    suspend fun desmatricular(alunoId: String, turmaId: String) {
        val existente = dao.buscarPorAlunoETurma(alunoId, turmaId) ?: return
        dao.salvar(existente.copy(ativo = false))
    }
}