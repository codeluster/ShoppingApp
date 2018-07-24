package com.example.tanmay.shoppingapp.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BaseContract {

    //Empty constructor to prevent instantiation
    private BaseContract() {
    }

    //  Refers to this particular application
    public static final String CONTENT_AUTHORITY = "com.example.tanmay.shoppingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Refers to particular databases
    public static final String PATH_PRODUCT_LIST = ProductEntry.TABLE_NAME;
    public static final String PATH_CART = CartEntry.TABLE_NAME;

    public static class ProductEntry implements BaseColumns {

        //  Uri pointing to this particular table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, ProductEntry.TABLE_NAME);
        //MIME type
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT_LIST;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT_LIST;

        //  Name of the table
        public static final String TABLE_NAME = "productListPrimary";

        //  Names of various columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_PRODUCT_NAME = "name";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "price";
        public static final String COLUMN_NAME_PRODUCT_THUMBNAIL = "thumbnail";
        public static final String COLUMN_NAME_PRODUCT_IMAGE = "image";
        public static final String COLUMN_NAME_PRODUCT_DESCRIPTION = "description";

    }

    public static class CartEntry implements BaseColumns {

        //Uri pointing to this particular table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, CartEntry.TABLE_NAME);
        //MIME type
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CART;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CART;

        //Name of the table
        public static final String TABLE_NAME = "cart";

        //Name of various columns
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_ORDERED_QUANTITY = "quantity";

    }

}
