package com.example.tanmay.shoppingapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

public class CartCursorAdapter extends CursorAdapter {

    public CartCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_cart, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = view.findViewById(R.id.cart_item_product_name);
        TextView quantityTextView = view.findViewById(R.id.cart_item_quantity_multiplier);
        TextView priceTextView = view.findViewById(R.id.cart_item_net_price);

        int productID = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID));

        String[] projection = {
                BaseContract.ProductEntry._ID,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE
        };

        String selection = BaseContract.ProductEntry._ID;

        String[] selectionArgs = {Integer.toString(productID)};

        Cursor product = context.getContentResolver().query(BaseContract.ProductEntry.CONTENT_URI, projection, selection, selectionArgs, null);

        try {
            product.moveToFirst();


            String productName = context.getString(product.getInt(product.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)));
            Integer productPrice = context.getResources().getInteger(product.getInt(product.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
            int quantityOrdered = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));

            nameTextView.setText(productName);
            priceTextView.setText(productPrice.toString());
            quantityTextView.setText("X " + quantityOrdered);

        } catch (NullPointerException e) {
            Log.e(CartCursorAdapter.class.getSimpleName(), "Product cursor NPE");
        }

        if (product != null) {
            product.close();
        }
    }

}
