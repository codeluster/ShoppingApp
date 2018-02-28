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

        // public static final Uri PRODUCT_NAME_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListPrimary.TABLE_NAME);
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListPrimary.TABLE_NAME);

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ProductListPrimary.TABLE_NAME + " (" +
                        ProductListPrimary._ID + " INTEGER PRIMARY KEY," +
                        ProductListPrimary.COLUMN_NAME_PRODUCT_NAME + " INTEGER)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProductListPrimary.TABLE_NAME;

    }

    public static class ProductListMetaData implements BaseColumns {

        public static final String TABLE_NAME = "productListMetaData";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "price";
        public static final String COLUMN_NAME_PRODUCT_THUMBNAIL = "thumbnail";

        // public static final Uri PRODUCT_PRICE_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListMetaData.COLUMN_NAME_PRODUCT_PRICE);
        //  public static final Uri PRODUCT_THUMBNAIL = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListMetaData.COLUMN_NAME_PRODUCT_THUMBNAIL);
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListMetaData.TABLE_NAME);

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " +
                        ProductListMetaData.TABLE_NAME + " (" +
                        ProductListMetaData._ID + " INTEGER PRIMARY KEY," +
                        ProductListMetaData.COLUMN_NAME_PRODUCT_THUMBNAIL + "INTEGER," +
                        ProductListMetaData.COLUMN_NAME_PRODUCT_PRICE + " INTEGER)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProductListMetaData.TABLE_NAME;

    }

    public static class ProductListProductImages implements BaseColumns {

        public static final String TABLE_NAME = "productListProductImages";
        public static final String COLUMN_NAME_PRODUCT_IMAGE = "image";

      //  public static final Uri PRODUCT_IMAGE = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListProductImages.COLUMN_NAME_PRODUCT_IMAGE);
      public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductListProductImages.TABLE_NAME);

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " +
                        ProductListProductImages.TABLE_NAME + " (" +
                        ProductListProductImages._ID + " INTEGER PRIMARY KEY," +
                        ProductListProductImages.COLUMN_NAME_PRODUCT_IMAGE + " INTEGER)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ProductListMetaData.TABLE_NAME;

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
            sqLiteDatabase.execSQL(ProductListMetaData.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(ProductListPrimary.SQL_DELETE_ENTRIES);
            sqLiteDatabase.execSQL(ProductListMetaData.SQL_DELETE_ENTRIES);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


}
