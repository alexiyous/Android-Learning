package com.alexius.newsery2.presentation.home

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.alexius.core.domain.model.ArticleModel
import com.alexius.core.util.Dimens.MediumPadding1
import com.alexius.newsery2.R
import com.alexius.core.util.Dimens.ExtraSmallPadding
import com.alexius.newsery2.presentation.common.ArticlesList
import com.alexius.newsery2.presentation.common.SearchBar
import com.alexius.newsery2.presentation.common.TypewriterText

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<ArticleModel>,
    navigateToSearch: () -> Unit,
    navigateToDetails:(ArticleModel) -> Unit
) {
    val titles = remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(0, 9))
                    .joinToString(separator =" \uD83D\uDFE1 "){ it.title }
            } else {
                ""
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        TypewriterText(
            text = "Welcome to Newsery",
            modifier = modifier.padding(horizontal = MediumPadding1),
            fontSize = 24.sp,
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_title),
        )

        Spacer(modifier = modifier.height(MediumPadding1))

        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = {
                navigateToSearch()
            },
            onSearch = {},
            modifier = modifier.padding(horizontal = ExtraSmallPadding)
        )

        Spacer(modifier = modifier.height(MediumPadding1))

        Text(text = titles.value,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )

        Spacer(modifier = modifier.height(MediumPadding1))

        ArticlesList(
            modifier = modifier.padding(horizontal = MediumPadding1),
            articles = articles,
            onClick = { article ->
                navigateToDetails(article)
            }
        )
    }
}