package com.globa.ntararmtestapp.camera

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.globa.ntalarmtestapp.common.theme.NTAlarmTestAppTheme
import com.globa.ntalarmtestapp.common.ui.Paddings
import com.globa.ntalarmtestapp.common.ui.composable.BaseHeader
import com.globa.ntalarmtestapp.common.ui.composable.CameraPermission
import com.globa.ntalarmtestapp.common.ui.composable.ErrorComposable
import com.globa.ntalarmtestapp.common.ui.composable.LocationPermissions
import com.globa.ntalarmtestapp.common.util.DateFormatter
import com.globa.ntalarmtestapp.common.util.readUri
import java.io.File
import java.util.Date

@Composable
fun CameraScreen(
    onBackButtonClick: () -> Unit
) {
    LocationPermissions {
        CameraPermission {
            Scaffold(
                topBar = {
                    BaseHeader(text = stringResource(R.string.add_photo_header))
                }
            ) {
                CameraScreenContent(
                    modifier = Modifier.padding(it)
                ) {
                    onBackButtonClick()
                }
            }
        }
    }
}

@Composable
fun CameraScreenContent(
    modifier: Modifier = Modifier,
    viewModel: CameraScreenViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val onPhotoCaptured = fun(uri: Uri) {
        val bytes = readUri(context, uri)
        if (bytes != null) viewModel.onPhotoCaptured(bytes)
        else onBackButtonClick()
    }

    val onSaveButtonClick = fun() {
        viewModel.onSaveButtonClick(context)
    }

    val onImageNameChange = fun(name: String) {
        viewModel.onImageNameChange(name)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
        it?.let { uri ->
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION + Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            viewModel.onUriChange(uri)
        }
    }
    val selectPath = fun() {
        launcher.launch(null)
    }

    when (val state = uiState.value) {
        CameraScreenUiState.Creating -> TakingPhoto(onPhotoCaptured = onPhotoCaptured)
        CameraScreenUiState.Done -> DoneScreen(
            modifier = modifier,
            onDoneButtonClick = onBackButtonClick
        )
        is CameraScreenUiState.Error -> ErrorComposable(
            modifier = modifier,
            errorMessage = state.message,
            onBackButtonClick = onBackButtonClick
        )
        is CameraScreenUiState.Processing -> ProcessingScreen(
            modifier = modifier,
            message = state.stateMessage
        )
        is CameraScreenUiState.ReadyToSave -> ReadyToSaveScreen(
            modifier = modifier,
            bitmap = state.bitmap,
            path = state.path?.toString()?: stringResource(R.string.unselected_image_folder_text),
            imageName = state.name,
            location = state.location,
            date = state.date,
            onImageNameChange = onImageNameChange,
            onSendButtonClick = onSaveButtonClick,
            onPathClick = selectPath
        )
        CameraScreenUiState.Saving -> ProcessingScreen(
            modifier = modifier,
            message = stringResource(R.string.saving_photo_text)
        )
    }
}

@Composable
fun TakingPhoto(
    onPhotoCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "com.globa.ntalarmtestapp.provider",
        file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            onPhotoCaptured(uri)
        }

    SideEffect {
        cameraLauncher.launch(uri)
    }
}

@Composable
fun DoneScreen(
    modifier: Modifier = Modifier,
    onDoneButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Paddings.large),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.photo_saved_text)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onDoneButtonClick() },
            shape = MaterialTheme.shapes.small
            ) {
            Text(text = stringResource(R.string.return_button_text))
        }
    }
}

@Composable
fun ProcessingScreen(
    modifier: Modifier = Modifier,
    message: String
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
            text = message,
            color = MaterialTheme.colorScheme.error
        )
        CircularProgressIndicator(
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun ReadyToSaveScreen(
    modifier: Modifier = Modifier,
    bitmap: Bitmap,
    path: String,
    imageName: String,
    location: String,
    date: String,
    onImageNameChange: (String) -> Unit,
    onSendButtonClick: () -> Unit,
    onPathClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = Paddings.large,
                end = Paddings.large,
                bottom = Paddings.large
            )
            .verticalScroll(
                state = scrollState,
                enabled = true
            ),
        verticalArrangement = Arrangement.spacedBy(Paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Photo to upload",
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = path,
            onValueChange = {},
            singleLine = true,
            readOnly = true,
            label = {
                Text(text = stringResource(R.string.path_label))
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable { onPathClick() }
                        .size(40.dp)
                    ,
                    painter = painterResource(id = R.drawable.ic_folder),
                    contentDescription = "Select folder")
            },
            shape = MaterialTheme.shapes.small
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = imageName,
            onValueChange = { onImageNameChange(it) },
            singleLine = true,
            label = {
                Text(text = stringResource(R.string.image_name_text))
            },
            shape = MaterialTheme.shapes.small
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = location
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = date
        )
        Button(
            enabled = path != stringResource(R.string.unselected_image_folder_text), //TODO: pass boolean
            modifier = Modifier.fillMaxWidth().padding(top = Paddings.extraLarge),
            shape = MaterialTheme.shapes.small,
            onClick = { onSendButtonClick() }
        ) {
            Text(text = stringResource(R.string.save_button_text))
        }
    }
}

fun Context.createImageFile(): File {
    val timeStamp = DateFormatter.getExtendDate(Date().time)
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}

@Preview
@Composable
fun ReadyToSaveScreenPreview() {
    NTAlarmTestAppTheme {
        Surface {
            ReadyToSaveScreen(
                bitmap = Bitmap.createBitmap(
                    360,
                    480,
                    Config.ALPHA_8
                ),
                path = "/v0/storage/downloads",
                imageName = "IMG_test",
                location = "Lat: 23.123456; Lng: 65.432165",
                date = DateFormatter.getExtendDate(1699115701),
                onSendButtonClick = {},
                onImageNameChange = {},
                onPathClick = {}
            )
        }
    }
}