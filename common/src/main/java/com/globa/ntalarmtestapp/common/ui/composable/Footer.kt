package com.globa.ntalarmtestapp.common.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.globa.ntalarmtestapp.common.R
import com.globa.ntalarmtestapp.common.theme.NTAlarmTestAppTheme

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    onPhotoListClick: () -> Unit,
    onMapClick: () -> Unit,
    onCameraClick: () -> Unit,
    selected: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FooterItem(
            name = "Photos",
            iconId = R.drawable.ic_grid,
            isSelected = selected == 1,
            onCLick = onPhotoListClick
        )
        FooterItem(
            name = "Add Photo",
            iconId = R.drawable.ic_camera,
            isSelected = selected == 2,
            onCLick = onCameraClick
        )
        FooterItem(
            name = "Map",
            iconId = R.drawable.ic_map_marker,
            isSelected = selected == 3,
            onCLick = onMapClick
        )
    }
}

@Composable
private fun FooterItem(
    modifier: Modifier = Modifier,
    name: String,
    iconId: Int,
    isSelected: Boolean,
    onCLick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .size(90.dp)
            .clip(MaterialTheme.shapes.extraSmall)
        ,
        onClick = { onCLick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onBackground
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "",
                tint = color
            )
            Text(
                text = name,
                color = color
            )
        }
    }
    
}


@Preview
@Composable
fun FooterPreview() {
    NTAlarmTestAppTheme {
        Surface {
            Footer(
                onPhotoListClick = {},
                onMapClick = {},
                onCameraClick = {},
                selected = 1
            )
        }
    }
}