package com.example.tanmay.shoppingapp.Activities;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
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

import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;
import com.example.tanmay.shoppingapp.YourCart;

public class ProductPage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

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

        // A new Uri with the the row number appended as "/#" wildcard
        Uri xacv = Uri.withAppendedPath(BaseContract.ProductEntry.CONTENT_URI, getIntent().getStringExtra("itemClicked"));

        prodName = findViewById(R.id.ProductPageProductName);
        prodDesc = findViewById(R.id.ProductPageProductDescription);
        prodPrice = findViewById(R.id.ProductPageProductPrice);
        prodImage = findViewById(R.id.ProductPageProductImage);

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

                values.put(BaseContract.CartEntry._ID, prodID);
                values.put(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, quantity);

                getContentResolver().insert(BaseContract.CartEntry.CONTENT_URI, values);

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
                    quantityBox.setText(Integer.toString(quantity));
                } else
                    Toast.makeText(ProductPage.this, "Cannot order more than 30 products", Toast.LENGTH_SHORT).show();

            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {

                    quantity--;
                    quantityBox.setText(Integer.toString(quantity));

                } else
                    Toast.makeText(ProductPage.this, "Cannot order less than 1 product", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                BaseContract.ProductEntry._ID,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE
        };

        return new CursorLoader(this,
                BaseContract.ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // Prevent execution if new pet is to be added
        if (cursor == null || cursor.getCount() < 1) return;

        if (cursor.moveToFirst()) {

            prodID = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.ProductEntry._ID));
            int prodNameID = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME));
            int prodPriceID = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE));
            int prodDescID = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION));
            int prodImageID = cursor.getInt(cursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE));

            prodName.setText(prodNameID);
            prodDesc.setText(prodDescID);
            prodPrice.setText(prodPriceID);
            prodImage.setImageResource(prodImageID);


        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Reset the text fields
        prodName.setText("");
        prodDesc.setText("");
        prodPrice.setText("");
        // Set the image to the placeholder image
        prodImage.setImageResource(R.drawable.placeholder);
    }

}
