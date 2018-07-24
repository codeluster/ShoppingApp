package com.example.tanmay.shoppingapp.Data.ProductList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

import static com.example.tanmay.shoppingapp.Data.BaseContract.ProductEntry.*;

/**
 * Created by tanmay on 3/3/18.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "products.db";
    public static final int DATABASE_VERSION = 1;
    private Context context;

    //Default constructor
    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //  SQL command to create the table
    //  All columns contain integers because they don't contain the actual Strings and Images,
    //      instead they hold the integral resource identifiers (R.string.* / R.integer.* / R.drawable.*).
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_PRODUCT_THUMBNAIL + " INTEGER, " +
                    COLUMN_NAME_PRODUCT_NAME + " INTEGER, " +
                    COLUMN_NAME_PRODUCT_PRICE + " INTEGER, " +
                    COLUMN_NAME_PRODUCT_DESCRIPTION + " INTEGER, " +
                    COLUMN_NAME_PRODUCT_IMAGE + " INTEGER);";

    //  Checks if a particular table already exists and then deletes it.
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        //  Creates the database bu executing String as SQL command
        db.execSQL(ProductDbHelper.SQL_CREATE_ENTRIES);

        DatabasePopulater populater = new DatabasePopulater(context);
        populater.populate();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //  Firsts deletes the old database then creates a new one
        sqLiteDatabase.execSQL(ProductDbHelper.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static class DatabasePopulater {

        private Context context;

        private DatabasePopulater(Context context) {
            this.context = context;
        }

        private final int[] PRODUCT_NAMES = context.getResources().getIntArray(R.array.product_Names);
        private final int[] PRODUCT_PRICES = context.getResources().getIntArray(R.array.product_Prices);
        private final int[] PRODUCT_DESCRIPTIONS = context.getResources().getIntArray(R.array.product_Descriptions);
        private final int[] PRODUCT_IMAGES = context.getResources().getIntArray(R.array.product_Images);
        private final int[] PRODUCT_THUMBNAILS = context.getResources().getIntArray(R.array.product_Thumbnails);

        // Insert every product into the database
        private void populate() {

            for (int i = 0; i < PRODUCT_NAMES.length; i++) {

                ContentValues values = new ContentValues();

                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, PRODUCT_NAMES[i]);
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE, PRODUCT_PRICES[i]);
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, PRODUCT_DESCRIPTIONS[i]);
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, PRODUCT_IMAGES[i]);
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, PRODUCT_THUMBNAILS[i]);

                context.getContentResolver().insert(BaseContract.ProductEntry.CONTENT_URI, values);

                values.clear();

            }

        }

    }

}
