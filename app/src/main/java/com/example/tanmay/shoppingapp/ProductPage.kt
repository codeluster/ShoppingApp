package com.example.tanmay.shoppingapp

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_product_page.*

class ProductPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_page)

        val displayProduct: Product
        val seeMore: TextView
        val productDescription: TextView
        val productPrice: TextView
        val productImage: ImageView
        val productName: TextView
        val quantityTextView: TextView
        val coordinatorLayout: ConstraintLayout
        val quantitySeekBar: SeekBar
        val toolbar: android.support.v7.widget.Toolbar

        toolbar = productPageToolBar
        setSupportActionBar(toolbar)

        coordinatorLayout = ProductPageConstraintLayout
        productName = ProductPageProductName
        productImage = ProductPageProductImage
        productPrice = ProductPageProductPrice
        productDescription = ProductPageProductDescription
        seeMore = ProductPageDescriptionSeeMore
        quantitySeekBar = ProductPageProductQuantitySeekBar
        quantityTextView = ProductPageProductQuantityTextView


    }


}
