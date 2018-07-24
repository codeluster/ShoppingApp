package com.example.tanmay.shoppingapp.Data.ProductList;

import android.content.ContentProvider;
import android.content.UriMatcher;

public class ProductProvider extends ContentProvider {

    private static final int PRODUCTS = 1834;
    private static final int PRODUCTS_ID = 2384;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.PATH_PRODUCT_LIST, PRODUCTS);
        sUriMatcher.addURI(ProductListContract.CONTENT_AUTHORITY, ProductListContract.PATH_PRODUCT_LIST + "/#", PRODUCTS_ID);
    }

    private ProductDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ProductDbHelper(getContext());
        return true;
    }
}
