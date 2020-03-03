package com.ankit.databasetest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

/**
 * Created by Parsania Hardik on 11/01/2016.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // looping through all rows and adding to list
    // adding to Students list
    val allUserList: ArrayList<String>
        get() {
            val userArrayList = ArrayList<String>()
            var name = ""
            val selectQuery = "SELECT  * FROM $TABLE_USERS"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    name = c.getString(c.getColumnIndex(KEY_FIRSTNAME))
                    userArrayList.add(name)
                } while (c.moveToNext())
                Log.d("array", userArrayList.toString())
            }
            return userArrayList
        }

    init {

        Log.d("table", CREATE_TABLE_USERS)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_USERS'")
        onCreate(db)
    }

    fun addUserDetail(student: String): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_FIRSTNAME, student)
        // insert row in students table

        return db.insert(TABLE_USERS, null, values)
    }

    companion object {

        var DATABASE_NAME = "user_database"
        private val DATABASE_VERSION = 1
        private val TABLE_USERS = "users"
        private val KEY_ID = "id"
        private val KEY_FIRSTNAME = "firstname"
        private val KEY_LASTNAME = "lastname"
        private val KEY_EMAIL = "email"
        private val KEY_PASSWORD = "password"
        private val KEY_QUESTION = "question"
        private val KEY_ANSWER = "answer"

        /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/
        private val CREATE_TABLE_USERS = ("CREATE TABLE "
                + TABLE_USERS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT," + KEY_EMAIL + " TEXT"
                + KEY_PASSWORD + " TEXT," + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER + " TEXT);")
                //+
    }
}