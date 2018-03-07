package com.example.tanmay.shoppingapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract;

public class ProductPage extends AppCompatActivity {

    TextView prodName;
    TextView prodPrice;
    TextView prodDesc;
    ImageView prodImage;

    int prodID;
    int prodNameID;
    int prodPriceID;
    int prodDescID;
    int prodImageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.homePageToolBar);
        setSupportActionBar(toolbar);

        String[] projection = {

                ProductListContract.ProductEntry._ID,
                ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL,
                ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE,
                ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE,
                ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION

        };

        // A new Uri with the the row number appended as "/#" wildcard
        Uri xacv = Uri.withAppendedPath(ProductListContract.ProductEntry.CONTENT_URI, getIntent().getStringExtra("itemClicked"));

        //Selection and selectionArgs are null becuase they get overriden in the ProductProvider's URI Matcher
        Cursor cursor = getContentResolver().query(xacv, projection, null, null, null);

        cursor.moveToFirst();

        prodID = cursor.getInt(cursor.getColumnIndexOrThrow(ProductListContract.ProductEntry._ID));
        prodNameID = cursor.getInt(cursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME));
        prodPriceID = cursor.getInt(cursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE));
        prodDescID = cursor.getInt(cursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION));
        prodImageID = cursor.getInt(cursor.getColumnIndexOrThrow(ProductListContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE));

        cursor.close();

        prodName = findViewById(R.id.ProductPageProductName);
        prodDesc = findViewById(R.id.ProductPageProductDescription);
        prodPrice = findViewById(R.id.ProductPageProductPrice);
        prodImage = findViewById(R.id.ProductPageProductImage);

        prodName.setText(prodNameID);
        prodDesc.setText(prodDescID);
        prodPrice.setText(prodPriceID);
        prodImage.setImageResource(prodImageID);
    }

}
