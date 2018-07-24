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

import com.example.tanmay.shoppingapp.Data.Cart.CartContract;
import com.example.tanmay.shoppingapp.Data.Cart.CartDbHelper;
import com.example.tanmay.shoppingapp.Data.ProductList.ProductDbHelper;
import com.example.tanmay.shoppingapp.Data.ProductList.ProductListContract;
import com.example.tanmay.shoppingapp.Data.ProductList.ProductListContract.ProductEntry;

/**
 * Created by tanmay on 28/2/18.
 */

public class DataProvider extends ContentProvider {

    private static final int ProductListTable = 1;
    private static final int ProductListRow = 2;
    private static final int CartListTable = 3;
    private static final int CartListRow = 4;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductEntry.TABLE_NAME, ProductListTable);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductEntry.TABLE_NAME + "/#", ProductListRow);
        sUriMatcher.addURI(CartContract.CONTENT_AUTHORITY, CartContract.CartEntry.TABLE_NAME, CartListTable);
        sUriMatcher.addURI(CartContract.CONTENT_AUTHORITY, CartContract.CartEntry.TABLE_NAME + "/#", CartListRow);

    }

    private ProductDbHelper mProductDbHelper;
    private CartDbHelper mCartDbHelper;

    @Override
    public boolean onCreate() {

        //Creates a new Product List DbHelper object
        mProductDbHelper = new ProductDbHelper(getContext());

        //Creates a new CartDbHelper
        mCartDbHelper = new CartDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //  Obtain a read-only copy of the productlist database
        SQLiteDatabase productListDataBase = mProductDbHelper.getReadableDatabase();

        //  Obtain a read-only copy of the database
        SQLiteDatabase cartDataBase = mCartDbHelper.getReadableDatabase();

        //  This cursor holds the result from the query.
        Cursor cursor = null;

        //  Switch to perform specific kind of query based on type of Uri
        switch (sUriMatcher.match(uri)) {

            //  Uri demanding entire table with the criteria defined in the fundtion params
            case ProductListTable:

                //  All the argumnets are the ones passed
                cursor = productListDataBase.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            //  Uri demanding a particular row item.
            case ProductListRow:

                //  "?" is a wildcard which gets replaced by any integer
                selection = ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf((ContentUris.parseId(uri)))};

                cursor = productListDataBase.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            //  Uri demanding entire table with the criteria defined in the fundtion params
            case CartListTable:

                //  All the argumnets are the ones passed
                cursor = cartDataBase.query(CartContract.CartEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            //  Uri demanding a particular row item.
            case CartListRow:

                //  "?" is a wildcard which gets replaced by any integer
                selection = CartContract.CartEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf((ContentUris.parseId(uri)))};

                cursor = cartDataBase.query(CartContract.CartEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

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

            case ProductListTable:
                return ProductEntry.CONTENT_LIST_TYPE;

            case ProductListRow:
                return ProductEntry.CONTENT_ITEM_TYPE;

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

            case ProductListTable:

                return insertProduct(uri, contentValues);

            case CartListTable:
                return insertCart(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

    }

    //For some reason cart uri falling in the hands o fproductprovider
    public Uri insertCart(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mCartDbHelper.getWritableDatabase();

        long id = db.insert(CartContract.CartEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {

            Log.e("com.whatever.tag", "Failed to insert row for " + uri);

            return null;

        }

        return ContentUris.withAppendedId(uri, id);
    }


    public Uri insertProduct(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = mProductDbHelper.getWritableDatabase();

        long id = db.insert(ProductEntry.TABLE_NAME, null, contentValues);

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
