package br.com.devcapu.cdc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewAuthorForm(
    viewModel: NewAuthorViewModel = viewModel(factory = NewAuthorViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    NewAuthorForm(
        uiState = uiState,
        onNameChange = { viewModel.updateState(uiState.copy(name = it)) },
        onEmailChange = { viewModel.updateState(uiState.copy(email = it)) },
        onDescriptionChange = { viewModel.updateState(uiState.copy(description = it)) },
        onSave = { viewModel.save() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewAuthorForm(
    uiState: NewAuthorFormUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.name,
                label = { Text("Nome") },
                onValueChange = onNameChange,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                singleLine = true,
            )

            if (uiState.name.isEmpty()) {
                Text(text = "Nome é obrigatório", color = Color.Red)
            }
        }

        Column {
            EmailTextField(
                email = uiState.email,
                onEmailChange = onEmailChange,
                onImeAction = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
            )

            if (uiState.email.isBlank()) {
                Text(text = "Email é obrigatório", color = Color.Red)
            } else if (!uiState.emailHasRightFormat) {
                Text(text = "Email inválido", color = Color.Red)
            }
        }

        Column {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                value = uiState.description,
                label = { Text("Descrição") },
                onValueChange = onDescriptionChange,
                maxLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) }),
                singleLine = false
            )

            if (uiState.description.isBlank()) {
                Text(text = "Descrição é obrigatória")
            } else if (uiState.descriptionIsValid.not()) {
                Text(text = "Descrição deve ter no máximo 400 caracteres", color = Color.Red)
            }
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSave
        ) {
            Text(text = "Salvar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    email: String,
    onImeAction: () -> Unit = {},
    onEmailChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = email,
        label = { Text("Email") },
        onValueChange = { onEmailChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { onImeAction() }),
        singleLine = true,
    )
}

@Preview(showBackground = true)
@Composable
fun NewAuthorFormPreviewWithEmptyEmail() {
    NewAuthorForm()
}