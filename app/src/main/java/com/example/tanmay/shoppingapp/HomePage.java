package com.example.tanmay.shoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract;
import com.example.tanmay.shoppingapp.DataSet.ProductProvider;

public class HomePage extends AppCompatActivity {

    String TAG = "com.whatever.tag";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page, menu);

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Adding custom toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.homePageToolBar);
        setSupportActionBar(toolbar);

        //Content Provider
        ProductProvider productProvider = new ProductProvider();

        ContentValues values = new ContentValues();
        //The values contains all the data to be entered into the table
        values.put(ProductListContract.ProductListPrimary.COLUMN_NAME_PRODUCT_NAME, R.string.product1Name);
        Log.i(TAG, "onCreate: values.put " + R.string.product1Name + " successful");
        values.put(ProductListContract.ProductListPrimary.COLUMN_NAME_PRODUCT_NAME, R.string.product2Name);

        productProvider.insert(ProductListContract.ProductListPrimary.CONTENT_URI, values);


        //Projection is just the name of the columns we would like to receive
        String[] projection = {

                BaseColumns._ID,
                ProductListContract.ProductListPrimary.COLUMN_NAME_PRODUCT_NAME

        };


        Cursor cursorNew = getContentResolver().query(ProductListContract.ProductListPrimary.CONTENT_URI, projection, null, null, null);

        ListView listView = findViewById(R.id.productList_homepage);
        listView.setAdapter(new productListAdapter(this, cursorNew));


    }

    private class productListAdapter extends CursorAdapter {

        public productListAdapter(Context context, Cursor c) {
            super(context, c);
        }

        //Returns a new blank view
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context).inflate(R.layout.dummy_item, viewGroup, false);
        }

        //Actually responsible for the data binding
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView id = findViewById(R.id.dummy_item_id_box);
            TextView name = findViewById(R.id.dummy_item_name_box);

            id.setText(cursor.getInt(cursor.getColumnIndexOrThrow(ProductListContract.ProductListPrimary._ID)));
            name.setText(getResources().getString(cursor.getInt(cursor.getColumnIndexOrThrow(ProductListContract.ProductListPrimary.COLUMN_NAME_PRODUCT_NAME))));

        }

    }

}
