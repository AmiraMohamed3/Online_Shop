package com.example.onlineshop.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlineshop.R
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.onlineshop.model.ItemsModel
import com.example.onlineshop.viewmodel.MainViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.onlineshop.ui.theme.OnlineShopTheme


@Composable
fun listItemScreen(
    title: String,
    onBackClick: () -> Unit,
    viewModel: MainViewModel,
    id: String,
    onItemClick: (ItemsModel) -> Unit
) {
    val items by viewModel.loadFiltered(id).observeAsState(emptyList())
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(id) {
        viewModel.loadFiltered(id)
    }

    ListItemScreenContent(
        title = title,
        onBackClick = onBackClick,
        items = items,
        onItemClick = onItemClick
    )
}

@Composable
fun ListItemScreenContent(
    title: String,
    onBackClick: () -> Unit,
    items: List<ItemsModel>,
    onItemClick: (ItemsModel) -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 36.dp)
                .padding(horizontal = 16.dp)
        ) {
            val (backBtn, cartTxt) = createRefs()
            Text(
                modifier = Modifier
                    .height(70.dp)
                    .width(400.dp)
                    .padding(8.dp)
                    .constrainAs(cartTxt) { centerTo(parent) },
                text = title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )

            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier.height(50.dp).clickable{ onBackClick() }
                    .constrainAs(backBtn){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )
        }
            //ListItems(items=items,onItemClick = onItemClick)
        if (isLoading){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }else{
            ListItemsFullSize(items,onItemClick)
        }
    }
    LaunchedEffect(items) {
        isLoading=items.isEmpty()
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemScreenPreview() {
    OnlineShopTheme {
        ListItemScreenContent(
            title = "Sample Title",
            onBackClick = {},
            items = listOf(
                ItemsModel(
                    title = "Sample Item 1",
                    price = 19.99,
                    rating = 4.5,
                    picUrl = arrayListOf("url1")
                ),
                ItemsModel(
                    title = "Sample Item 2",
                    price = 29.99,
                    rating = 3.5,
                    picUrl = arrayListOf("url2")
                )
            ),
            onItemClick = {}
        )
    }
}
