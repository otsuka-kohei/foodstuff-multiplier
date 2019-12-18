package com.example.foodstuff_multiplier

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class FmSQLiteOpenHelper(val context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        // データーベースのバージョン
        private val DATABASE_VERSION = 1

        // データーベース情報を変数に格納
        private val DATABASE_NAME = "json_data.db"
        private val TABLE_NAME = "json_data_db"
        private val ID = "id"
        private val COLUMN_NAME_JSON = "json_data"

        lateinit var memoSQLiteOpenHelper: FmSQLiteOpenHelper

        fun setup(context: Context) {
            if (!canUse) {
                memoSQLiteOpenHelper = FmSQLiteOpenHelper(context)
                db = memoSQLiteOpenHelper.readableDatabase
                canUse = true
            }
        }

        private var canUse = false
        lateinit var db: SQLiteDatabase

        fun appendData(jsonString: String) {
            if (!canUse) {
                return
            }

            val values = ContentValues()
            values.put(COLUMN_NAME_JSON, jsonString)
            
            db.insert(TABLE_NAME, null, values)
        }

        fun readAllData(): List<Triple<Int, String, String>> {
            val list = ArrayList<Triple<Int, String, String>>()

            if (!canUse) {
                return list
            }

            val cursor = db.query(
                TABLE_NAME,
                arrayOf(COLUMN_NAME_JSON),
                null,
                null,
                null,
                null,
                null
            )

            cursor.moveToFirst()

            Log.d("MemoDB", "cursor count : ${cursor.count}")

            for (i in 0 until cursor.count) {
                val memo: String = cursor.getString(0)
                val imageBase64: String = cursor.getString(1)

                list.add(Triple(i, memo, imageBase64))

                cursor.moveToNext()
            }

            cursor.close()

            return list
        }
    }

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${TABLE_NAME} (${ID} INTEGER PRIMARY KEY,${COLUMN_NAME_JSON} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}