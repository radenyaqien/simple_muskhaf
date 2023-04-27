package com.radenyaqien.muskhaf.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun SwippableComponent(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val draggableModifier = Modifier

        .draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                offsetX += delta
                if (offsetX.absoluteValue >= screenWidth.value) {
                    if (offsetX < 0) {
                        onSwipeLeft()
                    } else {
                        onSwipeRight()
                    }
                    offsetX = 0f
                }
            }
        )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(draggableModifier)
    ) {
        content()
    }
}
