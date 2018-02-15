package com.example.tanmay.shoppingapp

import java.util.ArrayList

/**
 * Created by tanmay on 16/2/18.
 */


class CartList{

    companion object {
        internal val cartArrayList = ArrayList<CartElement>()
    }

    fun getCartList(): ArrayList<CartElement> {

        return CartList.cartArrayList
    }


    fun getCartElement(index: Int): CartElement {

        return CartList.cartArrayList.get(index)
    }

}