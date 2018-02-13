package com.example.tanmay.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

public class HomePage extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.homePageToolBar);
        setSupportActionBar(toolbar);

        startService(new Intent(HomePage.this, ProductList.class));

/*        ProductList productList = new ProductList();
        Product someProduct = productList.productArrayList.get(0);
        Integer Name = someProduct.getNameID();

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(Name.toString());*/

        Button button = findViewById(R.id.DummyProductPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ProductPage.class));
            }
        });


    }
}
