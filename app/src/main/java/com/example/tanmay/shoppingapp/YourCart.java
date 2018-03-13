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

    Cursor cart;
    TextView prodName;
    TextView prodPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

        ContentValues values = new ContentValues();

        values.put(CartContract.CartEntry._ID, 2);
        values.put(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, 37);

        getContentResolver().insert(CartContract.CartEntry.CONTENT_URI, values);

        String[] projection = {

                CartContract.CartEntry._ID,
                CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY

        };

        //gets the entire cart
        cart = getContentResolver().query(CartContract.CartEntry.CONTENT_URI, projection, null, null, null);

        ListView cartList = findViewById(R.id.CartListView);
        cartList.setAdapter(new cartAdapter(YourCart.this,cart));

    }

    private class cartAdapter extends CursorAdapter {
        public cartAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

            View v = LayoutInflater.from(context).inflate(R.layout.cart_list_element, viewGroup, false);

            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {


            prodName = view.findViewById(R.id.cartListElementProductNameTextView);
            prodPrice = view.findViewById(R.id.cartListElementProductPriceTextView);

            //Projection is just the name of the columns we would like to receive
            String[] projection = {

                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL,
                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE

            };

            Integer ui = cursor.getInt(cursor.getColumnIndexOrThrow(CartContract.CartEntry._ID));

            String[] hoho = {ui.toString()};

            Cursor cursorNew = getContentResolver().query(ProductListContract.ProductEntry.CONTENT_URI, projection, ProductListContract.ProductEntry._ID, hoho, null);

            prodName.setText(cursorNew.getInt(cursorNew.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)));

            ui = cursorNew.getInt(cursorNew.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE));
            prodPrice.setText(ui.toString());

            cursorNew.close();

        }
    }

}
