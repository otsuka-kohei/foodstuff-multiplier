package com.otk1fd.foodstuff_multiplier

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Foodstuff Multiplier SQLiteOpenHelper
 */

class FmSQLiteOpenHelper(val context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        // データーベースのバージョン
        private const val DATABASE_VERSION = 1

        // データーベース情報を変数に格納
        private const val DATABASE_NAME = "data.db"
        private const val TABLE_NAME = "data"
        private const val ID = "id"
        private const val COLUMN_NAME_DATA_ID = "data_id"
        private const val COLUMN_NAME_JSON = "json_data"

        fun appendData(context: Context, dataId: Int, jsonString: String) {
            val fmSQLiteOpenHelper = FmSQLiteOpenHelper(context)
            val db = fmSQLiteOpenHelper.writableDatabase

            val values = ContentValues()
            values.put(COLUMN_NAME_DATA_ID, dataId)
            values.put(COLUMN_NAME_JSON, jsonString)

            db.insert(TABLE_NAME, null, values)

            db.close()
        }

        fun deleteData(context: Context, dataId: Int) {
            val fmSQLiteOpenHelper = FmSQLiteOpenHelper(context)
            val db = fmSQLiteOpenHelper.writableDatabase

            db.delete(TABLE_NAME, "${COLUMN_NAME_DATA_ID} = ?", arrayOf(dataId.toString()))

            db.close()
        }

        fun updateData(context: Context, dataId: Int, jsonString: String) {
            val fmSQLiteOpenHelper = FmSQLiteOpenHelper(context)
            val db = fmSQLiteOpenHelper.writableDatabase

            val values = ContentValues()
            values.put(COLUMN_NAME_JSON, jsonString)

            db.update(TABLE_NAME, values, "${COLUMN_NAME_DATA_ID} = ?", arrayOf(dataId.toString()))

            db.close()
        }

        fun readAllData(context: Context): List<Pair<Int, String>> {
            val fmSQLiteOpenHelper = FmSQLiteOpenHelper(context)
            val db = fmSQLiteOpenHelper.readableDatabase

            val list = mutableListOf<Pair<Int, String>>()

            val cursor = db.query(
                TABLE_NAME,
                arrayOf(COLUMN_NAME_DATA_ID, COLUMN_NAME_JSON),
                null,
                null,
                null,
                null,
                null
            )

            cursor.moveToFirst()

            Log.d("MemoDB", "cursor count : ${cursor.count}")

            for (i in 0 until cursor.count) {
                val dataId: Int = cursor.getInt(0)
                val jsonString: String = cursor.getString(1)

                list.add(Pair(dataId, jsonString))

                cursor.moveToNext()
            }

            cursor.close()

            db.close()

            return list.toList()
        }

        fun readDataIdList(context: Context): List<Int> {
            val fmSQLiteOpenHelper = FmSQLiteOpenHelper(context)
            val db = fmSQLiteOpenHelper.readableDatabase

            val list = mutableListOf<Int>()

            val cursor = db.query(
                TABLE_NAME,
                arrayOf(COLUMN_NAME_DATA_ID),
                null,
                null,
                null,
                null,
                null
            )

            cursor.moveToFirst()

            Log.d("MemoDB", "cursor count : ${cursor.count}")

            for (i in 0 until cursor.count) {
                val dataId: Int = cursor.getInt(0)

                list.add(dataId)

                cursor.moveToNext()
            }

            cursor.close()

            db.close()

            return list.toList()
        }
    }

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (${ID} INTEGER PRIMARY KEY,${COLUMN_NAME_DATA_ID} INTEGER,${COLUMN_NAME_JSON} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}