package com.example.tanmay.shoppingapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_product_page.*

class ProductPage : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        val toolbar: android.support.v7.widget.Toolbar = productPageToolBar

        setSupportActionBar(toolbar)

val coordinatorLayout : CoordinatorLayout = ProductPageCoordinatorLayout
        val productName : TextView = ProductPageProductName
        val productImage : ImageView = ProductPageProductImage
        val productPrice : TextView = ProductPageProductPrice
        val productDescription : TextView = ProductPageProductDescription
        val seeMore : TextView = ProductPageDescriptionSeeMore
        val quantitySeekBar : SeekBar = ProductPageProductQuantitySeekBar
        val quantityTextView : TextView = ProductPageProductQuantityTextView
        val buyButton : Button = ProductPageBuyButton
        val addToCartButton : Button = ProductPageAddToCartButton


        addToCartButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                Snackbar.make(coordinatorLayout, "Added To Cart", Snackbar.LENGTH_SHORT).show()

            }
        })

    }

}
