package com.example.tanmay.shoppingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanmay on 15/2/18.
 */

public class Product implements Parcelable {

    int nameID;
    int priceID;
    int imageID;
    int thumbnailID;
    int descriptionID;

    Product(int nameID, int priceID, int imageID, int thumbnailID, int descriptionID) {

        this.nameID = nameID;
        this.priceID = priceID;
        this.imageID = imageID;
        this.thumbnailID = thumbnailID;
        this.descriptionID = descriptionID;

    }

    protected Product(Parcel in) {
        nameID = in.readInt();
        priceID = in.readInt();
        imageID = in.readInt();
        thumbnailID = in.readInt();
        descriptionID = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(nameID);
        parcel.writeInt(priceID);
        parcel.writeInt(imageID);
        parcel.writeInt(thumbnailID);
        parcel.writeInt(descriptionID);
    }
}
