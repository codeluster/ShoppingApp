package com.example.tanmay.shoppingapp.DataSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tanmay.shoppingapp.ProductList;

/**
 * Created by tanmay on 28/2/18.
 */

public class ProductProvider extends ContentProvider {

    ProductListContract.ProductReaderDbHelper productReaderDbHelper;

    private static final int tableproductListPrimary = 1;
    private static final int tableproddutcListMetaData = 2;
    private static final int tableproductImages = 3;

    private static final int tableproductListPrimary_ID = 4;
    private static final int tableproddutcListMetaData_ID = 5;
    private static final int tableproductImages_ID = 6;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListPrimary.TABLE_NAME, tableproductListPrimary);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListMetaData.TABLE_NAME, tableproddutcListMetaData);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListProductImages.TABLE_NAME, tableproductImages);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListPrimary.TABLE_NAME + "/#", tableproductListPrimary_ID);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListMetaData.TABLE_NAME + "/#", tableproddutcListMetaData_ID);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.ProductListProductImages.TABLE_NAME + "/#", tableproductImages_ID);
    }

    @Override
    public boolean onCreate() {

        productReaderDbHelper = new ProductListContract.ProductReaderDbHelper(getContext(), null, null, 1);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
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