package com.example.onlineshop.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onlineshop.R

@Preview
@Composable
 fun IntroScrecen(onClick:()-> Unit={}) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .verticalScroll(rememberScrollState())
        .padding(16.dp)


    ) {
       Image(
           painter = painterResource(R.drawable.intro_pic),
           null,
            modifier = Modifier.padding(top = 16.dp)
                .fillMaxSize(),
           contentScale = ContentScale.Fit
       )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.subtitle),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
        )

        Button(onClick={onClick()},
            modifier = Modifier
                .padding(horizontal =32.dp, vertical = 16.dp )
                .fillMaxWidth()
                .height(40.dp),
                 colors = ButtonDefaults.buttonColors(colorResource(R.color.blue)),
            shape = RoundedCornerShape(10.dp)
        ) {Text(text = stringResource(R.string.start),
                color = Color.White,
                fontSize = 16.sp)
        }

        Text(
            text = stringResource(id = R.string.signin),
            modifier = Modifier.padding(horizontal=50.dp).padding(bottom = 16.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )

    }
    
}