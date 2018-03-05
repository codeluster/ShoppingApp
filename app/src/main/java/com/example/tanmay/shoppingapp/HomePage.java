package com.example.tanmay.shoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract.ProductEntry;

public class HomePage extends AppCompatActivity {

    TextView idBox;
    TextView nameBox;

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

        insertProduct();


        //Projection is just the name of the columns we would like to receive
        String[] projection = {

                ProductEntry._ID,
                ProductEntry.COLUMN_NAME_PRODUCT_NAME

        };

        Cursor cursorNew = getContentResolver().query(ProductEntry.CONTENT_URI, projection, null, null, null);
        cursorNew.moveToNext();

         int uweh = cursorNew.getInt(cursorNew.getColumnIndex(ProductEntry.COLUMN_NAME_PRODUCT_NAME));
        TextView coco = findViewById(R.id.e83957);
        coco.setText(uweh);

        ListView listView = findViewById(R.id.productList_homepage);
        listView.setAdapter(new productListAdapter(HomePage.this, cursorNew));

    }

    private void insertProduct() {

        ContentValues values = new ContentValues();

        //The values contains all the data to be entered into the table
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, R.string.product1Name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, R.integer.product1Price);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, R.drawable.product1thumbnail);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, R.drawable.product1image);

        Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);

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

            idBox = (TextView) findViewById(R.id.dummy_item_id_box);
            nameBox = (TextView) findViewById(R.id.dummy_item_name_box);

        }

    }

}
