package com.example.tanmay.shoppingapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.lang.reflect.Array
import java.util.ArrayList

/**
 * Created by tanmay on 13/2/18.
 */

class ProductList {

    companion object {

        internal val productArrayList = ArrayList<Product>()
    }

    private fun initialize() {

        productArrayList.add(Product(R.string.product1Name, R.integer.product1Price, R.drawable.product1image, R.drawable.product1thumbnail, R.string.product1Description))
        productArrayList.add(Product(R.string.product2Name, R.integer.product2Price, R.drawable.product2image, R.drawable.product2thumbnail, R.string.product2Description))

    }

    fun getProductList(): ArrayList<Product> {
        initialize()
        return productArrayList
    }


    fun getProduct(index: Int): Product {

        return productArrayList.get(index)
    }

}

