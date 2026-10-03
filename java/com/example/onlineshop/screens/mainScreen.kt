package com.example.onlineshop.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.onlineshop.R
import com.example.onlineshop.model.CategoryModel
import com.example.onlineshop.model.SliderModel
import com.example.onlineshop.model.ItemsModel
import com.example.onlineshop.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        // Replaced TODO() with an empty lambda to prevent NotImplementedError during preview rendering
        onCartClick = {},
        onItemClick = {}
    )
}

@Composable
fun MainScreen(
    onCartClick:()-> Unit,
    modifier: Modifier = Modifier,
    onCategoryClick: (String, String) -> Unit = { _, _ -> },
    onItemClick: (ItemsModel) -> Unit
) {
    val viewmodel = remember { MainViewModel() }
    val banners by viewmodel.banners.observeAsState(emptyList())
    val categories by viewmodel.categories.observeAsState(emptyList())
    val recomended by viewmodel.recomended.observeAsState(emptyList())

    val showBannerLoading = banners.isEmpty()
    val showCategoryLoading = categories.isEmpty()
    val showRecomendedLoading = recomended.isEmpty()

    ConstraintLayout(modifier = Modifier.background( Color.White)) {
        val (scrollList,bottomMenu) = createRefs()
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .constrainAs(scrollList){
              top.linkTo(parent.top)
              bottom.linkTo(parent.bottom)
              end.linkTo(parent.end)
                start.linkTo(parent.start)
        })
        {
            item {
             Row(modifier = Modifier
                 .fillMaxSize()
                 .padding(start = 16.dp, end = 16.dp, top = 48.dp),
                 horizontalArrangement = Arrangement.SpaceBetween,
                 verticalAlignment = Alignment.CenterVertically
             )
             {
                 Column {
                     Text("Welcome Back", color = Color.Black)
                     Text("Jackie", color = Color.Black,
                         fontSize = 18.sp, fontWeight = FontWeight.Bold
                     )

                 }
                 Row {
                     Image(painter = painterResource(id = R.drawable.fav_icon),
                         contentDescription = null)
                     Spacer(modifier = Modifier.padding(16.dp))
                     Image(painter = painterResource(id = R.drawable.search_icon),
                         contentDescription = null)
                                          }
             }
         }
            item { if (showBannerLoading) {
                Box( modifier= Modifier
                    .fillMaxSize()
                    .height(200.dp),
                    contentAlignment = Alignment.Center
                    ){
                    CircularProgressIndicator()
                }

            }else{
               Banner(banners) } }

            item { SectionTitle("Categories","SeeAll") }
            item { if (showCategoryLoading){
                     Box(modifier= Modifier
                         .fillMaxSize()
                         .height(50.dp),
                         contentAlignment = Alignment.Center
                     ){
                        CircularProgressIndicator()
                     }
            }else{
                       CategoryList(categories,onCategoryClick)
            }
            }
            item { SectionTitle("Recomendation","SeeAll") }
            item {
                if (showRecomendedLoading){

                    Box(modifier= Modifier
                        .fillMaxSize()
                        .height(200.dp),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }else{
                    ListItems(recomended, onItemClick  )
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp))



            BottomMenu(
                modifier= Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomMenu){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)


                    },
                onItemClick=onCartClick
            )

        }}
    }

}
@Composable
fun IndicatorDot(modifier: Modifier = Modifier,
                 size: Dp,
                 color: Color
){
    Box(modifier = modifier
        .size(size)
        .clip(CircleShape)
        .background(color)
    )

}
@Composable
fun DorIndicator(modifier: Modifier= Modifier,
                 totalDots: Int,
                 selectedIndex: Int,
                 selectedColor: Color= colorResource(R.color.blue),
                 unselectedColor: Color= colorResource(R.color.grey),
                 dotSize: Dp)
{
    LazyRow(modifier = modifier.wrapContentWidth().wrapContentHeight()) {
        items(totalDots) {index->
            IndicatorDot(color = if (index==selectedIndex)selectedColor else unselectedColor,
                size =dotSize)
            if (index != totalDots-1){
                Spacer(modifier= Modifier.padding(horizontal = 2.dp))
            }
        }
    } }

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(banners: List<SliderModel>) {
    AutoSlidingCarousel(banners =banners)
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(modifier: Modifier= Modifier,
                        pagerState: PagerState=remember { PagerState()},
                        banners: List<SliderModel>
){
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    Column(modifier= Modifier.fillMaxSize()) {
        if (banners.isNotEmpty()){
            HorizontalPager(count = banners.size, state = pagerState) {page->
                AsyncImage(model = ImageRequest
                    .Builder(LocalContext.current )
                    .data(banners[page].url)
                    .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 16.dp)
                        .height(150.dp)
                )
            }
        }
        DorIndicator(modifier = Modifier.padding(horizontal = 8.dp)
            .align (Alignment.CenterHorizontally),
            totalDots = banners.size,
            selectedIndex =if (isDragged)pagerState.currentPage else pagerState.currentPage,
            dotSize = 8.dp
        )

    }
}


@Composable
fun SectionTitle(title: String, actionTxt: String) {
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp,end= 16.dp,top=16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(text = title,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text=actionTxt,
            color = colorResource(R.color.blue))
    }
}

@Composable
fun CategoryItem(item : CategoryModel,isSelected: Boolean,onItemClick:()-> Unit){
    Row(modifier = Modifier
        .clickable(onClick = onItemClick)
        .background(color= if (isSelected)colorResource(R.color.blue)else Color.Transparent,
            shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically

    ) {
        AsyncImage(
            model = (item.picUrl),
            null,
            modifier = Modifier
                .size(45.dp)
                .background(color = if (isSelected) Color.Transparent else colorResource(R.color.light_grey),
                    shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Inside,
            colorFilter = if (isSelected){
                ColorFilter.tint(Color.White)
            }else{
                ColorFilter.tint(Color.Black)
            }

        )
        if (isSelected){
            Text(
                text = item.title,
                color= Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    } }


@Composable
 fun CategoryList(categories: List<CategoryModel>, onCategoryClick: (String, String) -> Unit) {
   var selectedIndex by remember { mutableStateOf( value =-1) }
    LazyRow(
        modifier= Modifier
            .fillMaxSize(),
          horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
       items(categories.size) {index ->
         CategoryItem(item=categories[index] ,
            isSelected = selectedIndex==index,
            onItemClick =

                {
                    selectedIndex=index
                    onCategoryClick(categories[index].id.toString(), categories[index].title) }

           )
       }} }

@Composable
fun BottomMenu(modifier: Modifier= Modifier,onItemClick: () -> Unit){
    Row(modifier= Modifier
        .padding(top = 16.dp, end = 16.dp, bottom = 50.dp)
        .fillMaxWidth()
        .background(colorResource(R.color.blue),
            shape = RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomMenuItem(icon=painterResource(R.drawable.btn_1),text="Explorer")
        BottomMenuItem(icon=painterResource(R.drawable.btn_2),text="Cart",
            onItemClick=onItemClick)
        BottomMenuItem(icon=painterResource(R.drawable.btn_3),text="Favorite")
        BottomMenuItem(icon=painterResource(R.drawable.btn_4),text="Orders")
        BottomMenuItem(icon=painterResource(R.drawable.btn_5),text="Profile")

    }

}

@Composable
fun BottomMenuItem(icon: Painter, text: String,onItemClick: (() -> Unit)?=null) {
   Column(modifier = Modifier
       .height(60.dp)
       .clickable{
           onItemClick?.invoke()}
       .padding(8.dp),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ) {
       Icon(icon, contentDescription = text, tint = Color.White)
       Text(text,color=Color.White, fontSize = 10.sp)
   }
}









