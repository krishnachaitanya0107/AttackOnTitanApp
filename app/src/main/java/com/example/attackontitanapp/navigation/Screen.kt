package com.example.attackontitanapp.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Welcome : Screen("welcome_screen")
    object Home : Screen("home_screen")
    object Details : Screen("details_screen/{titanId}") {
        fun passTitanId(titanId: Int): String {
            return "details_screen/$titanId"
        }
    }

    object Search : Screen("search_screen")
}
