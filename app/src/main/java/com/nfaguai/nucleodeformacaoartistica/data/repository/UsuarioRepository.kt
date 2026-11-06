package com.nfaguai.nucleodeformacaoartistica.data.repository

import com.nfaguai.nucleodeformacaoartistica.data.local.dao.UsuarioDao
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Usuario
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(private val dao: UsuarioDao) {

    val usuariosAtivos: Flow<List<Usuario>> = dao.listarAtivos()
    val professoresAtivos: Flow<List<Usuario>> = dao.listarProfessoresAtivos()

    suspend fun salvar(usuario: Usuario) = dao.salvar(usuario)
    suspend fun buscarPorId(id: String) = dao.buscarPorId(id)

    suspend fun desativar(usuario: Usuario) = dao.salvar(usuario.copy(ativo = false))
}