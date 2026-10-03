package com.example.onlineshop.screens

import android.widget.Button
import android.widget.ImageButton
import android.widget.RatingBar
import androidx.compose.animation.animateBounds
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.onlineshop.R
import com.example.onlineshop.model.ItemsModel
import com.example.onlineshop.ui.theme.OnlineShopTheme

@Composable
fun DetailScreen(
    item: ItemsModel,
    onBackClick:()-> Unit,
    onAddToCartClick:()-> Unit,
    onCartClick:()->Unit
) {
    var selectedImage by remember { mutableStateOf((item.picUrl.firstOrNull() ?: "")) }
    var selectedModelIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)) {

        ConstraintLayout(
            modifier = Modifier
                .padding(top = 36.dp, bottom = 16.dp)
                .fillMaxSize()
        ) {
            val (back, fav) = createRefs()
            Image(
                painterResource(R.drawable.back),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .constrainAs(back) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }

            )
            Image(
                painterResource(R.drawable.fav_icon),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .constrainAs(fav) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )
        }
            if(selectedImage.isNotEmpty()){
                Image(painter = rememberAsyncImagePainter(selectedImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(290.dp)
                        .background(
                            Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)

                    )
            }

        LazyRow(modifier = Modifier.padding(16.dp)) {
            items(item.picUrl) { imageUrl ->
                ImageThumnail(
                    imageUrl = imageUrl,
                    isSelected = selectedImage == imageUrl,
                    onClick = { selectedImage = imageUrl }
                )
            } }

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top=10.dp)) {
            Text(text = item.title,
                fontSize = 23.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(end=16.dp)
                )
            Text(text = "$${item.price}",
                fontSize = 22.sp)
        }
        rateBar(rating =item.rating)
        modelSelector(
            models=item.model,
            selectedModelIndex=selectedModelIndex,
            onModelSelected={selectedModelIndex=it}
        )
        Text(text = item.description,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
            )
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
            ) {
            Button(onClick= onAddToCartClick,
                shape=RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.blue)),
                   modifier = Modifier
                       .weight(1f)
                       .padding(8.dp)
                       .height(50.dp)
                ){
               Text(text = "Bye Now", fontSize = 18.sp)
            }
            IconButton(onClick=onCartClick,
               modifier = Modifier.background(colorResource(R.color.light_grey),
                   shape = RoundedCornerShape(10.dp))
                 ) {
                      Icon(painterResource(R.drawable.btn_2),
                          null,
                          tint= Color.Black
                          )
            }
        }
    }

}
@Composable
fun modelSelector(models: ArrayList<String>,selectedModelIndex: Int,onModelSelected:(Int)-> Unit){

    LazyRow(modifier= Modifier.padding(vertical = 8.dp)) {
        itemsIndexed(models){index,model ->
            Box(modifier = Modifier.padding(end = 8.dp)
                .height(48.dp)
                .then(if(index==selectedModelIndex){
                    Modifier.border(
                        1.dp,colorResource(R.color.blue),
                    RoundedCornerShape(10.dp))

                }else{

                    Modifier

                })
                .background(if(index==selectedModelIndex)colorResource(R.color.light_purple)else
                                 colorResource(R.color.light_grey),
                    RoundedCornerShape(10.dp)
                ).clickable{onModelSelected(index)}
                .padding(horizontal=16.dp)
            ){
               Text(text = model, textAlign = TextAlign.Center,
                   fontWeight = FontWeight.Bold,
                   color = if (index==selectedModelIndex) colorResource(R.color.blue)
                   else colorResource(R.color.black),
                   modifier = Modifier.align (Alignment.Center)
                   )
            }
        }
    }
}
@Composable
fun rateBar(rating : Double){
         Row(verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier.padding(top = 16.dp))
{
             Text(text = "Selected Model",
                 fontWeight = FontWeight.Bold,
                 modifier = Modifier.weight(1f)
                 )
             Image(painter = painterResource(R.drawable.star),
                 contentDescription = null,
                 modifier = Modifier.padding(end = 16.dp)
                 )
             Text(text = "$rating Rating", style = MaterialTheme.typography.bodyMedium)


}
}

@Composable
fun ImageThumnail(imageUrl: String, isSelected: Boolean, onClick: () -> Unit) {

    val backColor= if (isSelected) colorResource(R.color.light_purple)else
        colorResource(R.color.light_grey)
    Box(
        modifier = Modifier.padding(4.dp)
            .size(55.dp)
            .then(if (isSelected){
                Modifier.border(1.dp,colorResource(R.color.blue),
                    RoundedCornerShape(10.dp))
            }else{
                Modifier

            }).background(backColor, shape = RoundedCornerShape(10.dp))
            .clickable(onClick=onClick)
            .padding(4.dp)
    ){
     Image(painter = rememberAsyncImagePainter(model = imageUrl),
         contentDescription = null,
         modifier = Modifier.fillMaxSize()
             .padding(4.dp)
         )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    OnlineShopTheme {
        DetailScreen(
            item = ItemsModel(
                title = "Sample Product",
                description = "This is a sample description for the product. It has some features and details that users might be interested in.",
                price = 129.99,
                rating = 4.8,
                picUrl = arrayListOf("https://example.com/pic1.jpg"),
                model = arrayListOf("S", "M", "L", "XL")
            ),
            onBackClick = {},
            onAddToCartClick = {},
            onCartClick = {}
        )
    }
}