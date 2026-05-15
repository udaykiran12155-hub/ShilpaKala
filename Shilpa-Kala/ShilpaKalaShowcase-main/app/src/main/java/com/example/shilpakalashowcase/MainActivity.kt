package com.example.shilpakalashowcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shilpakalashowcase.ui.screens.*
import com.example.shilpakalashowcase.ui.theme.ShilpaKalaShowcaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShilpaKalaShowcaseTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel()
                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") { LoginScreen(navController, mainViewModel) }
                        composable("dashboard") { DashboardScreen(navController, mainViewModel) }
                        composable("profile") { ProfileScreen(navController, mainViewModel) }
                        composable("settings") { SettingsScreen(navController, mainViewModel) }
                        composable("history") { HistoryScreen(navController, mainViewModel) }
                    }
                }
            }
        }
    }
}
