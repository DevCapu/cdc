package br.com.devcapu.cdc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.devcapu.cdc.ui.theme.CasaDoCódigoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasaDoCódigoTheme {
                NewAuthorForm()
            }
        }
    }
}