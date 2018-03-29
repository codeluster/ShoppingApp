package com.example.tanmay.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tanmay.shoppingapp.DataSet.CartContract;
import com.example.tanmay.shoppingapp.DataSet.ProductListContract;

public class YourCart extends AppCompatActivity {

    TextView prodName;
    TextView prodPrice;
    TextView prodQuantity;
    Cursor prodCursor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.your_cart_toolbar, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.clearCart:
                return true;
            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.YourCartToolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getSharedPreferences("UserInformation", MODE_PRIVATE);

        if (preferences.getString("FirstName", null) != null) {
            toolbar.setTitle(preferences.getString("FirstName", null) + "'s Cart");
        }

        String[] projection = {

                CartContract.CartEntry._ID,
                CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY

        };

        //gets the entire cart
        Cursor cart = getContentResolver().query(CartContract.CartEntry.CONTENT_URI, projection, null, null, null);

        if (cart.getCount() == 0) {

            TextView title = findViewById(R.id.cart_empty_title);
            TextView text = findViewById(R.id.cart_empty_text);

            title.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);

        }

        //getSubtotal(cart, prodCursor);

        ListView cartList = findViewById(R.id.CartListView);
        cartList.setVisibility(View.VISIBLE);
        cartList.setAdapter(new cartAdapter(YourCart.this, cart));

    }

    private int getSubtotal(Cursor cart, Cursor productList) {

        int subtotal = 0;

        int cartCounter = 1;

        //Travereses the cart
        while (!cart.isLast()) {

            cart.move(cartCounter);
            int quan = cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));

            //Traverses the productList
            //Moves to location as given by the cart ID field
            productList.move(cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry._ID)));

            subtotal += quan * getResources().getInteger(productList.getInt(productList.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));

            cartCounter++;
        }

        return subtotal;
    }

    private class cartAdapter extends CursorAdapter {

        public cartAdapter(Context context, Cursor c) {
            super(context, c);
        }

        //Returns a new blank view
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

            View v = LayoutInflater.from(context).inflate(R.layout.cart_list_element, viewGroup, false);

            return v;
        }

        //Actually responsible for data binding
        @Override
        public void bindView(View view, Context context, Cursor cart) {

            prodName = view.findViewById(R.id.cartListElementProductNameTextView);
            prodPrice = view.findViewById(R.id.cartListElementProductPriceTextView);
            prodQuantity = view.findViewById(R.id.cart_quantity_multiplier);


            //Projection is what columns we want
            String[] projectionX = {

                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE

            };

            //gets the name and price of everyhting in productList
            prodCursor = getContentResolver().query(ProductListContract.ProductEntry.CONTENT_URI, projectionX, null, null, null);


            //get ID of cart element
            Integer id = cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry._ID));
            //get quantity ordered
            int quantity = cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));

            //move the productCursor to the location corresponding to cart id
            prodCursor.move(id);

            //get the price of one product
            int price = getResources().getInteger(prodCursor.getInt(prodCursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));

            //multiply it by number of products ordrered
            int netPrice = price * quantity;

            //set the name of the product
            prodName.setText(prodCursor.getInt(prodCursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)));

            //set the price
            prodPrice.setText("" + netPrice);

            //set the quantity multiplier
            prodQuantity.setText("X " + quantity);


        }

    }

}
