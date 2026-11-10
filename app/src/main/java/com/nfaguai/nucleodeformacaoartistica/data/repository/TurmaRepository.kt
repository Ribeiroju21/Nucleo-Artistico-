package com.nfaguai.nucleodeformacaoartistica.data.repository

import com.nfaguai.nucleodeformacaoartistica.data.local.dao.TurmaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.DiasSemana
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TurmaRepository(private val dao: TurmaDao) {

    val turmasAtivas: Flow<List<Turma>> = dao.listarAtivas()

    fun turmasDoProfessor(professorId: String): Flow<List<Turma>> =
        dao.listarPorProfessor(professorId)

    fun turmasDeHoje(professorId: String): Flow<List<Turma>> =
        dao.listarDoProfessorNoDia(professorId, DiasSemana.hoje())

    suspend fun salvar(turma: Turma) = dao.salvar(turma)
    suspend fun buscarPorId(id: String) = dao.buscarPorId(id)

    suspend fun encerrar(turma: Turma) = dao.salvar(turma.copy(ativo = false))
}