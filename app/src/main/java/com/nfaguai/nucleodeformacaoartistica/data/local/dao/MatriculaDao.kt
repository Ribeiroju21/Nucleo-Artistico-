package com.nfaguai.nucleodeformacaoartistica.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aluno
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Matricula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import kotlinx.coroutines.flow.Flow

@Dao
interface MatriculaDao {

    @Upsert
    suspend fun salvar(matricula: Matricula)

    @Upsert
    suspend fun salvarTodas(matriculas: List<Matricula>)

    @Query("SELECT * FROM matriculas WHERE aluno_id = :alunoId AND turma_id = :turmaId")
    suspend fun buscarPorAlunoETurma(alunoId: String, turmaId: String): Matricula?

    @Query(
        """
        SELECT a.* FROM alunos a
        INNER JOIN matriculas m ON m.aluno_id = a.id
        WHERE m.turma_id = :turmaId AND m.ativo = 1 AND a.ativo = 1
        ORDER BY a.nome
        """
    )
    fun alunosDaTurma(turmaId: String): Flow<List<Aluno>>

    @Query(
        """
        SELECT t.* FROM turmas t
        INNER JOIN matriculas m ON m.turma_id = t.id
        WHERE m.aluno_id = :alunoId AND m.ativo = 1 AND t.ativo = 1
        ORDER BY t.dia_semana, t.hora_inicio
        """
    )
    fun turmasDoAluno(alunoId: String): Flow<List<Turma>>

    @Query("SELECT COUNT(*) FROM matriculas WHERE turma_id = :turmaId AND ativo = 1")
    fun contarAlunosDaTurma(turmaId: String): Flow<Int>
}