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


    public static final String CONTENT_AUTHORITY = "com.example.tanmay.shoppingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static class ProductListPrimary implements BaseColumns {

        public static final String TABLE_NAME = "productListPrimary";
        public static final String COLUMN_NAME_PRODUCT_NAME = "name";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "price";
        public static final String COLUMN_NAME_PRODUCT_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_PRODUCT_IMAGE = "image";

        // public static final Uri PRODUCT_NAME_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListPrimary.TABLE_NAME);
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListPrimary.TABLE_NAME);

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ProductListPrimary.TABLE_NAME + " (" +
                        ProductListPrimary._ID + " INTEGER PRIMARY KEY, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_NAME + " INTEGER, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_PRICE + "INTEGER, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_THUMBNAIL + "INTEGER, " +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_IMAGE + "INTEGER)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProductListPrimary.TABLE_NAME;

    }


    public static class ProductReaderDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "ProductListPrimary.db";

        public ProductReaderDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(ProductListPrimary.SQL_CREATE_ENTRIES);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(ProductListPrimary.SQL_DELETE_ENTRIES);

            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


}
