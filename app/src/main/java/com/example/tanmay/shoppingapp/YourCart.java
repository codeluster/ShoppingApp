package com.example.tanmay.shoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.DataSet.CartContract;
import com.example.tanmay.shoppingapp.DataSet.ProductListContract;

public class YourCart extends AppCompatActivity {

    TextView prodName;
    TextView prodPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

        dummyCart();

        String[] projection = {

                CartContract.CartEntry._ID,
                CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY

        };

        //gets the entire cart
        Cursor cart = getContentResolver().query(CartContract.CartEntry.CONTENT_URI, projection, null, null, null);

        ListView cartList = findViewById(R.id.CartListView);
        cartList.setAdapter(new cartAdapter(YourCart.this, cart));

    }

    private void dummyCart() {

        ContentValues values = new ContentValues();

        values.put(CartContract.CartEntry._ID, 2);
        values.put(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, 7);

        getContentResolver().insert(CartContract.CartEntry.CONTENT_URI, values);

        values.put(CartContract.CartEntry._ID, 3);
        values.put(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, 7);

        getContentResolver().insert(CartContract.CartEntry.CONTENT_URI, values);

        values.put(CartContract.CartEntry._ID, 1);
        values.put(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, 7);

        getContentResolver().insert(CartContract.CartEntry.CONTENT_URI, values);
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

            Integer id = cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry._ID));
            Integer quantity = cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));

            //Running a query to fetch meta-data of product with corresponding id

            //Projection is what columns we want
            String[] projection = {

                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME

            };

            //What is the comparison criteria
            String[] selectionArgs = {

                    id.toString()

            };

            //gets the relevant product
            Cursor prodCursor = getContentResolver().query(ProductListContract.ProductEntry.CONTENT_URI, projection,null,null, null);

            prodCursor.move(id);
            prodName.setText(prodCursor.getInt(prodCursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)));

            prodCursor.close();
        }
    }

}
