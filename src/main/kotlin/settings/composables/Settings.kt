package settings.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Preview()
@Composable
fun Settings() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2F384B))
    ) {

    }
}