package com.example.tanmay.shoppingapp.DataSet;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by tanma on 07-03-2018.
 */

public class CartContract {

    //  Refers to this particular application
    public static final String CONTENT_AUTHORITY = "com.example.tanmay.shoppingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Empty constructor to prevent instantiation
    private CartContract() {
    }

    public static class CartEntry implements BaseColumns {

        //Name of the table
        public static final String TABLE_NAME = "cart";

        //Uri pointing to this particular table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, CartEntry.TABLE_NAME);

        //MIME type
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        //Name of various columns
        public static final String _ID = "id";
        public static final String COLUMN_NAME_ORDERED_QUANTITY = "quantity";

    }

}
