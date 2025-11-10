package com.smart.keuneunong.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.smart.keuneunong.ui.theme.SmartKeuneunongTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartKeuneunongTheme {
                SmartKeuneunongNavGraph()
            }
        }
    }
}