package com.example.tabiangifts.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Product implements Parcelable {

    private static final String TAG = "Product";

    private String title;
    private String description;
    private int image;
    private BigDecimal price;
    private BigDecimal salePrice;
    private int numRatings;
    private BigDecimal rating;
    private int serialNumber;

    public Product() {
    }

    public Product(String title, String description, int image, BigDecimal price, BigDecimal salePrice,
                   int numRatings, BigDecimal rating, int serialNumber) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.salePrice = salePrice;
        this.numRatings = numRatings;
        this.rating = rating;
        this.serialNumber = serialNumber;
    }

    protected Product(Parcel in) {
        title = in.readString();
        description = in.readString();
        image = in.readInt();
        numRatings = in.readInt();
        serialNumber = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(image);
        dest.writeInt(numRatings);
        dest.writeInt(serialNumber);
    }

    @Override
    public int describeContents() {
        return 0;
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


    public boolean hasSalePrice() {
        double salePrice = this.salePrice.doubleValue();
        if (salePrice > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getNumberRatingsString() {
        return ("(" + getNumRatings() + ")");
    }

    public static String getTAG() {
        return TAG;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}
