package com.example.coffeshop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.coffeshop.ui.theme.BrownColor
import com.example.coffeshop.ui.theme.white
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверка авторизации
        if (!isUserLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

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

    private fun isUserLoggedIn(): Boolean {
        // Здесь должна быть логика проверки авторизации пользователя.
        // Для упрощения примера возвращаем true.
        // В реальном приложении это может быть проверка токена или данных пользователя.
        return true
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
    var isFlipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (isFlipped) 180f else 0f)
    val cardFrontAlpha by animateFloatAsState(if (isFlipped) 0f else 1f)
    val cardBackAlpha by animateFloatAsState(if (isFlipped) 1f else 0f)


    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 0.dp, LocalConfiguration.current.screenHeightDp.dp-12.dp)
                .background(Color.Transparent) // Устанавливаем прозрачный фон для основного Box
        ) {

            Card(
                shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                backgroundColor = MaterialTheme.colorScheme.background, // Задаем белый цвет фона для Card
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, LocalConfiguration.current.screenHeightDp.dp - 12.dp)
                    .align(Alignment.TopCenter) // Центрируем Card внутри Box
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFEFEFE)) // Цвет фона для содержимого
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp
                            )
                        ) // Закругление углов для содержимого
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
                        text = "Адреса кофейн",
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


                    val productCategories = listOf("All", "Category 1", "Category 2", "Category 3")
                    var selectedCategory by remember { mutableStateOf(productCategories.first()) }
                    val products = mapOf(
                        "Category 1" to listOf(
                            Product("Product 1-1", "Description 1-1"),
                            Product("Product 1-2", "Description 1-2")
                        ),
                        "Category 2" to listOf(
                            Product("Product 2-1", "Description 2-1"),
                            Product("Product 2-2", "Description 2-2")
                        ),
                        "Category 3" to listOf(
                            Product("Product 3-1", "Description 3-1"),
                            Product("Product 3-2", "Description 3-2")
                        )
                    )
// Объединяем все продукты в один список для категории "All"
                    val allProducts = products.values.flatten()
                    Column {
                        // Карусель для подзаголовков с товарами
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(productCategories) { category ->
                                Text(
                                    text = category,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            selectedCategory = category
                                        },
                                    color = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        // Пространство между каруселью и карточками
                        Spacer(modifier = Modifier.height(16.dp))

                        // Карточки с товарами
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val selectedProducts =
                                if (selectedCategory == "All") allProducts else products[selectedCategory]
                                    ?: emptyList()
                            items(selectedProducts) { product ->
                                ProductItem(product)
                            }

                            // Отображение сообщения, если нет продуктов для выбранной категории
                            if (selectedProducts.isEmpty()) {
                                item {
                                    Text(
                                        text = "No products available for this category",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                        //ProductList()
                    }
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
                        .padding(top = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ESTETIC COFFEE",
                        color = LogoColor,
                        fontSize = 36.sp,
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .weight(1f) // Отступ справа
                        ) {
                            Text(
                                text = "Кофейку,",
                                color = BrownColor, // Цвет текста
                                fontSize = 24.sp, // Размер текста
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.height(0.dp)) // Adjust this value to control the spacing
                            Text(
                                text = "Худан?", // Текст с именем
                                color = BrownColor, // Цвет текста
                                fontSize = 24.sp, // Размер текста
                                style = MaterialTheme.typography.displayLarge,
                            )
                        }

                        val context = LocalContext.current

                        IconButton(
                            onClick = {
                                val intent = Intent(context, ProfileActivity::class.java)
                                context.startActivity(intent)
                            },
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
                    Spacer(modifier = Modifier.height(20.dp)) // Добавляем пустое пространство ниже карточки
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(350.dp, 160.dp)
                            .graphicsLayer {
                                rotationY = rotation
                                cameraDistance = 12f * density
                                transformOrigin = TransformOrigin.Center
                            }
                            .clickable { isFlipped = !isFlipped }
                            .align(Alignment.CenterHorizontally) // Добавляем выравнивание по центру вертикально
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    color = if (isFlipped) Color.White else Color(0xFF40E0D0),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(1.dp, Color.White, shape = RoundedCornerShape(16.dp))
                        )

                        if (!isFlipped) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "МОЯ КАРТА COFFEE",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.paddingFromBaseline(top = 0.dp)
                                )
                                Text(
                                    text = "кешбек 3%",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.paddingFromBaseline(top = 12.dp)
                                )
                                Text(
                                    text = "0",
                                    color = Color.White,
                                    fontSize = 32.sp,
                                    style = MaterialTheme.typography.labelLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.paddingFromBaseline(top = 12.dp)
                                )
                                var progress by remember { mutableStateOf(0.5f) }

                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = progress,
                                    color = Color.White,
                                    backgroundColor = Color(0xFF149B8A),
                                    modifier = Modifier
                                        .fillMaxWidth(0.6f)
                                        .padding(horizontal = 24.dp)
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                )
                                Text(
                                    text = "До подарка осталось 777",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.paddingFromBaseline(top = 14.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(cardBackAlpha),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Обратная сторона",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


// Пример модели товара
data class Product(
    val name: String,
    val description: String
)

@Composable
fun ProductItem(product: Product) {
    // Здесь определите, как будет выглядеть карточка товара
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp  // Исправлено: передача конкретного значения типа Dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            // Дополнительная информация о продукте
        }
    }
}

