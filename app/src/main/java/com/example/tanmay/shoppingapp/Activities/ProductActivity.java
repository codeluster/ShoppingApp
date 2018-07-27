package com.example.tanmay.shoppingapp.Activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

public class ProductActivity extends AppCompatActivity {

    ScrollView parentView;
    TextView prodPrice;
    TextView prodDesc;
    ImageView prodImage;
    TextView ExpandToggle;
    TextView CollapseToggle;
    Button addToCart;
    Button decrease;
    Button increase;
    EditText quantityBox;
    int productID;

    int quantity;

    private Uri currentProductUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        // Get the intent passed on item click in catalog activity
        Intent intent = getIntent();
        currentProductUri = intent.getData();

        ExpandToggle = findViewById(R.id.ProductPageDescriptionSeeMore);
        CollapseToggle = findViewById(R.id.ProductPageDescriptionSeeLess);
        addToCart = findViewById(R.id.add_to_cart);
        decrease = findViewById(R.id.productPageQuantityDecrease);
        increase = findViewById(R.id.productPageQuantityIncrease);
        quantityBox = findViewById(R.id.productPageQuantity);
        parentView = findViewById(R.id.activity_product_parent_scroll_view);

        prodDesc = findViewById(R.id.ProductPageProductDescription);
        prodPrice = findViewById(R.id.ProductPageProductPrice);
        prodImage = findViewById(R.id.ProductPageProductImage);

        String[] projection = {
                BaseContract.ProductEntry._ID,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE
        };

        Cursor currentProductCursor = getContentResolver().query(currentProductUri, projection, null, null, null);

        if (currentProductCursor.moveToFirst()) {
            productID = currentProductCursor.getInt(currentProductCursor.getColumnIndexOrThrow(BaseContract.ProductEntry._ID));
            setTitle(getString(currentProductCursor.getInt(currentProductCursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME))));
            prodDesc.setText(currentProductCursor.getInt(currentProductCursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION)));
            prodPrice.setText(currentProductCursor.getInt(currentProductCursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
            prodImage.setImageResource(currentProductCursor.getInt(currentProductCursor.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE)));
        }

        currentProductCursor.close();

        //Expands description on clicking the description text
        prodDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDescriptionLength(true);
            }
        });

        //Expands description by clicking on "SEE MORE"
        ExpandToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDescriptionLength(true);
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
                Snackbar.make(parentView, "Added to Cart", Snackbar.LENGTH_SHORT).show();
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
                    Toast.makeText(ProductActivity.this, "Cannot order more than 30 products", Toast.LENGTH_SHORT).show();

            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {

                    quantity--;
                    quantityBox.setText(Integer.toString(quantity));

                } else
                    Toast.makeText(ProductActivity.this, "Cannot order less than 1 product", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Expand is true when view to be expanded and false when view to be collapsed
    private void toggleDescriptionLength(final Boolean expand) {

        if (expand) {

            prodDesc.setMaxLines(Integer.MAX_VALUE);
            ExpandToggle.setVisibility(View.GONE);
            CollapseToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleDescriptionLength(false);
                }
            });
            CollapseToggle.setVisibility(View.VISIBLE);

        } else {

            prodDesc.setMaxLines(4);
            CollapseToggle.setVisibility(View.GONE);
            ExpandToggle.setVisibility(View.VISIBLE);

        }

    }

    private void addToCart() {

        String[] projection = {
                BaseContract.CartEntry._ID,
                BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID,
                BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY
        };

        String selection = BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID + "=?";

        String[] selectionArgs = {String.valueOf(productID)};

        // Get a cursor requesting an existing entry of current product
        Cursor cart = getContentResolver().query(BaseContract.CartEntry.CONTENT_URI, projection, selection, selectionArgs, null);

        // If cart already has an entry
        // update it's quantity
        if (cart != null && cart.moveToFirst()) {

            int cartQuantity = cart.getInt(cart.getColumnIndexOrThrow(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));
            int cartID = cart.getInt(cart.getColumnIndexOrThrow(BaseContract.CartEntry._ID));
            Uri cartUri = ContentUris.withAppendedId(BaseContract.CartEntry.CONTENT_URI, cartID);

            ContentValues values = new ContentValues();
            values.put(BaseContract.CartEntry._ID, cartID);
            values.put(BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID, productID);
            values.put(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, cartQuantity + quantity);

            getContentResolver().update(cartUri, values, null, null);

            values.clear();

        } else {

            // If cart doesn't already have this product
            // add a new entry

            ContentValues values = new ContentValues();

            values.put(BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID, productID);
            values.put(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, quantity);

            getContentResolver().insert(BaseContract.CartEntry.CONTENT_URI, values);

            values.clear();

        }

        cart.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.YourCartAppBar:
                startActivity(new Intent(ProductActivity.this, CartActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
