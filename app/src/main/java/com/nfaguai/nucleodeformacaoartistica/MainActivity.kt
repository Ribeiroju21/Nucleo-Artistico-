package com.nfaguai.nucleodeformacaoartistica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nfaguai.nucleodeformacaoartistica.ui.screens.chamada.ChamadaRoute
import com.nfaguai.nucleodeformacaoartistica.ui.screens.home.HomeRoute
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoDeFormacaoArtistica2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NucleoDeFormacaoArtistica2Theme {
                val navController = rememberNavController()
                val usuarioId = "prof-cris"

                NavHost(navController = navController, startDestination = "home") {

                    composable("home") {
                        HomeRoute(
                            usuarioId = usuarioId,
                            onAbrirTurma = { turmaId ->
                                navController.navigate("chamada/$turmaId")
                            },
                            onSelecionarAba = {},
                        )
                    }

                    composable(
                        route = "chamada/{turmaId}",
                        arguments = listOf(navArgument("turmaId") { type = NavType.StringType }),
                    ) { backStackEntry ->
                        val turmaId = backStackEntry.arguments?.getString("turmaId") ?: return@composable
                        ChamadaRoute(
                            turmaId = turmaId,
                            usuarioId = usuarioId,
                            onVoltar = { navController.popBackStack() },
                            onSelecionarAba = {},
                        )
                    }
                }
            }
        }
    }
}