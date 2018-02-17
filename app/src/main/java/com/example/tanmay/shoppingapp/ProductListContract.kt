package com.example.tanmay.shoppingapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by tanmay on 17/2/18.
 */
object ProductListContract {

    //Contains all the names that will
    object ProductListEntry : BaseColumns {

        val TABLE_NAME: String = "ProductList"
        val COMLUMN_NAME_ID: String = "ProductID"
        val COLUMN_NAME_PRODUCT_NAME: String = "Name"
        val COLUMN_NAME_PRODUCT_PRICE: String = "Price"
        val COLUMN_NAME_PRODUCT_IMAGE: String = "Image"
        val COLUMN_NAME_PRODUCT_THUMBNAIL: String = "Thumbnail"
        val COLUMN_NAME_PRODUCT_DESCRIPTION: String = "Description"


    }

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${ProductListEntry.TABLE_NAME} (${ProductListEntry.COMLUMN_NAME_ID} INTEGER PRIMARY KEY, ${ProductListEntry.COLUMN_NAME_PRODUCT_NAME} INTEGER, ${ProductListEntry.COLUMN_NAME_PRODUCT_PRICE} INTEGER, ${ProductListEntry.COLUMN_NAME_PRODUCT_IMAGE} INTEGER, ${ProductListEntry.COLUMN_NAME_PRODUCT_THUMBNAIL} INTEGER, ${ProductListEntry.COLUMN_NAME_PRODUCT_DESCRIPTION} INTEGER ) "

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ProductListEntry.TABLE_NAME}"


    class PetDbHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {


        override fun onCreate(p0: SQLiteDatabase?) {

            p0?.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0?.execSQL(SQL_DELETE_ENTRIES)
            onCreate(p0)
        }

        companion object {
            val DATABASE_VER = 1
            val DATABASE_NAME = "ProductList.db"
        }


    }

}