package com.example.tanmay.shoppingapp;

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
    TextView serialNum;
    TextView prodQuantity;
    int snum;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

        snum = 1;
        total = 0;

        String[] projection = {

                CartContract.CartEntry._ID,
                CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY

        };

        //gets the entire cart
        Cursor cart = getContentResolver().query(CartContract.CartEntry.CONTENT_URI, projection, null, null, null);

        ListView cartList = findViewById(R.id.CartListView);
        cartList.setAdapter(new cartAdapter(YourCart.this, cart));

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
            serialNum = view.findViewById(R.id.cart_serial_number);
            prodQuantity = view.findViewById(R.id.cart_quantity_multiplier);

            Integer id = cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry._ID));
            int quantity = cart.getInt(cart.getColumnIndexOrThrow(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));

            //Running a query to fetch meta-data of product with corresponding id

            //Projection is what columns we want
            String[] projectionX = {

                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                    ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE

            };

            //What is the comparison criteria
            String[] selectionArgsX = {

                    id.toString()

            };

            //gets the relevant product
            Cursor prodCursor = getContentResolver().query(ProductListContract.ProductEntry.CONTENT_URI, projectionX, null, null, null);


            prodCursor.move(id);

            int price = getResources().getInteger(prodCursor.getInt(prodCursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
            int netPrice = price * quantity;
            prodName.setText(prodCursor.getInt(prodCursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)));
            prodPrice.setText("" + netPrice);
            serialNum.setText(snum + ".");
            prodQuantity.setText("X " + quantity);
            snum++;
            total += netPrice;
            prodCursor.close();

        }

    }

}
