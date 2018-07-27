package com.example.tanmay.shoppingapp.Activities;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tanmay.shoppingapp.Adapters.CartCursorAdapter;
import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

public class CartActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CART_LOADER = 0;
    CartCursorAdapter mCursorAdapter;
    private static int numCart = 0;
    FloatingActionButton checkOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

        checkOutButton = findViewById(R.id.activity_cart_checkout_fab);
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

        cartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoProduct(l);
            }
        });

        registerForContextMenu(cartListView);

        getLoaderManager().initLoader(CART_LOADER, null, this);

    }

    private void gotoProduct(long itemClicked) {

        Intent viewProduct = new Intent(CartActivity.this, ProductActivity.class);

        // Get the Uri of the product in cart
        Uri cartUri = ContentUris.withAppendedId(BaseContract.CartEntry.CONTENT_URI, itemClicked);

        String[] projection = {
                BaseContract.CartEntry._ID,
                BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID
        };

        // This cursor contains one element corresponding to the product clicked
        Cursor fuhgvh = getContentResolver().query(cartUri, projection, null, null, null, null);

        fuhgvh.moveToFirst();

        // This is the ID of the product in the products database
        int productID = fuhgvh.getInt(fuhgvh.getColumnIndexOrThrow(BaseContract.CartEntry.COLUMN_NAME_PRODUCT_ID));

        fuhgvh.close();

        // This Uri corresponds to row in product database containing required product
        Uri productUri = ContentUris.withAppendedId(BaseContract.ProductEntry.CONTENT_URI, productID);

        viewProduct.setData(productUri);
        startActivity(viewProduct);

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
    protected void onStart() {
        super.onStart();
        // If the cart is empty then halt creation of menu
        if (numCart == 0) {
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (numCart == 0) {
            MenuItem clearCart = menu.findItem(R.id.action_clear_cart);
            clearCart.setVisible(false);
            checkOutButton.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.your_cart_toolbar, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.cart_list_view) {
            getMenuInflater().inflate(R.menu.cart_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // Get information about the menu that is created
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectID = menuInfo.position;
        switch (item.getItemId()) {
            case R.id.action_delete_cart_entry:
                showDeleteConfirmationDialog(selectID);
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_clear_cart:
                showDeleteAllConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.remove_from_cart_dialog_message);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = ContentUris.withAppendedId(BaseContract.CartEntry.CONTENT_URI, position + 1);
                getContentResolver().delete(uri, null, null);

            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }

    private void showDeleteAllConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.empty_cart_dialog_message);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getContentResolver().delete(BaseContract.CartEntry.CONTENT_URI, null, null);
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) dialogInterface.dismiss();
            }
        });
        builder.create().show();

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
        numCart = cursor.getCount();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}

