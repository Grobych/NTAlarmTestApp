package com.globa.ntalarmtestapp.common.ui.composable

import android.annotation.SuppressLint
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    content: @Composable () -> Unit
) {

    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    if (cameraPermissionState.status.isGranted) {
        content()
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(Paddings.large),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                stringResource(R.string.camera_permission_rationale_1)
            } else {
                stringResource(R.string.camera_permission_rationale_2)
            }
            Text(
                text = textToShow,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
                onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text(stringResource(R.string.permission_button_text))
            }
        }
    }
}