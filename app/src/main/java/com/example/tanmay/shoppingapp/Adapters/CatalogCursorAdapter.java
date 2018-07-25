package com.example.tanmay.shoppingapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

public class CatalogCursorAdapter extends CursorAdapter {

    public CatalogCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_catalog, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = view.findViewById(R.id.catalog_item_product_name);
        ImageView thumbnail = view.findViewById(R.id.catalog_item_product_thumbnail);

        int nameColumnIndex = cursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME);
        int thumbnailColumnIndex = cursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL);

        name.setText(cursor.getInt(nameColumnIndex));
        thumbnail.setImageResource(cursor.getInt(thumbnailColumnIndex));

    }

}
