package com.ss.gpacalculator.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ss.gpacalculator.R
import com.ss.gpacalculator.ui.common.DefaultButton
import com.ss.gpacalculator.ui.common.DefaultCenterTopAppBar
import com.ss.gpacalculator.ui.common.DefaultIconButton
import com.ss.gpacalculator.ui.common.DefaultScaffold

@Composable
fun HomeScreen(
    onOverallClick: () -> Unit,
    onSemesterClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    DefaultScaffold(topBar = { HomeTopAppBar(onSettingsClick = onSettingsClick) }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            DefaultButton(
                onClick = onOverallClick,
                text = R.string.home_calculate_overall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            DefaultButton(
                onClick = onSemesterClick,
                text = R.string.home_calculate_semester,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun HomeTopAppBar(onSettingsClick: () -> Unit) = DefaultCenterTopAppBar(
    title = R.string.app_name,
    actions = { DefaultIconButton(onClick = onSettingsClick, icon = Icons.Default.Settings) }
)