package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

@Composable
fun InfoChip(
    icone: ImageVector,
    texto: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(NucleoColors.Chip, CircleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = NucleoColors.TextoSuave,
            modifier = Modifier.size(12.dp),
        )
        Text(
            text = texto,
            fontSize = 11.sp,
            color = NucleoColors.TextoSuave,
        )
    }
}

@Preview
@Composable
private fun InfoChipPreview() {
    InfoChip(icone = Icons.Outlined.Schedule, texto = "Seg/Qua • 09:00")
}