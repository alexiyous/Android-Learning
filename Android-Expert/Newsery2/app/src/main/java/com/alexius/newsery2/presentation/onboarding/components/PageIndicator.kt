package com.alexius.newsery2.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexius.core.util.Dimens.IndicatorSize
import com.alexius.newsery2.presentation.onboarding.pages
import com.alexius.newsery2.ui.theme.BlueGray
import com.alexius.newsery2.ui.theme.NewseryTheme
import com.alexius.newsery2.ui.theme.Yellow

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.wormTransition(
    pagerState: PagerState
) =
    drawBehind {
        val distance = size.width + 10.dp.roundToPx()
        val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
        val wormOffset = (scrollPosition % 1) * 2

        val xPos = scrollPosition.toInt() * distance
        val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
        val tail = xPos + size.width + 1f.coerceAtMost(wormOffset) * distance

        val worm = RoundRect(
            head, 0f, tail, size.height,
            CornerRadius(50f)
        )

        val path = Path().apply { addRoundRect(worm) }
        drawPath(path = path, color = Yellow)
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    pagerState: PagerState,
    spacing: Dp = 10.dp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            modifier = modifier
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pageSize) {
                Box(
                    modifier = Modifier
                        .size(IndicatorSize)
                        .background(
                            color = BlueGray,
                            shape = CircleShape
                        )
                )
            }
        }

        Box(
            Modifier
                .wormTransition(pagerState)
                .size(IndicatorSize)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun PageIndicatorPreview() {

    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    NewseryTheme {
        PageIndicator(
            pageSize = 3,
            pagerState = pagerState
        )
    }
}