package com.example.tanmay.shoppingapp.Data;

import com.example.tanmay.shoppingapp.R;

public class ProductDatabase {

    private static final Product PRODUCT_LIST[] = new Product[3];

    private static void instantiate() {

        PRODUCT_LIST[0] = new Product(R.string.product1Name, R.integer.product1Price, R.drawable.product1thumbnail, R.drawable.product1image, R.string.product1Description);
        PRODUCT_LIST[1] = new Product(R.string.product2Name, R.integer.product2Price, R.drawable.product2thumbnail, R.drawable.product2image, R.string.product2Description);
        PRODUCT_LIST[2] = new Product(R.string.product3Name, R.integer.product3Price, R.drawable.product3thumbnail, R.drawable.product3image, R.string.product3Description);

    }

    public static Product[] getProductList() {
        instantiate();
        return PRODUCT_LIST;
    }
}