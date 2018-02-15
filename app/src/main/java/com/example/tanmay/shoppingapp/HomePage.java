package com.example.tanmay.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    ArrayList<Product> productList;
    ArrayList<CartElement> cartList;

    ListView productListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.YourCartAppBar) {
            startActivityForResult(sendCart(new Intent(HomePage.this, com.example.tanmay.shoppingapp.YourCart.class)), 457);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        productList.clear();
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.homePageToolBar);
        setSupportActionBar(toolbar);

        ProductList co = new ProductList();
        productList = co.getProductList();


        cartList = new ArrayList<>();

        productListView = (ListView) findViewById(R.id.HomePageProductList);

        productListView.setAdapter(new ProductListAdapter(this, productList));

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent io = new Intent(HomePage.this, ProductPage.class);
                io.putExtra("productInfo", productList.get(i));
                startActivityForResult(sendCart(io), 999);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999 && resultCode == RESULT_OK) {
            cartList = data.getParcelableArrayListExtra("Cart");
        }

        if (requestCode == 457 && resultCode == RESULT_OK){
            cartList = data.getParcelableArrayListExtra("Cart");
        }
    }

    private Intent sendCart(Intent intent) {

        intent.putParcelableArrayListExtra("Cart", cartList);

        return intent;

    }



}

class ProductListAdapter extends ArrayAdapter<Product> {

    private ArrayList<Product> contentList;

    ProductListAdapter(Context context, ArrayList<Product> contentList) {

        super(context, 0, contentList);

        this.contentList = contentList;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        convertView = inflater.inflate(R.layout.product_list_element, parent, false);

        ImageView thumbnail = convertView.findViewById(R.id.productListElementImageView);
        thumbnail.setImageResource(contentList.get(position).thumbnailID);


        TextView productName = (TextView) convertView.findViewById(R.id.productListElementProductNameTextView);
        productName.setText(contentList.get(position).nameID);

        return convertView;


    }

}
