package com.example.tanmay.shoppingapp.DataSet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.tanmay.shoppingapp.Product;

/**
 * Created by tanmay on 26/2/18.
 */

public class ProductListContract {


    //  Refers to this particular application
    public static final String CONTENT_AUTHORITY = "com.example.tanmay.shoppingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static class ProductListPrimary implements BaseColumns {

        //  Name of the table
        public static final String TABLE_NAME = "productListPrimary";

        //  Names of various columns
        public static final String COLUMN_NAME_PRODUCT_NAME = "name";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "price";
        public static final String COLUMN_NAME_PRODUCT_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_PRODUCT_IMAGE = "image";

        //  Uri pointing to this particular table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListPrimary.TABLE_NAME);

        //  SQL command to create the table
        //  All columns contain integers because they don't contain the actual Strings and Images,
        //      instead they hold the integral resource identifiers (R.string.* / R.integer.* / R.drawable.*).
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ProductListPrimary.TABLE_NAME + " (" +
                        ProductListPrimary._ID + " INTEGER PRIMARY KEY, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_NAME + " INTEGER, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_PRICE + "INTEGER, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_THUMBNAIL + "INTEGER, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_IMAGE + "INTEGER)";

        //  Checks if a particular table already exists and then deletes it.
        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProductListPrimary.TABLE_NAME;

    }


    //  DbHelper is the front-end of the Database
    public static class ProductReaderDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "products.db";

        //  Default Constructor
        public ProductReaderDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            //  Creates the database bu executing String as SQL command
            sqLiteDatabase.execSQL(ProductListPrimary.SQL_CREATE_ENTRIES);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            //  Firsts deletes the old database then creates a new one
            sqLiteDatabase.execSQL(ProductListPrimary.SQL_DELETE_ENTRIES);
            onCreate(sqLiteDatabase);

        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            onUpgrade(db, oldVersion, newVersion);

        }

    }


}
