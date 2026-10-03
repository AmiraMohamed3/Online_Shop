package com.example.onlineshop.screens

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.onlineshop.R
import com.example.onlineshop.helper.ChangeNumberItemsListener
import com.example.onlineshop.helper.ManagementCart
import com.example.onlineshop.model.ItemsModel


@Composable

fun CartScreen(managementCart: ManagementCart= ManagementCart(LocalContext.current),onBackClick:()-> Unit={}){

    val cartItem= remember{mutableStateOf(managementCart.getListCart())}
    val tax = remember { mutableStateOf(0.0) }
    clculatorCart(managementCart,tax)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp)) {
        ConstraintLayout(modifier = Modifier.padding(top = 36.dp)) {
            val (backBtn,cartTxt)=createRefs()
            Text(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(cartTxt) { centerTo(parent) },
                text = "Your Cart",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize =25.sp
            )
            Image(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                modifier= Modifier
                    .clickable { onBackClick() }
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                        ) }
        if (cartItem.value.isEmpty()){
            Text(text = "Cart Is Empty",
                modifier = Modifier.align (Alignment.CenterHorizontally))
        }else{
             cartlist(cartItem=cartItem.value,managementCart) {
                 cartItem.value=managementCart.getListCart()
                 clculatorCart(managementCart,tax)
             }
            cartSummary(
                itemTotal = managementCart.getTotalFee(),
                tax=tax.value,
                delivery = 10.0
            )
        }

    }}
fun clculatorCart(
    managementCart: ManagementCart,
    tax: MutableState<Double>
) {
    val percentTax=0.02
    tax.value = Math.round(managementCart.getTotalFee()*percentTax*100)/100.0
}
@Composable
fun cartItem(cartItem: ArrayList<ItemsModel>,item: ItemsModel,
             managementCart: ManagementCart,onItemChange:()-> Unit){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp,)
    ) {
        val (pic,titleTxt,feeEachTime,totalEachItem,Quantity)=createRefs()
        Image(painter = rememberAsyncImagePainter(item.picUrl[0]),
             contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .background(
                    colorResource(R.color.light_grey),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
                .constrainAs(pic) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(text = item.title,
            modifier = Modifier
                .constrainAs(titleTxt) {
                    start.linkTo(pic.end)
                    top.linkTo(pic.top)
                }
                .padding(start = 8.dp, top = 8.dp)
        )
        Text(text = "$${item.price}",color= colorResource(R.color.blue),
            modifier = Modifier
                .constrainAs(feeEachTime) {
            start.linkTo(titleTxt.start)
            top.linkTo(titleTxt.bottom) }
                    .padding(start = 8.dp, top = 16.dp))
        Text(text = "$${item.numberInCart*item.price}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
            ,color= colorResource(R.color.blue),
            modifier = Modifier
                .constrainAs(totalEachItem) {
                    start.linkTo(titleTxt.start)
                    bottom.linkTo(pic.bottom) }
                .padding(start = 8.dp))
        ConstraintLayout(
            modifier = Modifier.width(100.dp)
                .constrainAs(Quantity){
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }.background(colorResource(R.color.light_grey),
            shape=RoundedCornerShape(10.dp))
        ) {
            val (plusCartBtn,minCartBtn,numberItemTxt)=createRefs()
            Text(text = item.numberInCart.toString(), color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(numberItemTxt){
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                )
            Box(modifier = Modifier.padding(2.dp).size(28.dp).background(colorResource(R.color.blue),
                shape = RoundedCornerShape(10.dp))
                .constrainAs(plusCartBtn){
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom) }
                .clickable{managementCart.plusItem(cartItem,cartItem.indexOf(item),object:
                    ChangeNumberItemsListener {
                    override fun onChanged() {
                        onItemChange()
                    }
                })}
            ){
                 Text(text = "+",
                     color = Color.White,
                     modifier = Modifier.align (Alignment.Center),
                     textAlign = TextAlign.Center


                 )
            }
            Box(modifier = Modifier.padding(2.dp).size(28.dp).background(colorResource(R.color.white),
                shape = RoundedCornerShape(10.dp))
                .constrainAs(minCartBtn){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom) }
                .clickable{managementCart.minusItem(cartItem,cartItem.indexOf(item),object:
                    ChangeNumberItemsListener {
                    override fun onChanged() {
                        onItemChange()
                    }
                })}
            ){
                Text(text = "-",
                    color = Color.Black,
                    modifier = Modifier.align (Alignment.Center),
                    textAlign = TextAlign.Center


                )
            }
        }

    }

}
@Composable
fun cartlist(cartItem: ArrayList<ItemsModel>,managementCart: ManagementCart,onItemChange: () -> Unit){
    LazyColumn(Modifier.padding(16.dp))
    {items(cartItem){item->cartItem(cartItem,item=item,
        managementCart= managementCart,onItemChange=onItemChange) } }

}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    val sampleItem = ItemsModel(
        title = "Sample Item",
        description = "this is adescription for sample item 1",
        price = 100.0,
        rating = 4.5,
        numberInCart = 2,
        picUrl = arrayListOf("")
    )
    com.example.onlineshop.ui.theme.OnlineShopTheme {
        cartItem(
            cartItem = arrayListOf(sampleItem),
            item = sampleItem,
            managementCart = ManagementCart(LocalContext.current),
            onItemChange = {}
        )
    }
}
@Composable
fun cartSummary(itemTotal: Double,tax: Double,delivery:Double){
val total = itemTotal + tax + delivery
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
        ) { Text(text ="Item Total:",
            Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = Color.Gray
            )
            Text("$$itemTotal")

        }
        Row(modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
        ) { Text(text ="Tax:",
            Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
            Text("$$tax")

        }
        Row(modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
        ) { Text(text ="Delivery",
            Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
            Text("$$delivery")

        }
        Box(Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorResource(R.color.grey))
            .padding(vertical = 8.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
        ) { Text(text ="Total",
            Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
            Text("$$total")

        }
        Button(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.blue)),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(50.dp)
            )
         {
            Text(text = "Check Out",
                fontSize = 18.sp,
                color = Color.White

            )
         }
    }

}





