package com.example.quotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        setContent {
            QuotesApp()
        }
    }
}

@Composable
fun QuotesApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Display"
    ) {

        composable("Display") {
            DisplayQuoteScreen(navController)
        }

        composable("Add") {
            AddQuoteScreen(navController)
        }

        composable(
            route = "Edit/{id}/{quote}/{book}/{author}/{page}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
                navArgument("quote") {
                    type = NavType.StringType
                },
                navArgument("book") {
                    type = NavType.StringType
                },
                navArgument("author") {
                    type = NavType.StringType
                },
                navArgument("page") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val args = backStackEntry.arguments!!

            EditQuoteScreen(
                navController = navController,
                quote = Quote(
                    id = args.getString("id")!!,
                    quote = args.getString("quote")!!,
                    book = args.getString("book")!!,
                    author = args.getString("author")!!,
                    page = args.getString("page")!!
                )
            )
        }
    }
}