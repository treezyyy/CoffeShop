package com.example.coffeshop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.coffeshop.ui.theme.BrownColor
import com.example.coffeshop.ui.theme.white


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
        itemsIndexed(products) { _, product ->
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
        sheetContent = {Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 0.dp, LocalConfiguration.current.screenHeightDp.dp-12.dp)
                .background(Color.Transparent) // Устанавливаем прозрачный фон для основного Box
        ) {

            Card(
                shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18 .dp),
                backgroundColor = MaterialTheme.colorScheme.background, // Задаем белый цвет фона для Card
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, LocalConfiguration.current.screenHeightDp.dp-12.dp)
                    .align(Alignment.TopCenter) // Центрируем Card внутри Box
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFEFEFE)) // Цвет фона для содержимого
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Закругление углов для содержимого
                ) {
                    Text(
                        text = "Акции и новости",
                        modifier = Modifier
                            .padding(16.dp) // Отступ для заголовка
                            .align(Alignment.Start),
                        style = MaterialTheme.typography.bodyLarge, // Выбор стиля для заголовка
                        color = Color.Black // Убедитесь, что текст видим
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Карусель с элементами
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(5) { index ->
                            Box(
                                modifier = Modifier
                                    .size(150.dp, 100.dp)
                                    .background(Color.Black, RoundedCornerShape(8.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Адреса кофеен",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Start),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Прямоугольник для Google Maps
                    val context = LocalContext.current
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 100.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp))
                            .clickable {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("geo:53.226387,44.925847")
                                )
                                context.startActivity(intent)
                            }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProductList()
                }
            }
        }
        },

        sheetPeekHeight = 458.dp,
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
                            color = BrownColor, // Цвет текста
                            fontSize = 16.sp, // Размер текста
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f) // Отступ справа
                        )

                        // Прямоугольник похожий на карту

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
                    Box(
                        modifier = Modifier
                            .paddingFromBaseline(top = 64.dp)
                            .size(350.dp,160.dp)
                            .background(Color(0xFF40E0D0),
                                shape = RoundedCornerShape(16.dp)) // Цвет берюзовой карты
                            .align(Alignment.CenterHorizontally)
                            .border(1.dp,Color.White,
                            shape = RoundedCornerShape(16.dp))
                    )
                    {
                        // Добавьте другие элементы внутри прямоугольника
                        Text(
                            text = "Добавьте здесь текст",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .paddingFromBaseline(top = 48.dp) // Расстояние от базовой линии сверху
                                .align(Alignment.TopCenter) // Выравнивание по центру
                        )
                    }
                }
            }
        }
    )
}