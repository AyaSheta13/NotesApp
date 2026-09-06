package com.example.notesapp.ui.addeditnote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.ui.theme.CopperRose
import com.example.notesapp.ui.theme.ChinaDollDark
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.ui.theme.OnCopperRose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteRoute(
    onNavigateBack: () -> Unit,
    viewModel: AddEditNoteViewModel = viewModel(factory = AddEditNoteViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateBack.collect {
            onNavigateBack()
        }
    }

    AddEditNoteScreen(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onSaveClick = viewModel::onSaveClick,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    uiState: AddEditNoteUiState,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Note",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onSaveClick,
                containerColor = CopperRose,
                contentColor = OnCopperRose,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 12.dp
                ),
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Save Note",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = CopperRose,
                    strokeWidth = 3.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    placeholder = { Text("Enter note title") },
                    isError = uiState.titleError != null,
                    supportingText = {
                        uiState.titleError?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CopperRose,
                        unfocusedBorderColor = ChinaDollDark,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = CopperRose,
                        cursorColor = CopperRose
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = uiState.content,
                    onValueChange = onContentChange,
                    label = { Text("Content") },
                    placeholder = { Text("Start writing your note...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CopperRose,
                        unfocusedBorderColor = ChinaDollDark,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = CopperRose,
                        cursorColor = CopperRose
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun AddEditNoteScreenPreview() {
    NotesAppTheme {
        AddEditNoteScreen(
            uiState = AddEditNoteUiState(
                title = "My Note",
                content = "This is the content of my note. It can be quite long and will wrap to multiple lines."
            ),
            onTitleChange = {},
            onContentChange = {},
            onSaveClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun AddEditNoteScreenEmptyPreview() {
    NotesAppTheme {
        AddEditNoteScreen(
            uiState = AddEditNoteUiState(),
            onTitleChange = {},
            onContentChange = {},
            onSaveClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun AddEditNoteScreenErrorPreview() {
    NotesAppTheme {
        AddEditNoteScreen(
            uiState = AddEditNoteUiState(titleError = "Title cannot be empty"),
            onTitleChange = {},
            onContentChange = {},
            onSaveClick = {}
        )
    }
}
