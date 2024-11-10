package com.example.loginapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginapp.pages.HomePage
import com.example.loginapp.pages.LoginPage
import com.example.loginapp.pages.SignUpPage

@Composable
fun MyNavigationApp(modifier: Modifier = Modifier, authViewModel: AuthViewModel){

    /**
     * from navController we can navigate to any screen
     *
     * The navController in this code is responsible for managing app navigation,
     * controlling which screen is displayed,
     * and allowing navigation between different composable screens within the app
     * */
    val navController = rememberNavController()

    /**
     * The NavHost is responsible for defining the navigation graph,
     * which specifies the screens (or destinations) in the app and their routes
     * */
    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login"){ LoginPage(modifier, navController, authViewModel) }
        composable("signup"){ SignUpPage(modifier, navController, authViewModel) }
        composable("home"){ HomePage(modifier, navController, authViewModel) }
    })
}