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

    internal var initialized = false

    companion object {

        internal val productArrayList = ArrayList<Product>()
    }

    fun initialize(){

        productArrayList.add(Product(0,0,0,0,0))

    }

    fun getProductList(): ArrayList<Product>{

        if (initialized != true){
            initialize()
        }

        return productArrayList
    }


    fun getProduct(index : Int): Product{

        if (initialized != true){
            initialize()
        }

        return productArrayList.get(index)
    }


}

