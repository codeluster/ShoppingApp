package com.example.tanmay.shoppingapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanmay.shoppingapp.DataSet.ProductListContract.ProductEntry;

public class HomePage extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView name;
    ImageView thumbnail;
    DrawerLayout mDrawerLayout;

    Cursor cursorNew;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page_toolbar, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.YourCartAppBar:
                startActivity(new Intent(HomePage.this, YourCart.class));
                return true;

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 001 && resultCode == RESULT_OK) {

            Snackbar snackbar = Snackbar.make(linearLayout, "Added to Cart", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        linearLayout = findViewById(R.id.d68f8);

        SharedPreferences preferences = getSharedPreferences("ApplicationState", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //Adding custom toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.homePageToolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //Adding Navigation Drawer
        mDrawerLayout = findViewById(R.id.home_page_drawer);

        SharedPreferences userInfo = getSharedPreferences("UserInformation", MODE_PRIVATE);

        NavigationView navigationView = findViewById(R.id.home_page_nav_view);
/*
        View header = navigationView.getHeaderView(navigationView.getHeaderCount());
        TextView username = header.findViewById(R.id.nav_drawer_header_username);
        String nameString = userInfo.getString("FirstName", "") + userInfo.getString("LastName", "");
        username.setText(nameString);
*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_drawer_sign_up:
                        startActivity(new Intent(HomePage.this, SignUp.class));
                        break;

                    case R.id.homepage_drawer_your_cart:
                        startActivity(new Intent(HomePage.this, YourCart.class));
                        break;

                    case R.id.homepage_drawer_account:
                        startActivity(new Intent(HomePage.this, UserAccount.class));
                        break;
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        if (!preferences.getBoolean("ProductListCreated", false)) {

            insertProduct();

            editor.putBoolean("ProductListCreated", true);
            editor.apply();
        }


        //Projection is just the name of the columns we would like to receive
        String[] projection = {

                ProductEntry._ID,
                ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL,
                ProductEntry.COLUMN_NAME_PRODUCT_NAME

        };

        //gets the entire productList
        cursorNew = getContentResolver().query(ProductEntry.CONTENT_URI, projection, null, null, null);

        ListView listView = findViewById(R.id.productList_homepage);
        listView.setAdapter(new productListAdapter(HomePage.this, cursorNew));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemClicked, long l) {
                Intent intent = new Intent(HomePage.this, ProductPage.class);

                TextView fhu = view.findViewById(R.id.f249873);
                intent.putExtra("itemClicked", fhu.getText().toString());
                startActivityForResult(intent, 001);
            }
        });

       /* //Will crash app if API level < 25
        //Useless shortcut that opens google
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1")
                .setShortLabel("Your Cart")
                .setLongLabel("Open your cart")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_add_shopping_cart_black_24dp))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")))
                .build();

        shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));*/

        Button button = findViewById(R.id.djid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, SignUp.class));
            }
        });

    }

    // Only run on first launch
    private void insertProduct() {

        ContentValues values = new ContentValues();

        //Product 1
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, R.string.product1Name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, R.integer.product1Price);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, R.drawable.product1thumbnail);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, R.drawable.product1image);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, R.string.product1Description);
        getContentResolver().insert(ProductEntry.CONTENT_URI, values);

        //Product 2
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, R.string.product2Name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, R.integer.product2Price);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, R.drawable.product2thumbnail);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, R.drawable.product2image);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, R.string.product2Description);
        getContentResolver().insert(ProductEntry.CONTENT_URI, values);

        //Product 3
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, R.string.product3Name);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_PRICE, R.integer.product3Price);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL, R.drawable.product3thumbnail);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_IMAGE, R.drawable.product3image);
        values.put(ProductEntry.COLUMN_NAME_PRODUCT_DESCRIPTION, R.string.product3Description);
        getContentResolver().insert(ProductEntry.CONTENT_URI, values);

    }


    private class productListAdapter extends CursorAdapter {

        private productListAdapter(Context context, Cursor c) {

            super(context, c);

        }

        //Returns a new blank view
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context).inflate(R.layout.product_list_element, viewGroup, false);
        }

        //Actually responsible for the data binding
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            name = view.findViewById(R.id.productListElementProductNameTextView);
            thumbnail = view.findViewById(R.id.productListElementImageView);

            TextView id = view.findViewById(R.id.f249873);
            Integer geihl = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry._ID));

            name.setText(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRODUCT_NAME)));
            thumbnail.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRODUCT_THUMBNAIL)));
            id.setText(geihl.toString());

        }

    }

}
