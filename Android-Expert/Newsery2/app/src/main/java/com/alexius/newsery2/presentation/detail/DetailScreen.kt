package com.alexius.newsery2.presentation.detail

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alexius.core.domain.model.Article
import com.alexius.core.domain.model.Source
import com.alexius.core.util.Dimens.ArticleImageHeight
import com.alexius.core.util.Dimens.MediumPadding1
import com.alexius.newsery2.presentation.detail.components.DetailsTopBar
import com.alexius.newsery2.R
import com.alexius.newsery2.ui.theme.NewseryTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current

    val isBookmarked = article.isBookmarked

    Column(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                /* Open the article in the browser */
                Intent(Intent.ACTION_VIEW).also {
                    it.data = article.url.toUri()
                    /* Check if there is an app that can handle the intent */
                    /* Must add queries in the android manifest, see manifest */
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)}
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.type = "text/plain"
                    /* Share the article's URL */
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    /* Create a chooser to share the article */
                    context.startActivity(Intent.createChooser(it, null))
                }
            },
            onBookmarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(article))
            },
            isBookmarked = isBookmarked,
            onBackClick = {
                navigateUp()
            },
            compareToDatabase = {event(DetailsEvent.IsArticleInDatabase(article))},
        )

        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                end = MediumPadding1,
                top = MediumPadding1)
        ) {
            item{
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(article.urlToImage)
                        .build(),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = modifier.height(MediumPadding1))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )

                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Preview (showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DetailScreenPreview() {
    NewseryTheme {
        DetailScreen(
            article = Article(
                author = "Lucas",
                title = "The ESP32 and LSTM Neural Networks for Time Series Prediction",
                description = "ESP32 and LSTM Neural Networks for Time Series Prediction with Arduino IDE",
                url = "https://eloquentarduino.com/posts/arduino-esp32-lstm",
                urlToImage = "https://res.cloudinary.com/dltmvmpuz/image/upload/f_auto/c_limit,w_900/eq.com/storage/01J3389HVXXAAMVAAT11D5W7P2.png?_a=BAAAV6DQ",
                publishedAt = "2021-01-01",
                content = "ESP32 and LSTM Neural Networks for Time Series Prediction with Arduino IDE",
                source = Source(
                    id = "id",
                    name = "Name"
                ),
                isBookmarked = false
            ),
            event = { },
            navigateUp = { }
        )
    }
}