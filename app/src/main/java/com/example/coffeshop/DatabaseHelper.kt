package com.example.coffeshop


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "CoffeShop.db"
        private const val TABLE_USER = "sigma228"
        private const val COLUMN_USER_ID = "iduser"
        private const val COLUMN_USER_LOGIN = "login"
        private const val COLUMN_USER_PASSWORD = "password"
        private const val COLUMN_USER_KOINS = "koins"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USER_LOGIN TEXT,"
                + "$COLUMN_USER_PASSWORD TEXT,"
                + "$COLUMN_USER_KOINS INTEGER)")
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun addUser(login: String, password: String, koins: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_LOGIN, login)
        values.put(COLUMN_USER_PASSWORD, password)
        values.put(COLUMN_USER_KOINS, koins)
        val success = db.insert(TABLE_USER, null, values)
        db.close()
        return success
    }

    fun getUser(login: String, password: String): Cursor? {
        val db = this.readableDatabase
        return db.query(TABLE_USER, null, "$COLUMN_USER_LOGIN =? AND $COLUMN_USER_PASSWORD =?", arrayOf(login, password), null, null, null)
    }

    fun updateUserKoins(id: Int, koins: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_KOINS, koins)
        return db.update(TABLE_USER, values, "$COLUMN_USER_ID =?", arrayOf(id.toString()))
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_USER, "$COLUMN_USER_ID =?", arrayOf(id.toString()))
    }
}
