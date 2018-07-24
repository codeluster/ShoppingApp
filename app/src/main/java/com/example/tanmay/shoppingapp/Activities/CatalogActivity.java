package com.example.tanmay.shoppingapp.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.tanmay.shoppingapp.Adapters.CatalogCursorAdapter;
import com.example.tanmay.shoppingapp.Data.BaseContract;
import com.example.tanmay.shoppingapp.R;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

//    LinearLayout linearLayout;
//    TextView name;
//    ImageView thumbnail;
//    DrawerLayout mDrawerLayout;
//
//    Cursor cursorNew;
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.home_page_toolbar, menu);
//
//        return true;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.YourCartAppBar:
//                startActivity(new Intent(CatalogActivity.this, YourCart.class));
//                return true;
//
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
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
        setContentView(R.layout.activity_home_page);

//        linearLayout = findViewById(R.id.d68f8);

//        SharedPreferences preferences = getSharedPreferences("ApplicationState", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();

        //Adding custom toolbar
//        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.homePageToolBar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //Adding Navigation Drawer
//        mDrawerLayout = findViewById(R.id.home_page_drawer);

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
//                        startActivity(new Intent(CatalogActivity.this, YourCart.class));
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


//        if (!preferences.getBoolean("ProductListCreated", false)) {
//
//            insertProduct();
//
//            editor.putBoolean("ProductListCreated", true);
//            editor.apply();
//        }


        //Projection is just the name of the columns we would like to receive
//        String[] projection = {
//
//                ProductEntry._ID,
//                ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL,
//                ProductEntry.COLUMN_NAME_PRODUCT_NAME
//
//        };
//
//        //gets the entire productList
//        cursorNew = getContentResolver().query(ProductEntry.CONTENT_URI, projection, null, null, null);
//
//        ListView listView = findViewById(R.id.productList_homepage);
//        listView.setAdapter(new productListAdapter(CatalogActivity.this, cursorNew));
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int itemClicked, long l) {
//                Intent intent = new Intent(CatalogActivity.this, ProductPage.class);
//                TextView fhu = view.findViewById(R.id.f249873);
//                intent.putExtra("itemClicked", fhu.getText().toString());
//                startActivityForResult(intent, 1);
//            }
//        });

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

//    // Only runs on first launch
//    private void insertProduct() {
//
//        ContentValues values = new ContentValues();
//
//        final Product PRODUCT_LIST[] = ProductDatabase.getProductList();
//
//        for (Product x : PRODUCT_LIST) {
//
//            values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, x.getProduct_name());
//            values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, x.getProduct_price());
//            values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, x.getProduct_thumbnail());
//            values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, x.getProduct_image());
//            values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, x.getProduct_description());
//
//            getContentResolver().insert(ProductEntry.CONTENT_URI, values);
//
//            values.clear();
//        }
//
//    }

//
//    private class productListAdapter extends CursorAdapter {
//
//        private productListAdapter(Context context, Cursor c) {
//            super(context, c);
//        }
//
//        //Returns a new blank view
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//            return LayoutInflater.from(context).inflate(R.layout.product_list_element, viewGroup, false);
//        }
//
//        //Actually responsible for the data binding
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//
//            name = view.findViewById(R.id.productListElementProductNameTextView);
//            thumbnail = view.findViewById(R.id.productListElementImageView);
//
//            TextView id = view.findViewById(R.id.f249873);
//            Integer geihl = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry._ID));
//
//            name.setText(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRODUCT_NAME)));
//            thumbnail.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL)));
//            id.setText(geihl.toString());
//
//        }
//

        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

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

