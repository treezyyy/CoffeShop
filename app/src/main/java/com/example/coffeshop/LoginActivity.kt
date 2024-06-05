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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.coffeshop.ui.theme.AppDatabase
import com.example.coffeshop.ui.theme.CoffeShopTheme
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
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
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
                }) {
                    Text("Зарегистрироваться")
                }
                TextButton(onClick = onBackPressed) {
                    Text("Назад")
                }
            }
        }
    }
}

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
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    val userDao = db.userDao()
                    val user = userDao.getUser(username, password)
                    if (user != null) {
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text("Войти")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onRegister) {
                Text("Зарегистрироваться")
            }
        }
    }
}
