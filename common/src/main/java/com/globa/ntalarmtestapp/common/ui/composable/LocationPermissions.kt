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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissions(
    content: @Composable () -> Unit
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    val allPermissionsRevoked =
        locationPermissionsState.permissions.size ==
                locationPermissionsState.revokedPermissions.size
    if (!allPermissionsRevoked) {
        content()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Paddings.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.location_permission_rationale))
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text(text = stringResource(id = R.string.permission_button_text))
            }
        }

    }
}