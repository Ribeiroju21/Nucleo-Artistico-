package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

@Composable
fun CampoBusca(
    valor: String,
    onValorChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 44.dp),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(fontSize = 14.sp, color = NucleoColors.Texto),
        placeholder = {
            Text(placeholder, fontSize = 14.sp, color = NucleoColors.TextoSuave)
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = NucleoColors.TextoSuave)
        },
        trailingIcon = {
            if (valor.isNotEmpty()) {
                IconButton(onClick = { onValorChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Limpar busca", tint = NucleoColors.TextoSuave)
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = NucleoColors.Superficie,
            unfocusedContainerColor = NucleoColors.Superficie,
            focusedBorderColor = NucleoColors.Laranja,
            unfocusedBorderColor = NucleoColors.Borda,
            cursorColor = NucleoColors.Laranja,
        ),
    )
}

@Preview
@Composable
private fun CampoBuscaPreview() {
    var texto by remember { mutableStateOf("") }
    CampoBusca(valor = texto, onValorChange = { texto = it }, placeholder = "Buscar aluno")
}