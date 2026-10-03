package com.example.onlineshop.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.onlineshop.R
import com.example.onlineshop.model.ItemsModel
import com.example.onlineshop.ui.theme.OnlineShopTheme

@Composable
fun RecommendedItem(items: List<ItemsModel>, pos: Int, onItemClick: (ItemsModel) -> Unit) {
    val item = items[pos]
    Column(modifier = Modifier
        .padding(8.dp)
        .height(230.dp))
    {
        AsyncImage(model = items[pos].picUrl.firstOrNull(),
        contentDescription = null,
            modifier = Modifier.width(175.dp)
                .background(colorResource(R.color.light_grey),
                 shape = RoundedCornerShape(10.dp)
                    ).height(160.dp)
                .padding(8.dp).fillMaxSize()
                .clickable{onItemClick(items[pos])},
            contentScale = ContentScale.Inside
        )
        Text(
            text = items[pos].title,
            color= Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
        Row(modifier = Modifier.padding(top = 4.dp).width(175.dp),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Row{
                Image(painter = painterResource(R.drawable.star),
                    contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = items[pos].rating.toString(),
                    color = Color.Black,
                    fontSize = 15.sp,
                    ) }
            Text(
                text = "$${items[pos].price}",
                color = colorResource(R.color.blue),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewRecommendedItem() {
    val sampleItems = listOf(
        ItemsModel(
            title = "Product 1",
            price = 99.99,
            rating = 4.5,
            picUrl = arrayListOf("https://example.com/pic1.jpg")
        ),
        ItemsModel(
            title = "Product 2",
            price = 49.99,
            rating = 4.0,
            picUrl = arrayListOf("https://example.com/pic2.jpg")
        )
    )
    OnlineShopTheme {
        RecommendedItem(
            items = sampleItems,
            pos = 0,
            onItemClick = {}
        )
    }
}

@Composable
fun ListItems(items: List<ItemsModel>, onItemClick: (ItemsModel) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .height(500.dp)
           .padding( start = 16.dp)
            .padding(end = 16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size){
            row ->
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RecommendedItem(items,row,onItemClick)
            }
        }
    }

}

@Composable
fun ListItemsFullSize(items: List<ItemsModel>, onItemClick: (ItemsModel) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding( start = 16.dp)
            .padding(end = 16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size){
                row ->
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RecommendedItem(items,row,onItemClick)
            }
        }
    }}