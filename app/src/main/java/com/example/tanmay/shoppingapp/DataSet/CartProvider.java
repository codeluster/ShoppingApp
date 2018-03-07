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

/**
 * Created by tanma on 07-03-2018.
 */

public class CartProvider extends ContentProvider {

    private static final int CartListTable = 1;
    private static final int CartListRow = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(CartContract.CONTENT_AUTHORITY, CartContract.CartEntry.TABLE_NAME, CartListTable);
        sUriMatcher.addURI(CartContract.CONTENT_AUTHORITY, CartContract.CartEntry.TABLE_NAME + "/#", CartListRow);

    }

    private CartDbHelper mDbHelper;

    @Override
    public boolean onCreate() {

        //Creates a new DbHelper object
        mDbHelper = new CartDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //  Obtain a read-only copy of the database
        SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();

        //  This cursor holds the result from the query.
        Cursor cursor = null;

        //  Switch to perform specific kind of query based on type of Uri
        switch (sUriMatcher.match(uri)) {

            //  Uri demanding entire table with the criteria defined in the fundtion params
            case CartListTable:

                //  All the argumnets are the ones passed
                cursor = sqLiteDatabase.query(CartContract.CartEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            //  Uri demanding a particular row item.
            case CartListRow:

                //  "?" is a wildcard which gets replaced by any integer
                selection = CartContract.CartEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf((ContentUris.parseId(uri)))};

                cursor = sqLiteDatabase.query(CartContract.CartEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            default:
                throw new IllegalArgumentException("Cannot query unkown URI " + uri);

        }

        //Return the cursor containing query results
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case CartListTable:
                return CartContract.CartEntry.CONTENT_LIST_TYPE;

            case CartListRow:
                return CartContract.CartEntry.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unkown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case CartListTable:

                return insertCart(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

    }

    public Uri insertCart(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(CartContract.CartEntry.TABLE_NAME, null, contentValues);

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

