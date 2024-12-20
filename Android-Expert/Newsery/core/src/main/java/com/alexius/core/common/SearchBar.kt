package com.alexius.core.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.core.util.Dimens
import com.alexius.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean,
    onClick: (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    /* LaunchedEffect is used to trigger an event when the search bar is clicked */
    val isClicked = interactionSource.collectIsPressedAsState().value
    /* When the search bar is clicked, the onClick event is triggered */
    LaunchedEffect(key1 = isClicked) {
        if (isClicked) {
            onClick?.invoke()
        }
    }

    Box(modifier = modifier.background(Color.Transparent)){
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(Dimens.ExtraSmallPadding2)
                .searchBarBorder()
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(24.dp)),
            value = text,
            onValueChange = onValueChange,
            readOnly = readOnly,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    modifier = modifier.size(Dimens.IconSize),
                    tint = colorResource(id = R.color.body)
                )
            },
            placeholder = {
                Text(
                    text = "Search for news",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.placeholder)
                )
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.input_background),
                cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            ),
            textStyle = MaterialTheme.typography.bodySmall,
            interactionSource = interactionSource
        )
    }
}

fun Modifier.searchBarBorder() = composed{
    if (!isSystemInDarkTheme()) {
        border(
            width = 1.dp,
            color = Color.Transparent,
            shape = MaterialTheme.shapes.medium
        )
    } else {
        this
    }
}