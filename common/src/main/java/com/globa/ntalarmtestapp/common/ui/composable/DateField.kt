package com.globa.ntalarmtestapp.common.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.globa.ntalarmtestapp.common.R
import com.globa.ntalarmtestapp.common.theme.NTAlarmTestAppTheme
import com.globa.ntalarmtestapp.common.util.DateFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    onDateChanged: (Long?) -> Unit
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val date = datePickerState.selectedDateMillis
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        value = if (date != null) DateFormatter.getSimpleUTFDate(date) else "",
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = { onDateChanged(null) }) {
                Icon(painter = painterResource(id = R.drawable.ic_clear), contentDescription = "Clear")
            }
        },
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "Calendar")
            }
        },
        label = {
            Text(
                text = stringResource(R.string.date_picker_label)
            )
        }
    )
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
               showDatePicker = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDatePicker = false
                        onDateChanged(datePickerState.selectedDateMillis)
                    }
                ) {
                    Text(text = stringResource(R.string.date_picker_confirm_button_text))
                } },
            dismissButton = {
                Button(
                    onClick = { showDatePicker = false }) {
                    Text(text = stringResource(R.string.date_picker_cancel_button_text))
                }
            },
            content = {
                DatePicker(
                    state = datePickerState
                )
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DateFieldPreview() {
    NTAlarmTestAppTheme {
        Surface {
            val datePickerState = rememberDatePickerState(
                initialDisplayedMonthMillis = Date().time
            )
            DateField(
                datePickerState = datePickerState,
                onDateChanged = { datePickerState.setSelection(it) })
        }
    }
}