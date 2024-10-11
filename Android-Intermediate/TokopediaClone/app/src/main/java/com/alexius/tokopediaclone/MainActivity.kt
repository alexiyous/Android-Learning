package com.alexius.tokopediaclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.alexius.tokopediaclone.entity.SellableItems
import com.alexius.tokopediaclone.ui.theme.TokopediaCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TokopediaCloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        SearchBar()
                        FilterBar()
                        SellableItemList(
                            items = List(50) { index ->
                                SellableItems(index + 1, "Item ${index + 1}", "$${(index + 1) * 10}.00", "https://via.placeholder.com/150")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        label = { Text("Search") },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun FilterBar(modifier: Modifier = Modifier) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Popular", "Recent", "Favorites")

    Row(modifier = modifier.padding(16.dp)) {
        filters.forEach { filter ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { selectedFilter = filter }
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = if (selectedFilter == filter) Color.Gray else Color.LightGray
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = filter,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
    }
}

@Composable
fun SellableItemCard(item: SellableItems, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(item.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = item.price,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun SellableItemList(items: List<SellableItems>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(16.dp)
    ) {
        items(items) { item ->
            SellableItemCard(item = item)
        }
    }
}