package com.example.onlineshop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.onlineshop.helper.ManagementCart
import com.example.onlineshop.model.ItemsModel
import com.example.onlineshop.screens.CartScreen
import com.example.onlineshop.screens.DetailScreen
import com.example.onlineshop.screens.IntroScrecen
import com.example.onlineshop.screens.MainScreen
import com.example.onlineshop.screens.listItemScreen
import com.example.onlineshop.ui.theme.OnlineShopTheme
import com.example.onlineshop.viewmodel.MainViewModel
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           val navController = rememberNavController()
            NavHost(navController=navController, startDestination ="intro")
            { composable("intro"){
                IntroScrecen(onClick={
                    navController.navigate("main"){
                     popUpTo("intro"){inclusive=true}
                    }


                })
            }
           composable ("main"){
               MainScreen(
                   onCartClick={
                       navController.navigate("cart")
                   },
                   onCategoryClick = {categoryId,categoryName ->
                     navController.navigate("listItems/$categoryId/$categoryName")
                   }, onItemClick = { item ->
                       val json= Gson().toJson(item)
                       val encodeJson= URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                       navController.navigate("detail/$encodeJson")

                   }
               )

           }
                composable("listItems/{categoryId}/{title}",
                    arguments= listOf(
                        navArgument("categoryId"){ type= NavType.StringType },
                        navArgument("title"){type= NavType.StringType}
                        ))
                    {backStackEntry->
                    val categoryId= backStackEntry.arguments?.getString("categoryId")?:""
                    val categoryName= backStackEntry.arguments?.getString("title")?:""
                    listItemScreen(
                        title=categoryName,
                        onBackClick = {navController.popBackStack()},
                        viewModel = MainViewModel(),
                        id=categoryId,
                        onItemClick = {item ->
                            val json= Gson().toJson(item)
                            val encodeJson= URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                            navController.navigate("detail/$encodeJson")
                        }
                    )
                }
              composable( "detail/{itemJson}",
                  arguments=listOf(navArgument("itemJson"){type= NavType.StringType})
                  ){backStackEntry->
                  val encodeJson=backStackEntry.arguments?.getString("itemJson")?:""
                  val json= URLDecoder.decode(encodeJson, StandardCharsets.UTF_8.toString())
                  val item= Gson().fromJson(json, ItemsModel::class.java)
                  DetailScreen(
                      item=item,
                      onBackClick = { navController.popBackStack()},
                      {item.numberInCart=1
                          ManagementCart(this@MainActivity).insertItem(item)
                      },
                      onCartClick = {
                          navController.navigate("cart")
                      }
                  )
              }
                composable("cart"){

                    CartScreen (onBackClick ={
                        navController.popBackStack()

                    })
                }
            }
        }
    }
}


