package com.example.tanmay.shoppingapp.Adapters;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

        Uri productUri = ContentUris.withAppendedId(BaseContract.ProductEntry.CONTENT_URI, productID);

        Cursor product = context.getContentResolver().query(productUri, projection, null, null, null);


        if (product != null && product.moveToFirst()) {

            String productName = context.getString(product.getInt(product.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)));
            Integer productPrice = context.getResources().getInteger(product.getInt(product.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
            Integer quantityOrdered = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));

            nameTextView.setText(productName);
            priceTextView.setText(productPrice.toString());

            String quantityString = context.getString(R.string.quantity_multiplier);
            quantityTextView.setText(quantityString.concat(quantityOrdered.toString()));

            product.close();
        }
    }

}
