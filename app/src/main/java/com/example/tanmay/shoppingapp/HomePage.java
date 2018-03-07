package com.example.tanmay.shoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract.ProductEntry;

public class HomePage extends AppCompatActivity {

    TextView name;
    ImageView thumbnail;


    String TAG = "com.whatever.tag";
    Cursor cursorNew;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.YourCartAppBar){
            startActivity(new Intent(HomePage.this, YourCart.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        SharedPreferences preferences = getSharedPreferences("ApplicationState", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("ApplicationState", MODE_PRIVATE).edit();


        //Adding custom toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.homePageToolBar);
        setSupportActionBar(toolbar);


        if (!preferences.getBoolean("ProductListCreated", false)) {

            insertProduct();

            editor.putBoolean("ProductListCreated", true);
            editor.apply();
        }

        //Projection is just the name of the columns we would like to receive
        String[] projection = {

                ProductEntry._ID,
                ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL,
                ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductEntry.COLUMN_NAME_PRODUCT_PRICE

        };

        cursorNew = getContentResolver().query(ProductEntry.CONTENT_URI, projection, null, null, null);

        ListView listView = findViewById(R.id.productList_homepage);
        listView.setAdapter(new productListAdapter(HomePage.this, cursorNew));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemClicked, long l) {
                Intent intent = new Intent(HomePage.this, ProductPage.class);

                TextView fhu = view.findViewById(R.id.f249873);
                intent.putExtra("itemClicked", fhu.getText().toString());
                startActivity(intent);
            }
        });


    }

    private void insertProduct() {

        ContentValues values = new ContentValues();

        //Product 1
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, R.string.product1Name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, R.integer.product1Price);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, R.drawable.product1thumbnail);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, R.drawable.product1image);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, R.string.product1Description);
        getContentResolver().insert(ProductEntry.CONTENT_URI, values);

        //Product 2
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, R.string.product2Name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, R.integer.product2Price);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, R.drawable.product2thumbnail);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, R.drawable.product2image);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, R.string.product2Description);
        getContentResolver().insert(ProductEntry.CONTENT_URI, values);

        //Product 3
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, R.string.product3Name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, R.integer.product3Price);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, R.drawable.product3thumbnail);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, R.drawable.product3image);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, R.string.product3Description);
        getContentResolver().insert(ProductEntry.CONTENT_URI, values);

    }

    private class productListAdapter extends CursorAdapter {

        public productListAdapter(Context context, Cursor c) {

            super(context, c);

        }

        //Returns a new blank view
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

            Log.i(TAG, "newView: View created");
            View v = LayoutInflater.from(context).inflate(R.layout.product_list_element, viewGroup, false);

            return v;


        }

        //Actually responsible for the data binding
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            name = view.findViewById(R.id.productListElementProductNameTextView);
            thumbnail = view.findViewById(R.id.productListElementImageView);
            TextView id = view.findViewById(R.id.f249873);

            Integer geihl = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry._ID));

            name.setText(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRODUCT_NAME)));
            thumbnail.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL)));
            id.setText(geihl.toString());

        }

    }

}
