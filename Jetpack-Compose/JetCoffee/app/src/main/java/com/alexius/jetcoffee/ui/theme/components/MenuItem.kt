package com.alexius.jetcoffee.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.jetcoffee.model.Menu
import com.alexius.jetcoffee.ui.theme.JetCoffeeTheme

@Composable
fun MenuItem(
    menu: Menu,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .width(140.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            Image(
                painter = painterResource(id = menu.image),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
            )
            Text(
                text = menu.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            Text(
                text = menu.price,
                style = MaterialTheme.typography.titleSmall,
                modifier = modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            )
        }
    }
}