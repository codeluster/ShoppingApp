package com.example.tanmay.shoppingapp.Activities;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.tanmay.shoppingapp.Adapters.CartCursorAdapter;
import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

public class CartActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    CartCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

//
//        // Display personalized message in Action Bar Title
//        SharedPreferences preferences = getSharedPreferences("UserInformation", MODE_PRIVATE);
//        if (preferences.getString("FirstName", null) != null) {
//            toolbar.setTitle(preferences.getString("FirstName", null) + "'s Cart");
//        }

//        // Set an OnClickListener on the FAB
//        checkOutButton = findViewById(R.id.activity_cart_checkout_fab);
//        checkOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkOut();
//            }
//        });

        ListView cartListView = findViewById(R.id.cart_list_view);
        View empty_view = findViewById(R.id.activity_cart_empty_view);
        cartListView.setEmptyView(empty_view);
        mCursorAdapter = new CartCursorAdapter(this, null);
        cartListView.setAdapter(mCursorAdapter);

    }
//    private void checkOut() {
//
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.putExtra(Intent.EXTRA_EMAIL, "tanmaysingal2013@gmail.com");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Email sent from Shopping App");
//
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "No email client available", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    // Graveyard
//    private int getSubtotal(Cursor cart, Cursor productList) {
//
//        int subtotal = 0;
//        int cartCounter = 1;
//
//        //Travereses the cart
//        while (!cart.isLast()) {
//
//            cart.move(cartCounter);
//            int quan = cart.getInt(cart.getColumnIndexOrThrow(BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY));
//
//            //Traverses the productList
//            //Moves to location as given by the cart ID field
//            productList.move(cart.getInt(cart.getColumnIndexOrThrow(BaseContract.CartEntry._ID)));
//
//            subtotal += quan * getResources().getInteger(productList.getInt(productList.getColumnIndexOrThrow(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
//            cartCounter++;
//        }
//        return subtotal;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.your_cart_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clearCart:
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                BaseContract.CartEntry._ID,
                BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID,
                BaseContract.CartEntry.COLUMN_NAME_ORDERED_QUANTITY
        };

        return new CursorLoader(this,
                BaseContract.CartEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}

