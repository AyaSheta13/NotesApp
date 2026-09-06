package com.example.notesapp.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.data.local.NoteEntity
import com.example.notesapp.ui.theme.CopperRose
import com.example.notesapp.ui.theme.CopperRoseDark
import com.example.notesapp.ui.theme.CopperRoseLight
import com.example.notesapp.ui.theme.NoteCardChinaDoll
import com.example.notesapp.ui.theme.NoteCardCopperRose
import com.example.notesapp.ui.theme.NoteCardDustyRose
import com.example.notesapp.ui.theme.NoteCardLinen
import com.example.notesapp.ui.theme.NoteCardPlumWine
import com.example.notesapp.ui.theme.NoteCardRosewater
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.OnCopperRose
import com.example.notesapp.ui.theme.OnNoteCardChinaDoll
import com.example.notesapp.ui.theme.OnNoteCardCopperRose
import com.example.notesapp.ui.theme.OnNoteCardDustyRose
import com.example.notesapp.ui.theme.OnNoteCardLinen
import com.example.notesapp.ui.theme.OnNoteCardPlumWine
import com.example.notesapp.ui.theme.OnNoteCardRosewater
import com.example.notesapp.ui.theme.TextPrimary
import com.example.notesapp.ui.theme.TextSecondary

private val noteCardColors = listOf(
    NoteCardCopperRose,
    NoteCardDustyRose,
    NoteCardRosewater,
    NoteCardChinaDoll,
    NoteCardPlumWine,
    NoteCardLinen
)

private val noteCardTextColors = listOf(
    OnNoteCardCopperRose,
    OnNoteCardDustyRose,
    OnNoteCardRosewater,
    OnNoteCardChinaDoll,
    OnNoteCardPlumWine,
    OnNoteCardLinen
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListRoute(
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    viewModel: NotesListViewModel = viewModel(factory = NotesListViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    NotesListScreen(
        uiState = uiState,
        onNoteClick = onNoteClick,
        onAddClick = onAddClick,
        onDeleteNote = viewModel::deleteNote
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    uiState: NotesListUiState,
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onDeleteNote: (NoteEntity) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Notes",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = CopperRose,
                contentColor = OnCopperRose,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 12.dp
                ),
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Note",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        when (uiState) {
            is NotesListUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = CopperRose,
                            strokeWidth = 3.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading notes...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }
            is NotesListUiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(CopperRoseLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.StickyNote2,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = CopperRose
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "No notes yet",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap the + button to create your first note",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }
            is NotesListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 88.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.notes, key = { it.id }) { note ->
                        val index = uiState.notes.indexOf(note)
                        NoteItem(
                            note = note,
                            onClick = { onNoteClick(note.id) },
                            onDeleteClick = { onDeleteNote(note) },
                            cardColor = noteCardColors[index % noteCardColors.size],
                            textColor = noteCardTextColors[index % noteCardTextColors.size]
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteItem(
    note: NoteEntity,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    cardColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 16.dp, bottom = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (note.content.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = 0.72f),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                    )
                }
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(36.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = textColor.copy(alpha = 0.6f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "Delete note",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun NotesListScreenPreview() {
    NotesAppTheme {
        NotesListScreen(
            uiState = NotesListUiState.Success(
                notes = listOf(
                    NoteEntity(id = 1, title = "Shopping List", content = "Milk, eggs, bread, butter, cheese, apples, and coffee beans"),
                    NoteEntity(id = 2, title = "Meeting Notes", content = "Discuss Q3 roadmap with the team"),
                    NoteEntity(id = 3, title = "Workout Plan", content = "Monday: Chest and triceps\nTuesday: Back and biceps\nWednesday: Legs and shoulders"),
                    NoteEntity(id = 4, title = "Book Recommendations", content = ""),
                    NoteEntity(id = 5, title = "Travel Itinerary", content = "Day 1: Arrive in Paris, check in to hotel. Day 2: Visit the Louvre and Eiffel Tower.")
                )
            ),
            onNoteClick = {},
            onAddClick = {},
            onDeleteNote = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun NotesListScreenEmptyPreview() {
    NotesAppTheme {
        NotesListScreen(
            uiState = NotesListUiState.Empty,
            onNoteClick = {},
            onAddClick = {},
            onDeleteNote = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun NotesListScreenLoadingPreview() {
    NotesAppTheme {
        NotesListScreen(
            uiState = NotesListUiState.Loading,
            onNoteClick = {},
            onAddClick = {},
            onDeleteNote = {}
        )
    }
}
