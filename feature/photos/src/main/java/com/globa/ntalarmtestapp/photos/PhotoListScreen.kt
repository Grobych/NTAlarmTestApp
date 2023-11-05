package com.globa.ntalarmtestapp.photos

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.globa.ntalarmtestapp.common.theme.NTAlarmTestAppTheme
import com.globa.ntalarmtestapp.common.ui.Paddings
import com.globa.ntalarmtestapp.common.ui.composable.BaseHeader
import com.globa.ntalarmtestapp.common.util.DateFormatter
import com.globa.ntalarmtestapp.photos.api.Photo

@Composable
fun PhotoListScreen(
    viewModel: PhotosViewModel = hiltViewModel(),
    onPhotoClick: (Int) -> Unit
) {
    val uiState = viewModel.photosUiState.collectAsState()

    Scaffold(
        topBar = {
            BaseHeader(
                text = "Photos"
            )
        }
    ) {
        when (val state = uiState.value) {
            is PhotosUiState.Loading -> {
                CircularProgressIndicator()
            }
            is PhotosUiState.Error -> {
                ErrorPhotoListScreen(
                    modifier = Modifier.padding(it),
                    errorMessage = state.message,
                    onReturnButtonClick = { viewModel.fetchPhotos() }
                )
            }
            is PhotosUiState.Done -> {
                val photos = state.photos
                DonePhotoListScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    photos = photos,
                    onPhotoClick = onPhotoClick
                )
            }
        }
    }
}

@Composable
private fun ErrorPhotoListScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onReturnButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage
        )
        Button(onClick = { onReturnButtonClick() }) {
            Text(text = "Return")
        }
    }
}

@Composable
private fun DonePhotoListScreen(
    modifier: Modifier = Modifier,
    photos: List<Photo>,
    onPhotoClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(Paddings.medium)
    ) {
        items(photos.size) { index ->
            val photo = photos[index]
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(
                            start = Paddings.medium,
                            end = Paddings.medium,
                            top = Paddings.medium
                        )
                        .clickable {
                            onPhotoClick(photo.id)
                        }
                        .clip(MaterialTheme.shapes.medium),
                    model = photo.path,
                    loading = { CircularProgressIndicator() },
                    error = {
                        Image(
                            painter = painterResource(id = com.globa.ntalarmtestapp.common.R.drawable.ic_broken),
                            contentDescription = "Image broken or not found"
                        )
                    },
                    contentDescription = "Photo ${photo.id}"
                )
                Text(text = DateFormatter.getSimpleDate(photo.date))
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PhotoListScreenDonePreview() {
    val list = listOf(
        Photo(
            id = 1,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 2,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 3,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 4,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 5,
            path = "logo.png",
            date = 1262307723
        ),
    )

    NTAlarmTestAppTheme {
        Surface {
            DonePhotoListScreen(photos = list, onPhotoClick = {} )
        }
    }
}