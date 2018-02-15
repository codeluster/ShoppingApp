package com.example.tanmay.shoppingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanmay on 15/2/18.
 */

public class CartElement implements Parcelable{

    Product product;
    Integer quantityOrdered;


    CartElement(Product product, Integer quantityOrdered) {

        this.product = product;
        this.quantityOrdered = quantityOrdered;

    }

    protected CartElement(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        if (in.readByte() == 0) {
            quantityOrdered = null;
        } else {
            quantityOrdered = in.readInt();
        }
    }

    public static final Creator<CartElement> CREATOR = new Creator<CartElement>() {
        @Override
        public CartElement createFromParcel(Parcel in) {
            return new CartElement(in);
        }

        @Override
        public CartElement[] newArray(int size) {
            return new CartElement[size];
        }
    };

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        if (quantityOrdered == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantityOrdered);
        }
    }
}
