package com.example.tanmay.shoppingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanmay.shoppingapp.Data.CartContract;
import com.example.tanmay.shoppingapp.Data.ProductListContract;

public class ProductPage extends AppCompatActivity {

    TextView prodName;
    TextView prodPrice;
    TextView prodDesc;
    ImageView prodImage;
    TextView ExpandToggle;
    TextView CollapseToggle;
    Button addToCart;
    Button decrease;
    Button increase;
    EditText quantityBox;

    int prodID;
    int prodNameID;
    int prodPriceID;
    int prodDescID;
    int prodImageID;
    int quantity;
    ImageView favourite;
    Boolean favourited;

    Boolean descExpanded = false;

    //Expand is true when view to be expanded and false when view to be collapsed
    private void toggleDescLength(final Boolean expand) {

        if (expand) {

            prodDesc.setMaxLines(Integer.MAX_VALUE);
            ExpandToggle.setVisibility(View.GONE);
            CollapseToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleDescLength(false);
                }
            });
            CollapseToggle.setVisibility(View.VISIBLE);

        } else {

            prodDesc.setMaxLines(4);
            CollapseToggle.setVisibility(View.GONE);
            ExpandToggle.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page_toolbar, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.YourCartAppBar:
                startActivity(new Intent(ProductPage.this, YourCart.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void favToggle() {

        if (favourited) {

            //unfavourite


        } else {

            //favourite

        }

        //negate the boolean
        favourited = !favourited;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.productPageToolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ExpandToggle = findViewById(R.id.ProductPageDescriptionSeeMore);
        CollapseToggle = findViewById(R.id.ProductPageDescriptionSeeLess);
        addToCart = findViewById(R.id.add_to_cart);
        decrease = findViewById(R.id.productPageQuantityDecrease);
        increase = findViewById(R.id.productPageQuantityIncrease);
        quantityBox = findViewById(R.id.productPageQuantity);

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

        //Expands description on clicking the description text
        prodDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDescLength(true);
            }
        });

        //Expands description by clicking on "SEE MORE"
        ExpandToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDescLength(true);
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                values.put(CartContract.CartEntry._ID, prodID);
                values.put(CartContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, quantity);

                getContentResolver().insert(CartContract.CartEntry.CONTENT_URI, values);

                setResult(RESULT_OK);

                finish();
            }
        });

        quantity = 1;

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity < 30) {
                    quantity++;
                    quantityBox.setText("" + quantity);
                } else {

                    Toast.makeText(ProductPage.this, "Cannot order more than 30 products", Toast.LENGTH_SHORT).show();

                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {

                    quantity--;
                    quantityBox.setText("" + quantity);

                } else {

                    Toast.makeText(ProductPage.this, "Cannot order less than 1 product", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
