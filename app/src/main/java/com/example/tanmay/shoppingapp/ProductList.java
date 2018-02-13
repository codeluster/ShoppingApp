package com.example.tanmay.shoppingapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by tanmay on 13/2/18.
 */

public class ProductList extends Service {

    ArrayList<Product> productArrayList;

    @Override
    public void onCreate() {
        productArrayList = new ArrayList<>();
        productArrayList.add(new Product(0,0,0,0,0));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
