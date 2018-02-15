package com.example.tanmay.shoppingapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_product_page.*
import org.w3c.dom.Text

class ProductPage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        val productList : ArrayList<Product> = intent.getParcelableArrayListExtra("productList");
        val displayProduct : Product = productList.get(0)


        var seeMore: TextView
        val productDescription: TextView
        val productPrice: TextView
        val productImage: ImageView
        val productName: TextView
        val quantityTextView: TextView
        val coordinatorLayout: CoordinatorLayout
        val quantitySeekBar: SeekBar
        val buyButton: Button
        val addToCartButton: Button
        val toolbar: android.support.v7.widget.Toolbar


        toolbar = productPageToolBar
        setSupportActionBar(toolbar)

        coordinatorLayout = ProductPageCoordinatorLayout
        productName = ProductPageProductName
        productImage = ProductPageProductImage
        productPrice = ProductPageProductPrice
        productDescription = ProductPageProductDescription
        seeMore = ProductPageDescriptionSeeMore
        quantitySeekBar = ProductPageProductQuantitySeekBar
        quantityTextView = ProductPageProductQuantityTextView
        buyButton = ProductPageBuyButton
        addToCartButton = ProductPageAddToCartButton

        productName.text = resources.getString(displayProduct.nameID)
        productPrice.text = resources.getInteger(displayProduct.priceID).toString()
        productDescription.text = resources.getString(displayProduct.descriptionID)
        productImage.setImageResource(displayProduct.imageID)



        addToCartButton.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {

                if (canPurchase(quantityTextView, this@ProductPage)) {

                    //Create snackbar wuth option to Undo
                    Snackbar.make(coordinatorLayout, "Added To Cart", Snackbar.LENGTH_SHORT).setAction("UNDO", object : View.OnClickListener {

                        override fun onClick(p0: View?) {

                            Toast.makeText(this@ProductPage, "Removed from Cart", Toast.LENGTH_SHORT).show()

                        }
                    }).show()

                }

            }

        })

        seeMore.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                expandDescription(seeMore, productDescription)
            }
        })

        //Changes the quantity text according to Seekbar
        quantitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


            override fun onStartTrackingTouch(p0: SeekBar?) {

                quantityTextView.text = quantityTextView.text

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

                quantityTextView.text = quantityTextView.text

            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                quantityTextView.text = p1.toString()

            }
        })

    }


    fun expandDescription(seeMore: TextView, productDescription: TextView) {

        seeMore.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                productDescription.maxLines = 100;
                seeMore.text = "SEE LESS"
                collapseDescription(seeMore, productDescription)

            }
        })
    }

    fun canPurchase(quantity: TextView, context: Context): Boolean {

        if (QuantityIsNull(quantity)) {
            nullQuantityToast(context)
            return false
        } else return true


    }

    fun nullQuantityToast(context: Context): Unit {

        Toast.makeText(context, "Quantity cannot be zero", Toast.LENGTH_SHORT).show()

    }

    fun QuantityIsNull(quantity: TextView): Boolean {
        if (quantity.text == "0") {
            return true
        } else return false

    }

    fun collapseDescription(seeMore: TextView, productDescription: TextView) {
        seeMore.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                productDescription.maxLines = 5;
                seeMore.text = "SEE MORE"
                expandDescription(seeMore, productDescription)
            }
        })

    }

}
