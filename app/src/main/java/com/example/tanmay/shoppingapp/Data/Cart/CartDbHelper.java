package com.example.tanmay.shoppingapp.Data.Cart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.tanmay.shoppingapp.Data.BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY;
import static com.example.tanmay.shoppingapp.Data.BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID;
import static com.example.tanmay.shoppingapp.Data.BaseContract.CartEntry.TABLE_NAME;
import static com.example.tanmay.shoppingapp.Data.BaseContract.CartEntry._ID;

/**
 * Created by tanmay on 07-03-2018.
 */

public class CartDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 1;

    //Default constructor
    public CartDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //SQL command to create the table
    //All columns contain integers because they contain ID and quantity
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME_PRODUCT_ID + " INTEGER, " +
            COLUMN_NAME_ORDERED_QUANTITY + " INTEGER);";

    //Checks if  a particular table already exists and then deletes it
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        //  Creates the database by executing String as SQL command
        db.execSQL(CartDbHelper.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //  Firsts deletes the old database then creates a new one
        sqLiteDatabase.execSQL(CartDbHelper.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
