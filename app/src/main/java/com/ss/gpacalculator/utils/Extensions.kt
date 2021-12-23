package com.ss.gpacalculator.utils

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    anchorView: Int? = null,
    actionMessage: String? = null,
    action: (View) -> Unit = {}
) {
    Snackbar.make(this, message, length).apply {
        actionMessage?.let { this.setAction(actionMessage) { action(it) } }
        anchorView?.let { this.setAnchorView(anchorView) }
    }.show()
}

fun NavController.navigateTo(action: NavDirections, fragmentId: Int) {
    if (this.currentDestination == this.graph.findNode(fragmentId))
        this.navigate(action)
}