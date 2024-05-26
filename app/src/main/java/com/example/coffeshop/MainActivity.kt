package com.example.coffeshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeshop.ui.theme.CoffeShopTheme
import com.example.coffeshop.ui.theme.LogoColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun ProductList() {
    val products = List(50) { "Product $it" }

    LazyColumn(
        modifier = Modifier.fillMaxSize() // Заполнение всей доступной высоты
    ) {
        itemsIndexed(products) {index, product ->
            Text(
                text = product,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier.fillMaxWidth().heightIn(min = 256.dp, max = 600.dp)
            )
            ProductList()
        },
        sheetPeekHeight = 600.dp,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background) // Цвет фона для основного контента
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Estetic Coffee",
                        color = LogoColor,
                        fontSize = 48.sp,
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Кофейку?",
                            color = LogoColor, // Цвет текста
                            fontSize = 16.sp, // Размер текста
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f) // Отступ справа
                        )
                        IconButton(
                            onClick = { /* Действие при нажатии */ },
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 16.dp) // Размер кружка
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Меню аккаунта"
                            )
                        }
                    }
                }
            }
        }
    )
}