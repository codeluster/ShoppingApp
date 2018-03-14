package com.example.tanmay.shoppingapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract;

public class ProductPage extends AppCompatActivity {

    TextView prodName;
    TextView prodPrice;
    TextView prodDesc;
    ImageView prodImage;
    TextView ExpandToggle;
    FloatingActionButton fab;

    int prodID;
    int prodNameID;
    int prodPriceID;
    int prodDescID;
    int prodImageID;
    Boolean descExpanded = false;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Toolbar toolbar = findViewById(R.id.homePageToolBar);
        ActionBar actionBar = ProductPage.this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ExpandToggle = findViewById(R.id.ProductPageDescriptionSeeMore);
        fab = findViewById(R.id.add_to_cart);

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

        //Selection and selectionArgs are null because they get overridden in the DataProvider's URI Matcher
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

        textToggle();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //Sets an OnClickListener which expands or collapses the description
    private void textToggle() {

        ExpandToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descToggler(false);
            }
        });

        prodDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descToggler(true);
            }
        });

    }

    private void descToggler(Boolean initiated_by_descBox) {

        //Expand the TextView
        if (descExpanded == false) {

            prodDesc.setMaxLines(Integer.MAX_VALUE);
            descExpanded = !descExpanded;
            ExpandToggle.setText("See Less");

            //Collapses the TextView
        } else if (descExpanded == true && !initiated_by_descBox) {

            prodDesc.setMaxLines(4);
            descExpanded = !descExpanded;
            ExpandToggle.setText("See More");

        }

    }

}
