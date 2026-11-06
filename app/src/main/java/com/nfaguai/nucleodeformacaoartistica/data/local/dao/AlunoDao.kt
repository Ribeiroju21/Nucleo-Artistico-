package com.nfaguai.nucleodeformacaoartistica.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aluno
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    @Upsert
    suspend fun salvar(aluno: Aluno)

    @Upsert
    suspend fun salvarTodos(alunos: List<Aluno>)

    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun buscarPorId(id: String): Aluno?

    @Query("SELECT * FROM alunos WHERE ativo = 1 ORDER BY nome")
    fun listarAtivos(): Flow<List<Aluno>>

    @Query("SELECT * FROM alunos ORDER BY nome")
    fun listarTodos(): Flow<List<Aluno>>

    @Query("SELECT * FROM alunos WHERE nome LIKE '%' || :busca || '%' ORDER BY nome")
    fun buscarPorNome(busca: String): Flow<List<Aluno>>
}