package com.example.tanmay.shoppingapp.DataSet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.tanmay.shoppingapp.DataSet.CartContract.CartEntry.TABLE_NAME;
import static com.example.tanmay.shoppingapp.DataSet.CartContract.CartEntry._ID;
import static com.example.tanmay.shoppingapp.DataSet.CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY;

/**
 * Created by tanma on 07-03-2018.
 */

public class CartDbHelper extends SQLiteOpenHelper {

    //Name of the databade file
    public static final String DATABASE_NAME = "cart.db";

    //Database version to be incremented on change in schema
    public static final int DATABASE_VERSION = 1;
    //SQL command to create the table
    //All columns contain integers because they contain ID and quantity
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    _ID + " INTEGER NOT NULL, " +
                    COLUMN_NAME_ORDERED_QUANTITY + " INTEGER)";

    //Checks if  a particular table already exists and then deletes it
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    //Default constructor
    public CartDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //  Creates the database bu executing String as SQL command
        sqLiteDatabase.execSQL(CartDbHelper.SQL_CREATE_ENTRIES);

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
