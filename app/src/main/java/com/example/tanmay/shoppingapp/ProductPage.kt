package com.example.tanmay.shoppingapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_product_page.*
import org.w3c.dom.Text
import java.util.ArrayList

class ProductPage : AppCompatActivity() {

    var cartList: ArrayList<CartElement> = ArrayList()

    override fun onBackPressed() {

        val intent: Intent = Intent()

        intent.putExtra("Cart", cartList)

        setResult(Activity.RESULT_OK, intent)

        finish()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_page)

        cartList = receiveCart(intent)

        val displayProduct: Product = intent.getParcelableExtra("productInfo")
        val seeMore: TextView
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

                    cartList.add(CartElement(displayProduct, quantityTextView.text.toString().toInt()))

                    //Create snackbar wuth option to Undo
                    Snackbar.make(coordinatorLayout, "Added To Cart", Snackbar.LENGTH_SHORT).setAction("UNDO", object : View.OnClickListener {

                        override fun onClick(p0: View?) {

                            cartList.removeAt(cartList.size - 1)
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

        productDescription.maxLines = 100;

        seeMore.text = "SEE LESS"

        seeMore.setOnClickListener(object : View.OnClickListener {

            override fun onClick(p0: View?) {

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

        productDescription.maxLines = 5;

        seeMore.text = "SEE MORE"

        seeMore.setOnClickListener(object : View.OnClickListener {

            override fun onClick(p0: View?) {

                expandDescription(seeMore, productDescription)

            }

        })

    }

    private fun sendCart(intent: Intent, cartList: ArrayList<CartElement>): Intent {

        intent.putParcelableArrayListExtra("Cart", cartList)

        return intent

    }

    private fun receiveCart(intent: Intent): ArrayList<CartElement> {

        return intent.getParcelableArrayListExtra<CartElement>("Cart")

    }

}
