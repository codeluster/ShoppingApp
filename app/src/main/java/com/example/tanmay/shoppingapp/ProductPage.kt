package com.example.tanmay.shoppingapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_product_page.*

class ProductPage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        val toolbar: android.support.v7.widget.Toolbar = productPageToolBar

        setSupportActionBar(toolbar)


    }
}
