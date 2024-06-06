package com.example.coffeshop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.coffeshop.ui.theme.AppDatabase
import com.example.coffeshop.ui.theme.BrownColor
import com.example.coffeshop.ui.theme.CoffeShopTheme
import com.example.coffeshop.ui.theme.CoffeeColor
import com.example.coffeshop.ui.theme.LogoColor
import com.example.coffeshop.ui.theme.User
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "coffee-shop-database"
        ).build()

        setContent {
            CoffeShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    var isRegistrationScreenVisible by remember { mutableStateOf(false) }

                    if (isRegistrationScreenVisible) {
                        RegistrationScreen(db = db, onBackPressed = { isRegistrationScreenVisible = false })
                    } else {
                        LoginScreen(
                            db = db,
                            onLoginSuccess = {
                                context.startActivity(Intent(context, MainActivity::class.java))
                                (context as Activity).finish()
                            },
                            onRegister = { isRegistrationScreenVisible = true }
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(db: AppDatabase, onBackPressed: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = LogoColor,
                    focusedIndicatorColor = CoffeeColor,
                    unfocusedIndicatorColor = CoffeeColor,
                    focusedTextColor  = CoffeeColor, // Цвет текста
                    cursorColor = CoffeeColor, // Цвет курсора
                    focusedLabelColor = CoffeeColor, // Цвет метки в фокусе
                    unfocusedLabelColor = CoffeeColor // Цвет метки без фокуса
                )

            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
              //  visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = LogoColor,
                    focusedIndicatorColor = CoffeeColor,
                    unfocusedIndicatorColor = CoffeeColor,
                    focusedTextColor  = CoffeeColor, // Цвет текста
                    cursorColor = CoffeeColor, // Цвет курсора
                    focusedLabelColor = CoffeeColor, // Цвет метки в фокусе
                    unfocusedLabelColor = CoffeeColor // Цвет метки без фокуса
                )

            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    scope.launch {
                        val userDao = db.userDao()
                        userDao.insertUser(User(username = username, password = password))
                        Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LogoColor,
                        contentColor = CoffeeColor
                    )

                ) {
                    Text("Зарегистрироваться")
                }
                TextButton(onClick = onBackPressed) {
                    Text("Назад")
                }
            }
        }
    }
}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(db: AppDatabase, onLoginSuccess: () -> Unit, onRegister: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Логин",style = MaterialTheme.typography.bodyLarge) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LogoColor),  // Задний фон синий
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = LogoColor,
                    focusedIndicatorColor = CoffeeColor,
                    unfocusedIndicatorColor = CoffeeColor,
                    focusedTextColor  = CoffeeColor, // Цвет текста
                    cursorColor = CoffeeColor, // Цвет курсора
                    focusedLabelColor = CoffeeColor, // Цвет метки в фокусе
                    unfocusedLabelColor = CoffeeColor // Цвет метки без фокуса
                )
            )
            Spacer(modifier = Modifier.height(16.dp))  // Уменьшение расстояния
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль",style = MaterialTheme.typography.bodyLarge) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LogoColor),  // Задний фон синий
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = LogoColor,
                    focusedIndicatorColor = CoffeeColor,
                    unfocusedIndicatorColor = CoffeeColor,
                    focusedTextColor  = CoffeeColor, // Цвет текста
                    cursorColor = CoffeeColor, // Цвет курсора
                    focusedLabelColor = CoffeeColor, // Цвет метки в фокусе
                    unfocusedLabelColor = CoffeeColor // Цвет метки без фокуса
                )
            )
            Spacer(modifier = Modifier.height(0.dp))  // Уменьшение расстояния
            TextButton(
                onClick = onRegister,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(IntrinsicSize.Min) // Уменьшение высоты гиперссылки
            ) {
                Text(
                    "Зарегистрироваться",
                    fontSize = 16.sp // Уменьшение размера текста гиперссылки
                )
            }
            Spacer(modifier = Modifier.height(0.dp))  // Уменьшение расстояния
            Button(
                onClick = {
                    scope.launch {
                        val userDao = db.userDao()
                        val user = userDao.getUser(username, password)
                        if (user != null) {
                            onLoginSuccess()
                        } else {
                            Toast.makeText(context, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LogoColor,
                    contentColor = CoffeeColor
                )
            ) {
                Text("Войти")
            }
        }
    }
}
