package com.alexius.bookmark.bookmark

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.alexius.bookmark.R
import androidx.compose.ui.unit.sp
import com.alexius.core.common.ArticlesList
import com.alexius.core.common.TypewriterText
import com.alexius.core.util.Dimens.MediumPadding1

@androidx.compose.runtime.Composable
fun BookmarkScreen(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier.Companion,
    state: BookmarkState,
    navigateToDetails: (com.alexius.core.domain.model.Article) -> Unit,
){
    androidx.compose.foundation.layout.Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
    ) {
        TypewriterText(
            text = "Bookmark",
            fontSize = 24.sp,
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Companion.Bold),
            color = if (isSystemInDarkTheme()){
                Color(0xFFE4E6EB)
            } else {
                Color.Black
            }
        )

        androidx.compose.foundation.layout.Spacer(
            modifier = androidx.compose.ui.Modifier.Companion.height(
                MediumPadding1
            )
        )

        ArticlesList(articles = state.articles, onClick = { navigateToDetails(it) })
    }
}