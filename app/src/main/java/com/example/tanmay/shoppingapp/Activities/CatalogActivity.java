package com.example.tanmay.shoppingapp.Activities;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tanmay.shoppingapp.Adapters.CatalogCursorAdapter;
import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

//    LinearLayout linearLayout;
//    TextView name;
//    ImageView thumbnail;
//    DrawerLayout mDrawerLayout;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//
//            Snackbar snackbar = Snackbar.make(linearLayout, "Added to Cart", Snackbar.LENGTH_LONG);
//            snackbar.show();
//
//        }
//    }

    // Identifier for the product data loader
    private static final int PRODUCT_LOADER = 0;

    // Adapter for the list view
    CatalogCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        setTitle(R.string.title_activity_catalog);

//        linearLayout = findViewById(R.id.d68f8);https://github.com/codeluster/AB-Music-Player.git

        SharedPreferences first_run = getSharedPreferences("ApplicationState", MODE_PRIVATE);
        SharedPreferences.Editor editor = first_run.edit();


        //Adding Navigation Drawer
//        mDrawerLayout = findViewById(R.id.navigation_drawer_activity_catalog);

//        SharedPreferences userInfo = getSharedPreferences("UserInformation", MODE_PRIVATE);

//        NavigationView navigationView = findViewById(R.id.home_page_nav_view);

//        View header = navigationView.getHeaderView(navigationView.getHeaderCount());
//        TextView username = header.findViewById(R.id.nav_drawer_header_username);
//        String nameString = userInfo.getString("FirstName", "") + userInfo.getString("LastName", "");
//        username.setText(nameString);

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()) {
//
//                    case R.id.nav_drawer_sign_up:
//                        startActivity(new Intent(CatalogActivity.this, SignUp.class));
//                        break;
//
//                    case R.id.homepage_drawer_your_cart:
//                        startActivity(new Intent(CatalogActivity.this, CartActivity.class));
//                        break;
//
//                    case R.id.homepage_drawer_account:
//                        startActivity(new Intent(CatalogActivity.this, UserAccount.class));
//                        break;
//                }
//
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });


        if (!first_run.getBoolean("ProductListCreated", false)) {

            TypedArray PRODUCT_NAMES = getResources().obtainTypedArray(R.array.product_Names);
            TypedArray PRODUCT_PRICES = getResources().obtainTypedArray(R.array.product_Prices);
            TypedArray PRODUCT_DESCRIPTIONS = getResources().obtainTypedArray(R.array.product_Descriptions);
            TypedArray PRODUCT_IMAGES = getResources().obtainTypedArray(R.array.product_Images);
            TypedArray PRODUCT_THUMBNAILS = getResources().obtainTypedArray(R.array.product_Thumbnails);


            for (int i = 0; PRODUCT_NAMES.hasValue(i); i++) {

                ContentValues values = new ContentValues();

                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, PRODUCT_NAMES.getResourceId(i, 0));
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE, PRODUCT_PRICES.getResourceId(i, 0));
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, PRODUCT_DESCRIPTIONS.getResourceId(i, 0));
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, PRODUCT_IMAGES.getResourceId(i, 0));
                values.put(BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, PRODUCT_THUMBNAILS.getResourceId(i, 0));

                getContentResolver().insert(BaseContract.ProductEntry.CONTENT_URI, values);

                values.clear();

            }

            PRODUCT_NAMES.recycle();
            PRODUCT_PRICES.recycle();
            PRODUCT_DESCRIPTIONS.recycle();
            PRODUCT_IMAGES.recycle();
            PRODUCT_THUMBNAILS.recycle();

            editor.putBoolean("ProductListCreated", true);
            editor.apply();
        }

        ListView productListView = findViewById(R.id.catalog_list_view);
        mCursorAdapter = new CatalogCursorAdapter(this, null);
        productListView.setAdapter(mCursorAdapter);


        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemClicked, long clickedID) {
                Intent intent = new Intent(CatalogActivity.this, ProductActivity.class);
                // Uri of clicked product
                Uri clickedProduct = ContentUris.withAppendedId(BaseContract.ProductEntry.CONTENT_URI, clickedID);
                // Set Uri as data in intent
                intent.setData(clickedProduct);
                startActivity(intent);
            }
        });

//        //Will crash app if API level < 25
//        //Useless shortcut that opens google
//        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
//        ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1")
//                .setShortLabel("Your Cart")
//                .setLongLabel("Open your cart")
//                .setIcon(Icon.createWithResource(this, R.drawable.ic_add_shopping_cart_black_24dp))
//                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
//                .build();
//
//        shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));

//        Button button = findViewById(R.id.djid);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(CatalogActivity.this, SignUp.class));
//            }
//        });
//
//    }


        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_activity_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.YourCartAppBar:
                startActivity(new Intent(CatalogActivity.this, CartActivity.class));
                return true;

//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        // Define a projection to specify the rows needed
        String[] projection = {
                BaseContract.ProductEntry._ID,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME,
                BaseContract.ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL
        };

        return new CursorLoader(this,
                BaseContract.ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Update the cursor with new data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Callback when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}

