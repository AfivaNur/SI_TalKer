package com.afiva.appskelurahan.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RadioOption(title: String, options: List<String>) {
    var selected by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color.DarkGray)
        Row {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    RadioButton(
                        selected = selected == option,
                        onClick = { selected = option }
                    )
                    Text(option)
                }
            }
        }
    }
}
