package com.example.tanmay.shoppingapp.DataSet;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tanmay on 26/2/18.
 */

public class ProductListContract {

    //  Refers to this particular application
    public static final String CONTENT_AUTHORITY = "com.example.tanmay.shoppingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Empty constructor to prevent instantiation
    private ProductListContract() {}

    public static class ProductEntry implements BaseColumns {

        //  Name of the table
        public static final String TABLE_NAME = "productListPrimary";

        //  Uri pointing to this particular table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductEntry.TABLE_NAME);


        //MIME type
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        //  Names of various columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_PRODUCT_NAME = "name";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "price";
        public static final String COLUMN_NAME_PRODUCT_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_PRODUCT_IMAGE = "image";
        public static final String COLUMN_NAME_PRODUCT_DESCRIPTION = "description";


    }

}
