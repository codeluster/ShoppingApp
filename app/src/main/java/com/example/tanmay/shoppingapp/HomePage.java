package com.example.tanmay.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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

    ListView productListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);


        return true;
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
/*

        Button YourCart = (Button) findViewById(R.id.YourCartAppBar);
        YourCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, com.example.tanmay.shoppingapp.YourCart.class));
            }
        });*/

        productListView = (ListView) findViewById(R.id.HomePageProductList);

        productListView.setAdapter(new ProductListAdapter(this, productList));

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent io = new Intent(HomePage.this, ProductPage.class);
                io.putExtra("productInfo", productList.get(i));
                startActivity(io);

            }
        });

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
