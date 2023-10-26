package br.com.devcapu.cdc

data class NewAuthorFormUiState(
    val name: String = "",
    val onNameChange: (String) -> Unit = {},
    val email: String = "",
    val onEmailChange: (String) -> Unit = {},
    val description: String = "",
    val onDescriptionChange: (String) -> Unit = {},
    val onSaveAuthor: (String, String, String) -> Unit = { _, _, _ -> },
) {
    val emailHasRightFormat: Boolean
        get() {
            val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
            return email.matches(emailRegex)
        }

    val descriptionIsValid: Boolean
        get() {
            return email.isNotEmpty() && description.trim().length <= 400
        }
}