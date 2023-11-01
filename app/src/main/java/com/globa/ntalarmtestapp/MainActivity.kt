package com.globa.ntalarmtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.globa.ntalarmtestapp.common.theme.NTAlarmTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NTAlarmTestAppTheme {
                //Nav Host
            }
        }
    }
}