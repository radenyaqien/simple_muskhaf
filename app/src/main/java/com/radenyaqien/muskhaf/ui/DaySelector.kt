package com.radenyaqien.muskhaf.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.radenyaqien.muskhaf.R


@Composable
fun PageSelector(
    page: Int,
    onPrevious: () -> Unit,
    onNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onNextPage) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.next_page)
            )
        }
        Text(
            text = stringResource(id = R.string.page, page),
            style = MaterialTheme.typography.headlineMedium,
        )
        IconButton(onClick = onPrevious) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.previous_page)
            )
        }
    }
}