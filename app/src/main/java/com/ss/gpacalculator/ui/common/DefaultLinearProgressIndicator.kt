package com.ss.gpacalculator.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultLinearProgressIndicator(modifier: Modifier) = LinearProgressIndicator(
    modifier = modifier.fillMaxWidth()
)

@Composable
fun DefaultLinearProgressIndicator(modifier: Modifier, progress: Float) = LinearProgressIndicator(
    modifier = modifier.fillMaxWidth(),
    progress = progress
)