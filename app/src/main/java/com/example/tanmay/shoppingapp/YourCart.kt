package com.example.tanmay.shoppingapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_your_cart.*

class YourCart : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_cart)

        val toolbar: android.support.v7.widget.Toolbar = YourCartToolBar
        toolbar.title = resources.getString(R.string.cart)

        val CartList: ArrayList<CartElement> = intent.getParcelableArrayListExtra<CartElement>("Cart")
        val receivedCartElement: CartElement

        if (CartList.size > 0) {
            receivedCartElement = CartList.get(CartList.size - 1)
        }
        else{
            receivedCartElement = CartElement(Product(0,0,0,0,0),0)
        }


        val prod: Product = receivedCartElement.getProduct()

        tv1.text = resources.getString(prod.nameID)
        tv2.text = resources.getInteger(prod.priceID).toString()
        tv3.text = resources.getString(prod.descriptionID)
        tv4.text = resources.getInteger(receivedCartElement.quantityOrdered).toString()

        tv1.text = CartList.size.toString()
    }
}
