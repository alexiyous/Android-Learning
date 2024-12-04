package com.alexius.jetcoffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.jetcoffee.model.Menu
import com.alexius.jetcoffee.model.dummyBestSellerMenu
import com.alexius.jetcoffee.model.dummyCategory
import com.alexius.jetcoffee.model.dummyMenu
import com.alexius.jetcoffee.ui.theme.JetCoffeeTheme
import com.alexius.jetcoffee.ui.theme.components.CategoryItem
import com.alexius.jetcoffee.ui.theme.components.MenuItem
import com.alexius.jetcoffee.ui.theme.components.Search
import com.alexius.jetcoffee.ui.theme.components.SectionText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetCoffeeTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    JetCoffeeApp()
                }
            }
        }
    }
}

@Composable
fun JetCoffeeApp() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
    ) {
        Banner()
        SectionText(title = stringResource(id = R.string.section_category))
        CategoryRow()
        SectionText(stringResource(R.string.section_favorite_menu))
        MenuRow(dummyMenu)
        SectionText(stringResource(R.string.section_best_seller_menu))
        MenuRow(dummyBestSellerMenu)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun Banner(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(160.dp)
        )
        Search()
    }
}

@Composable
fun MenuRow(
    listMenu: List<Menu>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(listMenu, key = { it.title }) { menu ->
            MenuItem(menu)
        }
    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(items = dummyCategory, key = { it.textCategory }) { category ->
            CategoryItem(category = category)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryRowPreview() {
    JetCoffeeTheme {
        CategoryRow()
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun MenuRowPrev () {
    JetCoffeeTheme {
        MenuRow(listMenu = dummyMenu)
    }
}

@Preview(showBackground = true)
@Composable
fun JetCoffeeAppPreview() {
    JetCoffeeTheme {
        JetCoffeeApp()
    }
}
