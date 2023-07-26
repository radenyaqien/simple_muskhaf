package com.radenyaqien.muskhaf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.radenyaqien.muskhaf.R


@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    shouldShowHint: Boolean = false,
    hint: String = stringResource(id = R.string.search)
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
                defaultKeyboardAction(ImeAction.Search)
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(2.dp)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(end = 16.dp)
        )
        if (shouldShowHint) {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = Color.LightGray,
                modifier = Modifier
                    .align(
                        Alignment.CenterStart
                    )
                    .padding(start = 16.dp)
            )
        }
        IconButton(onClick = onSearch, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        }
    }

}