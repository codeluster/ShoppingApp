package com.example.tanmay.shoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page, menu);

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.homePageToolBar);
        setSupportActionBar(toolbar);

        ProductListContract.ProductReaderDbHelper productReaderDbHelper = new ProductListContract.ProductReaderDbHelper(this, null, null, 1);
        SQLiteDatabase db = productReaderDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ProductListContract.ProductListPrimary.COLUMN_NAME_PRODUCT_NAME, R.string.product1Name);
        db.insert(ProductListContract.ProductListPrimary.TABLE_NAME, null, values);

        values.put(ProductListContract.ProductListPrimary.COLUMN_NAME_PRODUCT_NAME, R.string.product2Name);
        db.insert(ProductListContract.ProductListPrimary.TABLE_NAME, null, values);

        //Projection is just the name of the columns we would like to receive
        String[] projection = {

                BaseColumns._ID,
                ProductListContract.ProductListPrimary.COLUMN_NAME_PRODUCT_NAME

        };

        Cursor cursorNew = getContentResolver().query(ProductListContract.ProductListPrimary.CONTENT_URI, projection, null, null, null);

    }

}
