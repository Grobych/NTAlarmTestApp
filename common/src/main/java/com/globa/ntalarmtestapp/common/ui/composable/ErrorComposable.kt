package com.globa.ntalarmtestapp.common.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.globa.ntalarmtestapp.common.R
import com.globa.ntalarmtestapp.common.ui.Paddings

@Composable
fun ErrorComposable(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onBackButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Paddings.large),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onBackButtonClick() }
        ) {
            Text(text = stringResource(R.string.return_button_text))
        }
    }
}