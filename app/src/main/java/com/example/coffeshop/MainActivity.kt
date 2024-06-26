package com.example.coffeshop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, LocalConfiguration.current.screenHeightDp.dp - 12.dp)
                    .background(Color.Transparent)
            ) {
                Card(
                    shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    elevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, LocalConfiguration.current.screenHeightDp.dp - 12.dp)
                        .align(Alignment.TopCenter)
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                             .background(Color(0xFFFEFEFE))
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    ) {
                        Text(
                            text = "Акции и новости",
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Start),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(5) { index ->
                                Image(
                                    painter = painterResource(id = R.drawable.skoro), // Укажите свои изображения
                                    contentDescription = "Promo Image $index",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(150.dp, 100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Адреса кофейн",
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Start),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val context = LocalContext.current
                        Image(
                            painter = painterResource(id = R.drawable.maps), // Укажите своё изображение
                            contentDescription = "Coffee Shop Location",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(16.dp)
                                .size(200.dp, 100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("geo:53.226387,44.925847")
                                    )
                                    context.startActivity(intent)
                                }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val productCategories = listOf("All", "Чай", "Кофе", "Десерты")
                        var selectedCategory by remember { mutableStateOf(productCategories.first()) }
                        val products = mapOf(
                            "Чай" to listOf(
                                Pair(Product("Чай черный", "Супер вкусный чай"), R.drawable.blacks),
                                Pair(Product("Чай элгрей", "Ультра вкусный чай"), R.drawable.elgray)
                            ),
                            "Кофе" to listOf(
                                Pair(Product("Капучино", "У нас самый вкусный"), R.drawable.capuchinko),
                                Pair(Product("Солёное", "Специальное предложение"), R.drawable.karamel)
                            ),
                            "Десерты" to listOf(
                                Pair(Product("Макэндчиз", "Чиназесный десерт"), R.drawable.chinazes),
                                Pair(Product("Сникерс", "Сникерсный десерт"), R.drawable.snikers)
                            )
                        )
                        val allProducts = products.values.flatten()
                        var addedProducts by remember { mutableStateOf(listOf<Product>()) }
                        fun handleAddProduct(product: Product) {
                            addedProducts = addedProducts + product
                        }

                        Column {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(0.dp) // Уменьшаем расстояние между категориями
                            ) {
                                items(productCategories) { category ->
                                    val isSelected = selectedCategory == category

                                    Text(
                                        text = category,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clickable {
                                                selectedCategory = category
                                            }
                                            .background(
                                                color = if (isSelected) Color.LightGray else Color.Transparent,
                                                shape = RoundedCornerShape(8.dp) // Добавляем закругление заднего фона
                                            )
                                            .padding(8.dp),
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            val selectedProducts =
                                if (selectedCategory == "All") allProducts else products[selectedCategory]
                                    ?: emptyList()

                            ProductGrid(products = selectedProducts, onAddProduct = ::handleAddProduct)

                            if (selectedProducts.isEmpty()) {
                                Text(
                                    text = "No products available for this category",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black
                                )
                            }
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
                    .background(MaterialTheme.colorScheme.background)
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
                                .weight(1f)
                        ) {
                            Text(
                                text = "Кофейку,",
                                color = BrownColor,
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.height(0.dp))
                            Text(
                                text = "Худан?",
                                color = BrownColor,
                                fontSize = 24.sp,
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
                                .padding(end = 16.dp)
                        ) {
                            Image(
                                //imageVector = Icons.Default.AccountCircle,
                                painter = painterResource(id = R.drawable.icon),
                                contentDescription = "Меню аккаунта"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
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
                            .align(Alignment.CenterHorizontally)
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
                                    text = "-------------------",
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
fun ProductItem(product: Product, imageResId: Int, onAddProduct: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onAddProduct(product) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Add")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid(products: List<Pair<Product, Int>>, onAddProduct: (Product) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { (product, imageResId) ->
            ProductItem(product = product, imageResId = imageResId, onAddProduct = onAddProduct)
        }
    }
}


data class OrderItem(
    val product: Product,
    var quantity: Int
)