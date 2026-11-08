package com.nfaguai.nucleodeformacaoartistica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nfaguai.nucleodeformacaoartistica.ui.screens.chamada.ChamadaRoute
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoDeFormacaoArtistica2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NucleoDeFormacaoArtistica2Theme {
                ChamadaRoute(
                    turmaId = "tur-violao",
                    usuarioId = "prof-cris",
                    onVoltar = { finish() },
                    onSelecionarAba = {},
                )
            }
        }
    }
}