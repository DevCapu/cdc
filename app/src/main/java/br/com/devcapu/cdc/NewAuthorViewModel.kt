package br.com.devcapu.cdc

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewAuthorViewModel(
    private val authorDAO: AuthorDAO
) : ViewModel() {

    private var _uiState = MutableStateFlow(NewAuthorFormUiState())
    val uiState: StateFlow<NewAuthorFormUiState> = _uiState.asStateFlow()

    fun updateState(newState: NewAuthorFormUiState) {
        _uiState.update {
            newState
        }
    }

    fun save() {
        viewModelScope.launch {
            authorDAO.save(
                Author(
                    name = uiState.value.name,
                    email = uiState.value.email,
                    description = uiState.value.description
                )
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = this[APPLICATION_KEY] as Context
                NewAuthorViewModel(
                    authorDAO = AppDatabase.getInstance(context).authorDao()
                )
            }
        }
    }
}