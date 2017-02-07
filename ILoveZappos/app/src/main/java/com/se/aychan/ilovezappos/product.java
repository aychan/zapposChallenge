package com.se.aychan.ilovezappos;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by aychan on 2/5/17.
 * Class which holds Product Object with specific attributes based on ReST Structure
 */

public class Product extends BaseObservable implements Parcelable {
    private String brandName;
    private String thumbnailImageUrl;
    private int productId;
    private String originalPrice;
    private int styleId;
    private int colorId;
    private String price;
    private String percentOff;
    private String productUrl;
    private String productName;

    public Product(String brandName, String thumbnailImageUrl, int productId, String originalPrice, int styleId, int colorId, String price, String percentOff, String productUrl, String productName) {
        this.brandName = String.valueOf(Html.fromHtml(brandName));
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.productId = productId;
        this.originalPrice = originalPrice;
        this.styleId = styleId;
        this.colorId = colorId;
        this.price = price;
        this.percentOff = percentOff;
        this.productUrl = productUrl;
        this.productName = String.valueOf(Html.fromHtml(productName));
    }

    protected Product(Parcel in) {
        brandName = in.readString();
        thumbnailImageUrl = in.readString();
        productId = in.readInt();
        originalPrice = in.readString();
        styleId = in.readInt();
        colorId = in.readInt();
        price = in.readString();
        percentOff = in.readString();
        productUrl = in.readString();
        productName = in.readString();
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
    @Bindable
    public String getBrandName() {
        String s = null;
        try {
            s = URLDecoder.decode(brandName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Decode UTF-8 special characters from their ASCII code
        Spanned f = Html.fromHtml(s);
        return f.toString();
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    @Bindable
    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    @Bindable
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    @Bindable
    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }
    @Bindable
    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }
    @Bindable
    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    @Bindable
    public String getPrice() {
        if(price.equals(getOriginalPrice())){
            return " ";
        }
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    /*
        Determine whether or not there is any percent off
     */
    public boolean hasDiscount(){
        return !price.equals(originalPrice);
    }
    @Bindable
    public String getPercentOff() {
        StringBuilder stringBuffer = new StringBuilder();
        if(!hasDiscount()){
            Log.d("Product Info", " No Discount");
            stringBuffer.append(" ");
        }else{
            stringBuffer.append("(").append(percentOff).append(" off)");
        }

        return stringBuffer.toString();
    }

    public void setPercentOff(String percentOff) {
        this.percentOff = percentOff;
    }
    @Bindable
    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    @Bindable
    public String getProductName() {
        String s = null;
        try {
            s = URLDecoder.decode(productName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Spanned f = Html.fromHtml(s);
        return f.toString();
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brandName);
        dest.writeString(thumbnailImageUrl);
        dest.writeInt(productId);
        dest.writeString(originalPrice);
        dest.writeInt(styleId);
        dest.writeInt(colorId);
        dest.writeString(price);
        dest.writeString(percentOff);
        dest.writeString(productUrl);
        dest.writeString(productName);
    }

}
