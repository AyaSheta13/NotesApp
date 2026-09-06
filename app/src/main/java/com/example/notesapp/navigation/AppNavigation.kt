package com.example.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.ui.addeditnote.AddEditNoteRoute
import com.example.notesapp.ui.notes.NotesListRoute

private const val NOTES_ROUTE = "notes"
private const val ADD_EDIT_ROUTE = "add_edit_note"
private const val NOTE_ID_ARG = "noteId"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NOTES_ROUTE) {
        composable(NOTES_ROUTE) {
            NotesListRoute(
                onNoteClick = { id -> navController.navigate("$ADD_EDIT_ROUTE?$NOTE_ID_ARG=$id") },
                onAddClick = { navController.navigate(ADD_EDIT_ROUTE) }
            )
        }
        composable(
            route = "$ADD_EDIT_ROUTE?$NOTE_ID_ARG={$NOTE_ID_ARG}",
            arguments = listOf(
                navArgument(NOTE_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditNoteRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}