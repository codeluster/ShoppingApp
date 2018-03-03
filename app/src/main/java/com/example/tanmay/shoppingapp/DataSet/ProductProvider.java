package com.example.tanmay.shoppingapp.DataSet;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tanmay.shoppingapp.ProductList;

/**
 * Created by tanmay on 28/2/18.
 */

public class ProductProvider extends ContentProvider {

    ProductListContract.ProductReaderDbHelper productReaderDbHelper;

    private static final int ProductListTable = 1;
    private static final int ProductListRow = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListPrimary.TABLE_NAME, ProductListTable);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListPrimary.TABLE_NAME + "/#", ProductListRow);

    }

    @Override
    public boolean onCreate() {

        //Creates a new DbHelper object
        productReaderDbHelper = new ProductListContract.ProductReaderDbHelper(getContext(), null, null, 1);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //  Obtain a read-only copy of the database
        SQLiteDatabase sqLiteDatabase = productReaderDbHelper.getReadableDatabase();

        //  This cursor holds the result from the query.
        Cursor cursor = null;

        //  Switch to perform specific kind of query based on type of Uri
        switch (sUriMatcher.match(uri)) {

            //  Uri demanding entire table with the criteria defined in the fundtion params
            case ProductListTable:

                //  All the argumnets are the ones passed
                cursor = sqLiteDatabase.query(
                        ProductListContract.ProductListPrimary.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;

            //  Uri demanding a particular row item.
            case ProductListRow:

                //  "?" is a wildcard which gets replaced by any integer
                selection = ProductListContract.ProductListPrimary._ID + "=?";
                selectionArgs = new String[]{String.valueOf((ContentUris.parseId(uri)))};

                cursor = sqLiteDatabase.query(

                        ProductListContract.ProductListPrimary.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;

        }

        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        SQLiteDatabase sqLiteDatabase = ProductListContract.ProductReaderDbHelper.

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case ProductListTable:
                return insertProduct(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

    }

    public Uri insertProduct(Uri uri, ContentValues contentValues) {

        ProductListContract.ProductReaderDbHelper mDbHelpwe = new ProductListContract.ProductReaderDbHelper(getContext(), null, null, 1);

        SQLiteDatabase db = mDbHelpwe.getWritableDatabase();

        long id = db.insert(ProductListContract.ProductListPrimary.TABLE_NAME, null, contentValues);

        if (id == -1) {

            Log.e("com.whatever.tag", "Failed to insert row for " + uri);

            return null;

        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
