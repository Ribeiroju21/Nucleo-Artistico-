package com.nfaguai.nucleodeformacaoartistica.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Presenca
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.PresencaComAluno
import kotlinx.coroutines.flow.Flow

@Dao
interface PresencaDao {

    @Upsert
    suspend fun salvar(presenca: Presenca)

    @Upsert
    suspend fun salvarTodas(presencas: List<Presenca>)

    @Query("SELECT * FROM presencas WHERE aula_id = :aulaId AND aluno_id = :alunoId")
    suspend fun buscar(aulaId: String, alunoId: String): Presenca?

    @Query("SELECT * FROM presencas WHERE aula_id = :aulaId")
    suspend fun listarDaAula(aulaId: String): List<Presenca>

    @Query(
        """
        SELECT p.*, a.nome AS aluno_nome FROM presencas p
        INNER JOIN alunos a ON a.id = p.aluno_id
        WHERE p.aula_id = :aulaId
        ORDER BY a.nome
        """
    )
    fun observarPorAula(aulaId: String): Flow<List<PresencaComAluno>>

    @Query("SELECT * FROM presencas WHERE aluno_id = :alunoId")
    fun listarDoAluno(alunoId: String): Flow<List<Presenca>>

    @Query("SELECT COUNT(*) FROM presencas WHERE aluno_id = :alunoId AND presente = :presente")
    suspend fun contarPorStatus(alunoId: String, presente: Boolean): Int
}