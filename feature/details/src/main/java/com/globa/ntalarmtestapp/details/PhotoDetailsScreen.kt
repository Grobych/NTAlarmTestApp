package com.globa.ntalarmtestapp.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.globa.ntalarmtestapp.common.theme.NTAlarmTestAppTheme
import com.globa.ntalarmtestapp.common.ui.DPs.headerHeight
import com.globa.ntalarmtestapp.common.ui.Paddings
import com.globa.ntalarmtestapp.common.ui.composable.ErrorComposable

@Composable
fun PhotoDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailsViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val showRemoveDialog = viewModel.showRemoveDialog.collectAsState()
    val context = LocalContext.current

    if (showRemoveDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.onRemoveDecline() },
            confirmButton = {
                Button(
                    shape = MaterialTheme.shapes.small,
                    onClick = { viewModel.onRemoveAccept() }
                ) {
                    Text(text = stringResource(R.string.confirm_button_text))
                }
            },
            dismissButton = {
                Button(
                    shape = MaterialTheme.shapes.small,
                    onClick = { viewModel.onRemoveDecline() }
                ) {
                    Text(text = stringResource(R.string.cancel_button_text))
                }
            },
            title = {
                Text(text = stringResource(R.string.remove_dialog_title))
            },
            text = {
                Text(text = stringResource(R.string.remove_dialog_text))
            }
        )
    }

    Scaffold(
        topBar = {
            Header(
                onBackButtonClick = onBackButtonClick,
                onRemoveButtonCLick = { viewModel.onRemoveButtonCLick() }
            )
        }
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .padding(it)
        ) {
            when (val state = uiState.value) {
                PhotoDetailsScreenUiState.Deleted -> {
                    Toast.makeText(
                        context,
                        stringResource(R.string.photo_removed_toast_text),
                        Toast.LENGTH_SHORT
                    ).show()
                    onBackButtonClick()
                }
                is PhotoDetailsScreenUiState.Done -> {
                    PhotoDetailsComposable(
                        title = state.imageTitle,
                        imageUri = state.imageUri,
                        date = state.date,
                        location = state.location
                    )
                }
                is PhotoDetailsScreenUiState.Error -> {
                    ErrorComposable(
                        errorMessage = state.message,
                        onBackButtonClick = onBackButtonClick
                    )
                }
                PhotoDetailsScreenUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun PhotoDetailsComposable(
    modifier: Modifier = Modifier,
    title: String,
    imageUri: String,
    date: String,
    location: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = Paddings.large,
                end = Paddings.large,
                bottom = Paddings.large
            ),
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .requiredWidth(360.dp)
                .requiredHeight(480.dp)
                .clip(MaterialTheme.shapes.medium),
        model = imageUri,
        contentDescription = "Photo",
        loading = { CircularProgressIndicator() },
        error = {
            Image(
                painter = painterResource(id = com.globa.ntalarmtestapp.common.R.drawable.ic_broken),
                contentDescription = "Image broken or not found"
            )
        })
        Column(
            verticalArrangement = Arrangement.spacedBy(Paddings.medium),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                text = title,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                text = "Date: $date"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                text = "Location: $location"
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onRemoveButtonCLick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = Paddings.large, end = Paddings.large)
            .height(headerHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onBackButtonClick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
        }
        Text(
            text = stringResource(R.string.photo_details_header_text),
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(onClick = { onRemoveButtonCLick() }) {
            Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "Remove")
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeaderPreview() {
    NTAlarmTestAppTheme {
        Surface {
            Header(
                onBackButtonClick = {},
                onRemoveButtonCLick = {}
            )
        }
    }
}