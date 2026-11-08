package com.nfaguai.nucleodeformacaoartistica.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import kotlinx.coroutines.flow.Flow

@Dao
interface TurmaDao {

    @Upsert
    suspend fun salvar(turma: Turma)

    @Upsert
    suspend fun salvarTodas(turmas: List<Turma>)

    @Query("SELECT * FROM turmas WHERE id = :id")
    suspend fun buscarPorId(id: String): Turma?

    @Query("SELECT * FROM turmas WHERE ativo = 1 ORDER BY (dias_semana & -dias_semana), hora_inicio")
    fun listarAtivas(): Flow<List<Turma>>

    @Query("SELECT * FROM turmas ORDER BY (dias_semana & -dias_semana), hora_inicio")
    fun listarTodas(): Flow<List<Turma>>

    @Query(
        """
        SELECT * FROM turmas
        WHERE professor_id = :professorId AND ativo = 1
        ORDER BY (dias_semana & -dias_semana), hora_inicio
        """
    )
    fun listarPorProfessor(professorId: String): Flow<List<Turma>>

    @Query("SELECT * FROM turmas WHERE (dias_semana & :dia) != 0 AND ativo = 1 ORDER BY hora_inicio")
    fun listarPorDia(dia: Int): Flow<List<Turma>>

    @Query(
        """
        SELECT * FROM turmas
        WHERE professor_id = :professorId
          AND (dias_semana & :dia) != 0
          AND ativo = 1
        ORDER BY hora_inicio
        """
    )
    fun listarDoProfessorNoDia(professorId: String, dia: Int): Flow<List<Turma>>
}