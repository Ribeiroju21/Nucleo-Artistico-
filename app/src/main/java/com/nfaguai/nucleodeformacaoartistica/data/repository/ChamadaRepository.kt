package com.nfaguai.nucleodeformacaoartistica.data.repository

import androidx.room.withTransaction
import com.nfaguai.nucleodeformacaoartistica.data.local.AppDatabase
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.AulaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.PresencaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Presenca

class ChamadaRepository(
    private val db: AppDatabase,
    private val aulaDao: AulaDao,
    private val presencaDao: PresencaDao
) {

    fun aulasDaTurma(turmaId: String) = aulaDao.listarPorTurma(turmaId)

    fun aulaComPresencas(aulaId: String) = aulaDao.observarComPresencas(aulaId)

    fun presencasDaAula(aulaId: String) = presencaDao.observarPorAula(aulaId)

    suspend fun presencasDoDia(turmaId: String, data: String): Map<String, Boolean> {
        val aula = aulaDao.buscarPorTurmaEData(turmaId, data) ?: return emptyMap()
        return presencaDao.listarDaAula(aula.id).associate { it.alunoId to it.presente }
    }

    suspend fun obterOuCriarAula(turmaId: String, data: String, usuarioId: String): Aula {
        val existente = aulaDao.buscarPorTurmaEData(turmaId, data)
        if (existente != null) return existente

        val nova = Aula(data = data, turmaId = turmaId, usuarioId = usuarioId)
        aulaDao.inserir(nova)

        return aulaDao.buscarPorTurmaEData(turmaId, data) ?: nova
    }

    suspend fun registrarChamada(presencas: List<Presenca>) {
        presencaDao.salvarTodas(presencas)
    }

    suspend fun abrirERegistrar(
        turmaId: String,
        data: String,
        usuarioId: String,
        statusPorAluno: Map<String, Boolean>
    ) {
        db.withTransaction {
            val aula = obterOuCriarAula(turmaId, data, usuarioId)
            val existentes = presencaDao.listarDaAula(aula.id).associateBy { it.alunoId }

            val lista = statusPorAluno.map { (alunoId, presente) ->
                existentes[alunoId]?.copy(presente = presente)
                    ?: Presenca(aulaId = aula.id, alunoId = alunoId, presente = presente)
            }

            presencaDao.salvarTodas(lista)
        }
    }

    suspend fun totalFaltas(alunoId: String) =
        presencaDao.contarPorStatus(alunoId, false)

    suspend fun totalPresencas(alunoId: String) =
        presencaDao.contarPorStatus(alunoId, true)
}