package com.nfaguai.nucleodeformacaoartistica.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.AulaComPresencas
import kotlinx.coroutines.flow.Flow

@Dao
interface AulaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserir(aula: Aula): Long

    @Query("SELECT * FROM aulas WHERE id = :id")
    suspend fun buscarPorId(id: String): Aula?

    @Query("SELECT * FROM aulas WHERE turma_id = :turmaId AND data = :data")
    suspend fun buscarPorTurmaEData(turmaId: String, data: String): Aula?

    @Query("SELECT * FROM aulas WHERE turma_id = :turmaId ORDER BY data DESC")
    fun listarPorTurma(turmaId: String): Flow<List<Aula>>

    @Query("SELECT * FROM aulas WHERE usuario_id = :usuarioId ORDER BY data DESC")
    fun listarPorUsuario(usuarioId: String): Flow<List<Aula>>

    @Transaction
    @Query("SELECT * FROM aulas WHERE id = :aulaId")
    fun observarComPresencas(aulaId: String): Flow<AulaComPresencas?>
}