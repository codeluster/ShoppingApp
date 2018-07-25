package com.example.tanmay.shoppingapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

public class ProductActivity extends AppCompatActivity {

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

    private Uri currentProductUri;

    Boolean descExpanded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
//        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.productPageToolBar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        currentProductUri = intent.getData();

        ExpandToggle = findViewById(R.id.ProductPageDescriptionSeeMore);
        CollapseToggle = findViewById(R.id.ProductPageDescriptionSeeLess);
        addToCart = findViewById(R.id.add_to_cart);
        decrease = findViewById(R.id.productPageQuantityDecrease);
        increase = findViewById(R.id.productPageQuantityIncrease);
        quantityBox = findViewById(R.id.productPageQuantity);

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

//        addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ContentValues values = new ContentValues();
//
//                values.put(BaseContract.CartEntry._ID, prodID);
//                values.put(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY, quantity);
//
//                getContentResolver().insert(BaseContract.CartEntry.CONTENT_URI, values);
//
//                finish();
//            }
//        });

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
                startActivity(new Intent(ProductActivity.this, YourCart.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
