package com.example.coffeshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeshop.ui.theme.BrownColor
import com.example.coffeshop.ui.theme.CoffeShopTheme
import com.example.coffeshop.ui.theme.CoffeeColor
import com.example.coffeshop.ui.theme.coffeshopTypography

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeShopTheme {
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Изображение сверху по центру
                        Image(
                            painter = painterResource(id = R.drawable.logo1),
                            contentDescription = "Your Image",
                            modifier = Modifier
                                .size(240.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Crop
                        )

                        // Текст и значок под изображением
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Худан,",
                                color = BrownColor,
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier.padding(start = 0.dp)

                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "накопленные баллы: 0",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        // Дополнительная панель LazyColumn
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth()
                                .background(Color.White)
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))

                        ) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp)) // Дополнительный отступ перед первым элементом
                            }
                            itemsIndexed(items) { index, pair ->
                                PairItem(pair.first, pair.second)
                            }
                        }
                    }
                }
            }
        }
    }

    // Пары данных для LazyColumn
    private val items = mutableListOf<Pair<String, String>>().apply {
        repeat(20) { index ->
            add("Пункт ${index + 1}" to "Значение ${index + 1}")
        }
    }

    // Компонент для отображения каждой пары
    @Composable
    fun PairItem(item: String, value: String) {
        Surface(
            color = Color.LightGray,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(120.dp) // Увеличение высоты
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = item, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = value, color = Color.White)
            }
        }
    }
}