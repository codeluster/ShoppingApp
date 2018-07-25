package com.example.tanmay.shoppingapp.Data.ProductList;

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

public class ProductProvider extends ContentProvider {

    private static final int PRODUCTS = 1834;
    private static final int PRODUCTS_ID = 2384;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(BaseContract.ProductEntry.PRODUCT_CONTENT_AUTHORITY, BaseContract.PATH_PRODUCT_LIST, PRODUCTS);
        sUriMatcher.addURI(BaseContract.ProductEntry.PRODUCT_CONTENT_AUTHORITY, BaseContract.PATH_PRODUCT_LIST + "/#", PRODUCTS_ID);
    }

    private ProductDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        // instantiate the database
        mDbHelper = new ProductDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case PRODUCTS:
                cursor = database.query(BaseContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCTS_ID:
                selection = BaseContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(BaseContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(invalidUriErrorGenerator(uri));
        }

        // Notifies the calling content resolver of change
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case PRODUCTS:
                return BaseContract.ProductEntry.CONTENT_LIST_TYPE;
            case PRODUCTS_ID:
                return BaseContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException(invalidUriErrorGenerator(uri));
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        if (sUriMatcher.match(uri) != PRODUCTS) {
            throw new IllegalArgumentException(invalidUriErrorGenerator(uri));
        }

        return insertProduct(uri, contentValues);

    }

    private Uri insertProduct(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long rowID = database.insert(BaseContract.ProductEntry.TABLE_NAME, null, values);

        if (rowID == -1) {
            Log.e(ProductProvider.class.getSimpleName(), rowInsertFailedErrorGenerator(uri));
        }

        // Notify about change
        getContext().getContentResolver().notifyChange(uri, null);

        // return the uri of inserted row
        return ContentUris.withAppendedId(uri, rowID);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (sUriMatcher.match(uri)) {

            case PRODUCTS:
                int numDeletedRows = deleteItem(selection, selectionArgs);
                if (numDeletedRows != 0) getContext().getContentResolver().notifyChange(uri, null);
                return numDeletedRows;
            case PRODUCTS_ID:
                selection = BaseContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                int numDeletedRowss = deleteItem(selection, selectionArgs);
                if (numDeletedRowss != 0) getContext().getContentResolver().notifyChange(uri, null);
                return numDeletedRowss;
            default:
                throw new IllegalArgumentException(invalidUriErrorGenerator(uri));
        }

    }

    private int deleteItem(String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        return database.delete(BaseContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int NO_UPDATE = -1;

        if (values.size() == 0) return NO_UPDATE;

        switch (sUriMatcher.match(uri)) {
            case PRODUCTS:
                int numItemsUpdated = updateItem(values, selection, selectionArgs);
                if (numItemsUpdated != 0) getContext().getContentResolver().notifyChange(uri, null);
                return numItemsUpdated;
            case PRODUCTS_ID:
                selection = BaseContract.ProductEntry._ID + "=?";
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
        return database.update(BaseContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    // Generates error message to throw in exception
    private String invalidUriErrorGenerator(Uri uri) {
        return "The provided Uri " + uri.toString() + " is not valid";
    }

    private String rowInsertFailedErrorGenerator(Uri uri) {
        return "Failed to insert row for " + uri.toString();
    }

}
