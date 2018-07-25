package com.example.tanmay.shoppingapp.Data.Cart;

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

import com.example.tanmay.shoppingapp.Data.BaseContract;

public class CartProvider extends ContentProvider {

    private static final int CART = 3731;
    private static final int CART_ID = 5464;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(BaseContract.CartEntry.CART_CONTENT_AUTHORITY, BaseContract.CartEntry.TABLE_NAME, CART);
        sUriMatcher.addURI(BaseContract.CartEntry.CART_CONTENT_AUTHORITY, BaseContract.CartEntry.TABLE_NAME + "/#", CART_ID);
    }

    private CartDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        //Creates a new CartDbHelper
        mDbHelper = new CartDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //  Obtain a read-only copy of the database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        //  This cursor holds the result from the query.
        Cursor cursor;

        //  Switch to perform specific kind of query based on type of Uri
        switch (sUriMatcher.match(uri)) {

            //  Uri demanding entire table with the criteria defined in the function params
            case CART:

                //  All the argumnets are the ones passed
                cursor = database.query(BaseContract.CartEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            //  Uri demanding a particular row item.
            case CART_ID:
                //  "?" is a wildcard which gets replaced by any integer
                selection = BaseContract.CartEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf((ContentUris.parseId(uri)))};
                cursor = database.query(BaseContract.CartEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException(invalidUriErrorGenerator(uri));
        }

        // Notifies the calling content resolver of change
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        //Return the cursor containing query results
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CART:
                return BaseContract.CartEntry.CONTENT_LIST_TYPE;
            case CART_ID:
                return BaseContract.CartEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException(invalidUriErrorGenerator(uri));
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        if (sUriMatcher.match(uri) != CART) {
            throw new IllegalArgumentException(invalidUriErrorGenerator(uri));
        }

        return insertCart(uri, contentValues);

    }

    //For some reason cart uri falling in the hands o fproductprovider
    public Uri insertCart(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(BaseContract.CartEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e(CartProvider.class.getSimpleName(), rowInsertFailedErrorGenerator(uri));
        }

        // Notify about change
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        switch (sUriMatcher.match(uri)) {

            case CART:
                int numDeletedRows = deleteItem(s, strings);
                if (numDeletedRows != 0) getContext().getContentResolver().notifyChange(uri, null);
                return numDeletedRows;
            case CART_ID:
                s = BaseContract.CartEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                int numDeletedRowss = deleteItem(s, strings);
                if (numDeletedRowss != 0) getContext().getContentResolver().notifyChange(uri, null);
                return numDeletedRowss;
            default:
                throw new IllegalArgumentException(invalidUriErrorGenerator(uri));

        }

    }

    private int deleteItem(String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        return database.delete(BaseContract.CartEntry.TABLE_NAME, selection, selectionArgs);

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int NO_UPDATE = -1;

        if (values.size() == 0) return NO_UPDATE;

        switch (sUriMatcher.match(uri)) {
            case CART:
                int numItemsUpdated = updateItem(values, selection, selectionArgs);
                if (numItemsUpdated != 0) getContext().getContentResolver().notifyChange(uri, null);
                return numItemsUpdated;
            case CART_ID:
                selection = BaseContract.CartEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                int numItemsUpdatedd = updateItem(values, selection, selectionArgs);
                if (numItemsUpdatedd != 0)
                    getContext().getContentResolver().notifyChange(uri, null);
                return numItemsUpdatedd;
            default:
                throw new IllegalArgumentException(invalidUriErrorGenerator(uri));
        }
    }

    private int updateItem(ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        return database.update(BaseContract.CartEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    // Generates error message to throw in exception
    private String invalidUriErrorGenerator(Uri uri) {
        return "The provided Uri " + uri.toString() + " is not valid";
    }

    private String rowInsertFailedErrorGenerator(Uri uri) {
        return "Failed to insert row for " + uri.toString();
    }

}
