package com.nfaguai.nucleodeformacaoartistica.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Upsert
    suspend fun salvar(usuario: Usuario)

    @Upsert
    suspend fun salvarTodos(usuarios: List<Usuario>)

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun buscarPorId(id: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE ativo = 1 ORDER BY nome")
    fun listarAtivos(): Flow<List<Usuario>>

    @Query("SELECT * FROM usuarios WHERE funcao = 'professor' AND ativo = 1 ORDER BY nome")
    fun listarProfessoresAtivos(): Flow<List<Usuario>>
}