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
        private val DATABASE_NAME = "json_serialized.db"
        private val TABLE_NAME = "json_serialized_db"
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

        fun teardown() {
            db.close()
            canUse = false
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

        fun updateData(id: Int, jsonString: String) {
            if (!canUse) {
                return
            }

            val values = ContentValues()
            values.put(COLUMN_NAME_JSON, jsonString)

            db.update(TABLE_NAME, values, "${ID} = ?", arrayOf(id.toString()))
        }

        fun readAllData(): List<Pair<Int, String>> {
            val list = mutableListOf<Pair<Int, String>>()

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
                val jsonString: String = cursor.getString(0)

                list.add(Pair(i, jsonString))

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